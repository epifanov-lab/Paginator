package com.example.paginationpolygon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import reactor.core.publisher.Flux;

import static com.example.paginationpolygon.Utils.toFlux;
import static java.util.Objects.requireNonNull;


public class SampleActivity extends AppCompatActivity {

  private static final int ITEM_LAYOUT = R.layout.item_1;
  public Flux<View> mFluxRefresh, mFluxRestart;
  public Flux<View> mFluxAdd, mFluxDelete, mFluxChange;
  private RecyclerView mRecycler;
  private View mProgress;
  private SamplePresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pagination);

    mFluxRefresh = toFlux(findViewById(R.id.button_refresh));
    mFluxRestart = toFlux(findViewById(R.id.button_restart));
    mFluxAdd = toFlux(findViewById(R.id.button_add));
    mFluxDelete = toFlux(findViewById(R.id.button_delete));
    mFluxChange = toFlux(findViewById(R.id.button_change));

    mRecycler = findViewById(R.id.recycler);

    mProgress = findViewById(R.id.progress);

    mRecycler.setAdapter(Utils.getPagedAdapter(LayoutInflater.from(getApplicationContext()), ITEM_LAYOUT));
    mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()) {
      @Override
      public boolean supportsPredictiveItemAnimations() {
        return false;
      }
    });

    mRecycler.setItemViewCacheSize(0);
    //mRecycler.setItemAnimator(null);

    mPresenter = new SamplePresenter(this);

  }

  @SuppressWarnings("unchecked")
  public void submitList(PagedList<Item> list) {
    ((PagedListAdapter<Item, RecyclerView.ViewHolder>)
      requireNonNull(mRecycler.getAdapter())).submitList(list);
  }

  @SuppressWarnings("unchecked")
  public int getCurrentRecyclerTopPosition() {
    try {
      LinearLayoutManager manager = (LinearLayoutManager) requireNonNull(mRecycler.getLayoutManager());
      int result = manager.findFirstVisibleItemPosition();
      int result2 = mRecycler.findViewHolderForLayoutPosition(result).getAdapterPosition();
      return (((PagedListAdapter<Item, RecyclerView.ViewHolder>) mRecycler.getAdapter())
        .getCurrentList()).snapshot().get(result2).getPosition();
      //return result2;
    } catch (Throwable t) {
      System.out.println("EXCEPTION " + t);
      return -1;
    }
  }

  public void showLoadingIndicator(boolean show) {
    mProgress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
  }

  public void restart() {
    Intent intent = getIntent();
    finish();
    startActivity(intent);
  }
}
