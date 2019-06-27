package com.example.paginationpolygon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import reactor.core.publisher.Flux;

import static com.example.paginationpolygon.Utils.toFlux;
import static java.util.Objects.requireNonNull;


public class SampleActivity extends AppCompatActivity {

  private static final int ITEM_LAYOUT = R.layout.item_1;

  private RecyclerView mRecycler;
  private View mProgress;

  Flux<View> mFluxRefresh, mFluxAction;

  private SamplePresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pagination);

    mFluxRefresh = toFlux(findViewById(R.id.button_refresh));
    mFluxAction = toFlux(findViewById(R.id.button_action));

    mRecycler = findViewById(R.id.recycler);
    mProgress = findViewById(R.id.progress);

    mRecycler.setAdapter(Utils.getPagedAdapter(LayoutInflater.from(getApplicationContext()), ITEM_LAYOUT));
    mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    mPresenter = new SamplePresenter(this, new DataService());

    mFluxAction.doOnNext(view -> {
      Intent intent = getIntent();
      finish();
      startActivity(intent);
    }).subscribe();
  }

  @SuppressWarnings("unchecked")
  public void submitList(PagedList<DataService.Content> list) {
    ((PagedListAdapter<DataService.Content, RecyclerView.ViewHolder>)
      requireNonNull(mRecycler.getAdapter())).submitList(list);
  }

  @SuppressWarnings("unchecked")
  public int getCurrentRecyclerTopPosition() {
    try {
      LinearLayoutManager manager = (LinearLayoutManager) requireNonNull(mRecycler.getLayoutManager());
      int result = Math.max(manager.findFirstVisibleItemPosition(), 0);
      int result2 = mRecycler.findViewHolderForLayoutPosition(result).getAdapterPosition();
      return (((PagedListAdapter<DataService.Content, RecyclerView.ViewHolder>) mRecycler.getAdapter())
        .getCurrentList()).snapshot().get(result2).getPosition();
    } catch (Throwable t) {
      System.out.println("EXCEPTION " + t);
      return 0;
    }
  }

  public void showLoadingIndicator(boolean show) {
    mProgress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
  }
}
