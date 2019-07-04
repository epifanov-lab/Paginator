package com.example.paginationpolygon.pagination;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.example.paginationpolygon.Constants;
import com.example.paginationpolygon.DataService;

import java.util.concurrent.Executor;

import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author Konstantin Epifanov
 * @since 25.06.2019
 */
public class PaginationPresenter implements Disposable {

  /** Disposable. */
  private final Disposable mDisposable;

  /** Data Service. */
  private final DataService mDataService;

  /** Default constructor. */
  PaginationPresenter(@NonNull PaginationActivity view) {

    mDataService = new DataService();

    final Executor fetch = Runnable::run,
      notify = new Handler()::post;

    final NonCachedPaginator<Item> paginator = new NonCachedPaginator<>(fetch, notify, Constants.PAGE_SIZE,
      (offset, size, callback) -> mDataService.load(offset, size).subscribe(callback));

    mDisposable = Disposables.composite(
      paginator,

      Flux.merge(Flux.just(new Object()), view.mFluxRefresh)
        .doOnNext(o -> view.showLoadingIndicator(true))
        .map(v -> Math.max(view.getCurrentRecyclerTopPosition(), 0))
        .switchMap(paginator::apply)
        .publishOn(Schedulers.fromExecutor(view::runOnUiThread))
        .doOnNext(o -> view.showLoadingIndicator(false))
        .subscribe(view::submitList),

      view.mFluxRestart
        .doOnNext(o -> view.restart())
        .subscribe(),

      view.mFluxAdd
        .doOnNext(o -> mDataService.addItem())
        .subscribe(),

      view.mFluxDelete
        .doOnNext(o -> mDataService.deleteItem())
        .subscribe(),

      view.mFluxChange
        .doOnNext(o -> mDataService.changeItem())
        .subscribe()
    );
  }

  /** {@inheritDoc} */
  @Override
  public final void dispose() {
    mDisposable.dispose();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isDisposed() {
    return mDisposable.isDisposed();
  }

}
