package com.example.paginationpolygon.old;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.paging.PositionalDataSource;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paginationpolygon.DataService;
import com.example.paginationpolygon.R;
import com.example.paginationpolygon.Utils;

import java.util.Arrays;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import static com.example.paginationpolygon.Utils.toFlux;
import static java.util.Objects.requireNonNull;


public class MainActivity extends AppCompatActivity {

  public static final int PAGE_SIZE = 10;

  private static final int ITEM_LAYOUT = R.layout.item_1;

  private ViewGroup mContainerMain;
  private TextView mLabelText;
  private RecyclerView mRecycler;
  private View mProgress;

  private Flux<View> mFluxRefresh, mFluxAction;

  private DataService mDataService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mContainerMain = findViewById(R.id.container_main);
    mLabelText = findViewById(R.id.label_text);
    mRecycler = findViewById(R.id.recycler);
    mProgress = findViewById(R.id.progress);

    mDataService = new DataService();

    initializeRecycler();

    initializeButtons();

  }

  private void initializeRecycler() {

    // DataSource
    PositionalDataSource dataSource = new PositionalDataSource() {
      @Override
      public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback callback) {
        Log.d("TAG", "loadInitial, requestedStartPosition = " + params.requestedStartPosition +
          ", requestedLoadSize = " + params.requestedLoadSize);

        mDataService.load(params.requestedStartPosition, params.requestedLoadSize)
          .publishOn(Schedulers.fromExecutor(mRecycler::post))
          .doOnSubscribe(subscription -> mProgress.setVisibility(View.VISIBLE))
          .doFinally(f -> mProgress.setVisibility(View.INVISIBLE))
          .subscribe(contents -> callback.onResult(Arrays.asList(contents), 0)
          );
      }

      @Override
      public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback callback) {
        Log.d("TAG", "loadRange, startPosition = " + params.startPosition + ", loadSize = " + params.loadSize);

        mDataService.load(params.startPosition, params.startPosition + params.loadSize)
          .publishOn(Schedulers.fromExecutor(mRecycler::post))
          .doOnSubscribe(subscription -> mProgress.setVisibility(View.VISIBLE))
          .doFinally(f -> mProgress.setVisibility(View.INVISIBLE))
          .subscribe(contents -> callback.onResult(Arrays.asList(contents))
          );
      }
    };

    // PagedList
    PagedList.Config config = new PagedList.Config.Builder()
      .setEnablePlaceholders(false)
      .setPageSize(PAGE_SIZE)
      .setPrefetchDistance(PAGE_SIZE)
      .build();

    PagedList<DataService.Content> list = new PagedList.Builder<>(dataSource, config)
      .setNotifyExecutor(mRecycler::post)
      .setFetchExecutor(Runnable::run)
      .build();


    // Adapter
    mRecycler.setAdapter(Utils.getPagedAdapter(LayoutInflater.from(getApplicationContext()), ITEM_LAYOUT));
    ((PagedListAdapter<DataService.Content, RecyclerView.ViewHolder>) requireNonNull(mRecycler.getAdapter())).submitList(list);

    // Layout Manager
    mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    /*
    Context context = getApplicationContext();
    final LayoutInflater inflater = LayoutInflater.from(context);
    mRecycler.setAdapter(Utils.getPagedAdapter(inflater, ITEM_LAYOUT));
    mRecycler.setLayoutManager(new LinearLayoutManager(context));
    mRecycler.setItemViewCacheSize(0);
    */
  }

  private void initializeButtons() {
    mFluxRefresh = toFlux(findViewById(R.id.button_refresh));
    mFluxAction = toFlux(findViewById(R.id.button_action));

    mFluxRefresh.subscribe(view ->
      mDataService.load(0, PAGE_SIZE)
        .publishOn(Schedulers.fromExecutor(mRecycler::post))
        .doOnSubscribe(subscription -> mProgress.setVisibility(View.VISIBLE))
        .doFinally(f -> mProgress.setVisibility(View.INVISIBLE))
        .subscribe(
          this::setData,
          throwable -> System.out.println("ERROR: " + throwable)
        ));


    mFluxAction.subscribe(view -> {
      System.out.println("CLACK");
    });

        /*
    DataService.shuffle(0, PAGE_SIZE)
        .publishOn(Schedulers.fromExecutor(mRecycler::post))
        .subscribe(
          this::setData,
          throwable -> System.out.println("ERROR: " + throwable)
        )
    **/
  }

  @SuppressWarnings("unchecked")
  void setData(@NonNull DataService.Content[] value) {
    /*
    ((PagedListAdapter<Content, RecyclerView.ViewHolder>) requireNonNull(mRecycler.getAdapter()))
      .submitList(Arrays.asList(value));
      */
  }

}
