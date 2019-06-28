package com.example.paginationpolygon;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
import androidx.paging.PositionalDataSource;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.Mono;

/**
 * @author Konstantin Epifanov
 * @since 25.06.2019
 */
public class Paginator<T> implements Disposable, Function<Integer, Mono<PagedList<T>>> {

  /** Executors. */
  private final Executor mFetch, mNotify;

  /** Page Size. */
  private final int mPageSize;

  /** Loader. */
  private final Loader<T> mLoader;

  /** Disposable swap */
  private final Disposable.Swap mSwap = Disposables.swap();

  /**
   * @param fetch  fetch executor (should be immediate, a-la Runnable::run)
   * @param notify notify executor (should be main-thread based)
   * @param page   paging page size
   * @param load   load async function
   */
  public Paginator(@NonNull Executor fetch, @NonNull Executor notify,
                   int page, @NonNull Loader<T> load) {
    mFetch = fetch;
    mNotify = notify;
    mPageSize = page;
    mLoader = load;
  }

  /**
   * @param list some paged list instance
   * @param <V>  type of paged list items
   *
   * @return disposable wrapper over paged list
   */
  @NonNull
  private static <V> Disposable toDisposable(@NonNull PagedList<V> list) {
    final DataSource<?, V> dataSource = list.getDataSource();
    return new Disposable() {
      @Override
      public final void dispose() {
        dataSource.invalidate();
      }

      @Override
      public final boolean isDisposed() {
        return dataSource.isInvalid();
      }
    };
  }

  /**
   * @param load  load function
   * @param items initial items
   * @param <T>   items type
   *
   * @return data source instance
   */
  @NonNull
  private static <T> DataSource<Integer, T> newDataSource(@NonNull Paginator.Loader<T> load, @NonNull T[] items) {
    //System.out.println("Paginator.newDataSource " + "items = [" + items.length + "]");

    return new PositionalDataSource<T>() {
      private final Disposable.Composite composite = Disposables.composite();

      {
        addInvalidatedCallback(new InvalidatedCallback() {
          @Override
          public final void onInvalidated() {
            removeInvalidatedCallback(this);
            composite.dispose();
            //System.out.println("Paginator.onInvalidated: DISPOSE");
          }
        });
      }

      @Override
      public final void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<T> callback) {
        //System.out.println("Paginator.loadInitial " + "params = [" + params.requestedStartPosition + "]" + ", initial size: " + items.length);
        callback.onResult(Arrays.asList(items), params.requestedStartPosition);
      }

      @Override
      public final void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<T> callback) {
        //System.out.println("Paginator.loadRange " + "params = [pos: " + params.startPosition + " ,size " + params.loadSize + "]");

        composite.add(
          load.load(params.startPosition, params.loadSize,
            array -> {
              //System.out.println(" === composite.update load: pos " + params.startPosition + " , requested size " + params.loadSize + " , loaded size: " + array.length);
              callback.onResult(Arrays.asList(array));
            })
        );
      }
    };
  }

  /**
   * @param page    page size
   * @param initial initial size
   *
   * @return paged list configuration
   */
  @NonNull
  private static PagedList.Config newPagedConfig(int page, int initial) {
    //System.out.println("Paginator.newPagedConfig " + "page = [" + page + "], initial = [" + initial + "]");

    return new PagedList.Config.Builder()
      .setEnablePlaceholders(false)
      .setPageSize(page)
      .setPrefetchDistance(page)
      .setInitialLoadSizeHint(initial)
      .build();
  }

  /**
   * @param fetch    fetch executor
   * @param notify   notify executor
   * @param pageSize page size
   * @param load     load function
   * @param offset   offset loaded items
   * @param items    loaded items array
   * @param <T>      type of items
   *
   * @return new created paged list
   */
  @NonNull
  private static <T> PagedList<T> newPagedList(@NonNull Executor fetch, @NonNull Executor notify, int pageSize,
                                               @NonNull Paginator.Loader<T> load, int offset, @NonNull T[] items) {
    //System.out.println("Paginator.newPagedList " + "pagesize = [" + pageSize + "], offset = [" + offset + "], items = [" + items.length + "]");

    return new PagedList.Builder<>(newDataSource(load, items), newPagedConfig(pageSize, items.length))
      .setFetchExecutor(fetch)
      .setNotifyExecutor(notify)
      .setInitialKey(offset)
      .build();
  }

  /** {@inheritDoc} */
  @Override
  public final void dispose() {
    mSwap.dispose();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isDisposed() {
    return mSwap.isDisposed();
  }

  /** {@inheritDoc} */
  @Override
  @NonNull
  public final Mono<PagedList<T>> apply(@NonNull Integer offset) {
    System.out.println("Paginator.apply: " + offset);

    final Mono<T[]> mono = Mono.create(sink -> {
      Disposable load = mLoader.load(offset, mPageSize, sink::success);
      sink.onDispose(load);
    });

    return mono.map(items -> {
      final PagedList<T> result = newPagedList(mFetch, mNotify, mPageSize, mLoader, offset, items);
      mSwap.update(toDisposable(result));
      return result;
    });
  }

  /**
   * Loader interface
   *
   * @param <V> type of items
   */
  @FunctionalInterface
  interface Loader<V> {

    /**
     * @param offset   load offset/position
     * @param size     load size
     * @param callback callback of loaded items
     *
     * @return disposable signal
     */
    @NonNull
    Disposable load(int offset, int size, @NonNull Consumer<V[]> callback);
  }
}
