package com.example.paginationpolygon.player;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paginationpolygon.R;
import com.example.paginationpolygon.utills.RecyclerTouchHelper;
import com.example.paginationpolygon.utills.Utils;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.DefaultHlsExtractorFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;

import static com.example.paginationpolygon.utills.Utils.toFlux;
import static java.util.Objects.requireNonNull;


public class PlayerActivity extends AppCompatActivity {

  private static final int ITEM_LAYOUT = R.layout.item_url;

  public Flux<View> mFluxRestart;
  public Flux<View> mFluxSwap, mFluxBigger, mFluxSmaller, mFluxScale;
  public Flux<View> mFluxStart, mFluxStop;

  public DirectProcessor<Integer> mUrlClicks = DirectProcessor.create();

  private View mProgress;

  private PlayerView mPlayerView_1;
  private PlayerView mPlayerView_2;
  private PlayerView currentPlayer;

  private SimpleExoPlayer mExoPlayer;

  private RecyclerView mRecyclerUrls;

  private PlayerPresenter mPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_player);

    mPlayerView_1 = findViewById(R.id.player_view_1);
    mPlayerView_2 = findViewById(R.id.player_view_2);
    currentPlayer = mPlayerView_1;

    mPlayerView_2.setOnClickListener(v -> {
      //todo
    });

    mProgress = findViewById(R.id.progress);
    mRecyclerUrls = findViewById(R.id.recycler_urls);

    mFluxRestart = toFlux(findViewById(R.id.button_restart));
    mFluxSwap = toFlux(findViewById(R.id.button_swap));
    mFluxStart = toFlux(findViewById(R.id.button_start));
    mFluxStop = toFlux(findViewById(R.id.button_stop));
    mFluxBigger = toFlux(findViewById(R.id.button_bigger));
    mFluxSmaller = toFlux(findViewById(R.id.button_smaller));
    mFluxScale= toFlux(findViewById(R.id.button_scale));

    mRecyclerUrls.setAdapter(Utils.getSimpleAdapter(LayoutInflater.from(getApplicationContext()), ITEM_LAYOUT));
    mRecyclerUrls.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    new RecyclerTouchHelper(mRecyclerUrls, mUrlClicks::onNext);

    mPresenter = new PlayerPresenter(this);

  }

  private void initializePlayer(PlayerView playerView, String url) {
    if (mExoPlayer == null) {
      mExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
      mExoPlayer.setPlayWhenReady(true); // pause or start (не влияет на флоу загрузки)
    }

    if (url != null) {
      MediaSource mediaSource = buildMediaSource(getApplicationContext(), Uri.parse(url));
      mExoPlayer.prepare(mediaSource, true, false);
    }

    playerView.setPlayer(mExoPlayer);
  }

  private MediaSource buildMediaSource(Context context, Uri uri) {
    return new HlsMediaSource
      .Factory(new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "polygon")))
      .setExtractorFactory(new DefaultHlsExtractorFactory())
      .createMediaSource(uri);
  }

  private void releasePlayer() {
    if (mExoPlayer != null) {
      mExoPlayer.stop();
      mExoPlayer.release();
      mExoPlayer = null;
    }
  }

  public void onClickUrl(String url) {
    System.out.println("URL: " + url);
    initializePlayer(currentPlayer, url);
  }

  public void onClickStart() {
    System.out.println("PlayerActivity.onClickStart");
    if (mExoPlayer != null) mExoPlayer.setPlayWhenReady(true);
  }

  public void onClickStop() {
    System.out.println("PlayerActivity.onClickStop");
    if (mExoPlayer != null) mExoPlayer.setPlayWhenReady(false);
  }

  public void onClickBigger() {
    changeSize(mPlayerView_2, 1.1f);
  }

  public void onClickSmaller() {
    changeSize(mPlayerView_2, 0.9f);
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
    PlayerView.switchTargetView(mExoPlayer, o, n);
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
    Intent intent = getIntent();
    finish();
    startActivity(intent);
  }

  @Override
  public void onPause() {
    super.onPause();
    if (mPlayerView_1 != null) mPlayerView_1.onPause();
    if (mPlayerView_2 != null) mPlayerView_2.onPause();
    releasePlayer();
  }

  @Override
  public void onStop() {
    super.onStop();
    if (mPlayerView_1 != null) mPlayerView_1.onPause();
    if (mPlayerView_2 != null) mPlayerView_2.onPause();
    releasePlayer();
  }
}
