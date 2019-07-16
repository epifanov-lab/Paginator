package com.example.paginationpolygon.pagination;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paginationpolygon.MainActivity;
import com.example.paginationpolygon.PagerFragment;
import com.example.paginationpolygon.R;
import com.example.paginationpolygon.utills.ChildViews;
import com.example.paginationpolygon.utills.RecyclerTouchHelper;
import com.example.paginationpolygon.utills.Utils;

import java.util.Comparator;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

import static com.example.paginationpolygon.utills.Utils.toFlux;
import static java.util.Objects.requireNonNull;

/**
 * @author Konstantin Epifanov
 * @since 05.07.2019
 */
public class PaginationTabView extends ConstraintLayout {

  public Flux<View> mFluxRefresh, mFluxRestart;
  public Flux<View> mFluxAdd, mFluxDelete, mFluxChange;

  private RecyclerView mRecycler;
  private View mProgress;
  private PaginationPresenter mPresenter;

  private static Checkable DUMMY_CHECKABLE = new Checkable() {
    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public boolean isChecked() {
      return false;
    }

    @Override
    public void toggle() {

    }
  };

  private Checkable mActiveCard = DUMMY_CHECKABLE;

  public DirectProcessor<Integer> mRecyclerClicks = DirectProcessor.create();

  public PaginationTabView(Context context) {
    this(context, null);
  }

  public PaginationTabView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PaginationTabView(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public PaginationTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    mProgress = this.findViewById(R.id.progress);

    mFluxRefresh = toFlux(this.findViewById(R.id.button_refresh));
    mFluxRestart = toFlux(this.findViewById(R.id.button_restart));
    mFluxAdd = toFlux(this.findViewById(R.id.button_add));
    mFluxDelete = toFlux(this.findViewById(R.id.button_delete));
    mFluxChange = toFlux(this.findViewById(R.id.button_change));

    mRecycler = this.findViewById(R.id.recycler);
    mRecycler.setAdapter(Utils.getPagedAdapter(LayoutInflater.from(getContext()), R.layout.item_player_card));

    mRecycler.setLayoutManager(new LinearLayoutManager(getContext()) {
      @Override
      public boolean supportsPredictiveItemAnimations() {
        return false;
      }
    });

    mRecycler.setItemViewCacheSize(0);
    //mRecycler.setItemAnimator(null);

    mPresenter = new PaginationPresenter(this);

    mRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //System.out.println("recyclerView onScrollStateChanged =  newState = [" + newState + "]");
      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        final int recyclerCY = mRecycler.getHeight() / 2;

        ChildViews.parallel(mRecycler)
          .min(minDistComparator(recyclerCY))
          .ifPresent(closest -> setActiveCard((Checkable) closest));
      }
    });

    //new RecyclerTouchHelper(mRecycler, mRecyclerClicks::onNext);
  }

  private void setActiveCard(Checkable v) {
    if (mActiveCard == v) return;
    ChildViews.sequential(mRecycler).forEach(current ->
      ((Checkable) current).setChecked(v == current));
    v.setChecked(true);
    mActiveCard = v;
  }

  private Comparator<View> minDistComparator(int target) {
    return (o1, o2) -> Integer.compare(getDist(o1, target), getDist(o2, target));
  }

  private int getDist(View v, int target) {
    //int[] pos = new int[2];
    //v.getLocationInWindow(pos);
    return Math.abs(((int) v.getY()) + v.getHeight() / 2 - target);
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

  public void goToFull(View v) {
    Fragment host = ((MainActivity) getContext()).getSupportFragmentManager().findFragmentByTag(PagerFragment.class.getSimpleName());
    PlayerCardView view = (PlayerCardView) v;
    view.getTextureView().setTransitionName(getResources().getString(R.string.shared_player));
    ((MainActivity) getContext()).goToFull(host, view.getTextureView());
  }

  public void restart() {
    ((MainActivity) getContext()).restart();
  }
}
