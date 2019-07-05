package com.example.paginationpolygon.pagination;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paginationpolygon.MainActivity;
import com.example.paginationpolygon.R;
import com.example.paginationpolygon.utills.Utils;

import reactor.core.publisher.Flux;

import static com.example.paginationpolygon.utills.Utils.toFlux;
import static java.util.Objects.requireNonNull;

/**
 * @author Konstantin Epifanov
 * @since 05.07.2019
 */
public class PaginationFragment extends Fragment {

  private static final int ITEM_LAYOUT = R.layout.item_pagination;

  public Flux<View> mFluxRefresh, mFluxRestart;
  public Flux<View> mFluxAdd, mFluxDelete, mFluxChange;

  private RecyclerView mRecycler;
  private View mProgress;
  private PaginationPresenter mPresenter;

  public static PaginationFragment newInstance() {
    return new PaginationFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_pagination, null);

    mFluxRefresh = toFlux(root.findViewById(R.id.button_refresh));
    mFluxRestart = toFlux(root.findViewById(R.id.button_restart));
    mFluxAdd = toFlux(root.findViewById(R.id.button_add));
    mFluxDelete = toFlux(root.findViewById(R.id.button_delete));
    mFluxChange = toFlux(root.findViewById(R.id.button_change));

    mRecycler = root.findViewById(R.id.recycler);

    mProgress = root.findViewById(R.id.progress);

    mRecycler.setAdapter(Utils.getPagedAdapter(LayoutInflater.from(getContext()), ITEM_LAYOUT));
    mRecycler.setLayoutManager(new LinearLayoutManager(getContext()) {
      @Override
      public boolean supportsPredictiveItemAnimations() {
        return false;
      }
    });

    mRecycler.setItemViewCacheSize(0);
    //mRecycler.setItemAnimator(null);

    mPresenter = new PaginationPresenter(this);

    return root;
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
    ((MainActivity) getActivity()).restart();
  }
}
