package com.example.paginationpolygon.player;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paginationpolygon.MainActivity;
import com.example.paginationpolygon.PagerFragment;
import com.example.paginationpolygon.R;
import com.example.paginationpolygon.pagination.PlayerTextureView;
import com.example.paginationpolygon.utills.OnSwipeTouchListener;
import com.example.paginationpolygon.utills.RecyclerTouchHelper;
import com.example.paginationpolygon.utills.Utils;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.video.VideoListener;

import java.util.List;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

import static com.example.paginationpolygon.utills.Utils.toFlux;
import static java.util.Objects.requireNonNull;

/**
 * @author Konstantin Epifanov
 * @since 05.07.2019
 */
public class PlayersTabView extends ConstraintLayout {

  private static final int ITEM_LAYOUT = R.layout.item_url;

  public Flux<View> mFluxRestart;
  public Flux<View> mFluxSwap, mFluxBigger, mFluxSmaller, mFluxScale;
  public Flux<View> mFluxStart, mFluxStop;

  public DirectProcessor<Integer> mUrlClicks = DirectProcessor.create();

  private View mProgress;

  private PlayerView mPlayerView_1;
  private PlayerView mPlayerView_2;
  private PlayerView currentPlayer;

  private RecyclerView mRecyclerUrls;

  private View mContainerMain;
  private View mPlayerContainer;

  private PlayerTextureView mTextureView1;

  private PlayerPresenter mPresenter;

  private View test;

  private static final Runnable DUMMY_CLEANER = () -> { };
  private Runnable cleaner = DUMMY_CLEANER;

  public PlayersTabView(Context context) {
    this(context, null);
  }

  public PlayersTabView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PlayersTabView(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public PlayersTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  @SuppressLint("ClickableViewAccessibility")
  protected void onFinishInflate() {
    super.onFinishInflate();

    mPlayerView_1 = this.findViewById(R.id.player_view_1);
    mPlayerView_2 = this.findViewById(R.id.player_view_2);
    currentPlayer = mPlayerView_1;

    mContainerMain = this.findViewById(R.id.container_player_main);
    mPlayerContainer = this.findViewById(R.id.player_container);
    mTextureView1 = this.findViewById(R.id.texture_1);

    test = this.findViewById(R.id.test_start);

    mPlayerView_2.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
      @Override
      public void onClick() {
        super.onClick();
        startFullPlayerActivity();
      }
    });

    mProgress = this.findViewById(R.id.progress);
    mRecyclerUrls = this.findViewById(R.id.recycler_urls);

    mFluxRestart = toFlux(this.findViewById(R.id.button_restart));
    mFluxSwap = toFlux(this.findViewById(R.id.button_swap));
    mFluxStart = toFlux(this.findViewById(R.id.button_start));
    mFluxStop = toFlux(this.findViewById(R.id.button_stop));
    mFluxBigger = toFlux(this.findViewById(R.id.button_bigger));
    mFluxSmaller = toFlux(this.findViewById(R.id.button_smaller));
    mFluxScale = toFlux(this.findViewById(R.id.button_scale));

    mRecyclerUrls.setAdapter(Utils.getSimpleAdapter(LayoutInflater.from(getContext()), ITEM_LAYOUT));
    mRecyclerUrls.setLayoutManager(new LinearLayoutManager(getContext()));
    new RecyclerTouchHelper(mRecyclerUrls, mUrlClicks::onNext);


    mPresenter = new PlayerPresenter(this);

  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    setPlayer(ExoHolder.get(getContext()));
  }

  public void onClickUrl(String url) {
    System.out.println("URL: " + url);
    //ExoHolder.get(getContext(), url);
    //mPlayerView_2.setPlayer(ExoHolder.get(getContext(), url));
    setPlayer(ExoHolder.get(getContext(), url));
  }

  public void onClickStart() {
    System.out.println("PlayerActivity.onClickStart");
    if (ExoHolder.get(getContext()) != null) ExoHolder.get(getContext()).setPlayWhenReady(true);
  }

  public void onClickStop() {
    System.out.println("PlayerActivity.onClickStop");
    if (ExoHolder.get(getContext()) != null) ExoHolder.get(getContext()).setPlayWhenReady(false);
  }

  public void onClickBigger() {
    //changeSize(mPlayerView_2, 1.1f);
    changeSize(mTextureView1, 1.1f);
  }

  public void onClickSmaller() {
    //changeSize(mPlayerView_2, 0.9f);
    changeSize(mTextureView1, 0.9f);
  }

  public void onClickScale(boolean bigger) {
    System.out.println("PlayerActivity.onClickScale");
    float factor = bigger ? 1.5f : 0.5f;

    mPlayerView_2.animate()
      .scaleX(factor)
      .scaleY(factor)
      .setDuration(1000)
      .setInterpolator(new FastOutSlowInInterpolator())
    ;
  }

  private void changeSize(View v, float factor) {
    int w = v.getLayoutParams().width;
    int h = v.getLayoutParams().height;
    int nw = (int) (w * factor);
    int nh = (int) (h * factor);
    System.out.println("changeSize: " + " w" + w + " h" + h + " nw" + nw + " nh" + nh);
    v.getLayoutParams().width = nw;
    v.getLayoutParams().height = nh;
    v.requestLayout();
  }

  public void onClickSwap() {
    System.out.println("PlayerActivity.onClickSwap");
    PlayerView n = currentPlayer == mPlayerView_1 ? mPlayerView_2 : mPlayerView_1;
    PlayerView o = currentPlayer == mPlayerView_1 ? mPlayerView_1 : mPlayerView_2;
    PlayerView.switchTargetView(ExoHolder.get(getContext()), o, n);
    currentPlayer = n;
  }

  @SuppressWarnings("unchecked")
  public void submitUrls(List<String> list) {
    ((ListAdapter<String, RecyclerView.ViewHolder>)
      requireNonNull(mRecyclerUrls.getAdapter())).submitList(list);
  }

  public void showLoadingIndicator(boolean show) {
    mProgress.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
  }

  public void restart() {
    ((MainActivity) getContext()).restart();
  }

  public void startFullPlayerActivity() {
    Fragment host = ((MainActivity) getContext()).getSupportFragmentManager().findFragmentByTag(PagerFragment.class.getSimpleName());
    ((MainActivity) getContext()).goToFull(host, mTextureView1, test);
  }

  private void scaleToFullScreen(Runnable endCallback) {
    mPlayerView_2.animate()
      .scaleX(1f)
      .setDuration(1000)
      .setInterpolator(new FastOutSlowInInterpolator())
      .setListener(new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
          endCallback.run();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
      });
  }

  public void setPlayer(SimpleExoPlayer player) {
    clean();

    if (player != null) {

      VideoListener listener = new VideoListener() {
        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
          mTextureView1.initialize(width, height);
        }
      };

      player.addVideoListener(listener);

      player.setVideoTextureView(mTextureView1);
      mTextureView1.animate().alpha(1f).setDuration(300);

      cleaner = () -> {
        player.removeVideoListener(listener);
        mTextureView1.animate().alpha(0f).setDuration(300);
      };
    }
  }

  private void clean() {
    cleaner.run();
    cleaner = DUMMY_CLEANER;
  }
}
