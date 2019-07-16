package com.example.paginationpolygon.pagination;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.paginationpolygon.R;
import com.example.paginationpolygon.player.ExoHolder;
import com.example.paginationpolygon.utills.DrawableTarget;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.video.VideoListener;

import java.util.function.Consumer;

/**
 * @author Konstantin Epifanov
 * @since 09.07.2019
 */
public class PlayerCardView extends FrameLayout implements Consumer<Item>, Checkable {

  private PlayerTextureView mTextureView;
  private TextView mLabelView;
  private DrawableTarget background;

  private Item data = null;
  private boolean isActive = false;

  private static final Runnable DUMMY_CLEANER = () -> { };
  private Runnable cleaner = DUMMY_CLEANER;

  public PlayerCardView(Context context) {
    this(context, null);
  }

  public PlayerCardView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PlayerCardView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    System.out.println("CONSTRUCOR");

    background = new DrawableTarget(getResources(), 16, -1, -1, Color.GREEN);
    setBackground(background);

    setClipToOutline(true);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    mTextureView = findViewById(R.id.texture);
    mLabelView = findViewById(R.id.label_user_info);
  }

  public void setImageBackground(String url) {
    background.setData(url.getBytes());
  }

  public void setPlayer(SimpleExoPlayer player) {
    clean();

    if (player != null) {

      VideoListener listener = new VideoListener() {
        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
          mTextureView.initialize(width, height);
        }
      };

      player.addVideoListener(listener);

      player.setVideoTextureView(mTextureView);
      mTextureView.animate().alpha(1f).setDuration(300);

      cleaner = () -> {
        player.removeVideoListener(listener);
        mTextureView.animate().alpha(0f).setDuration(300);
      };
    }
  }

  private void clean() {
    cleaner.run();
    cleaner = DUMMY_CLEANER;
  }

  @Override
  public void accept(Item item) {
    this.data = item;
    mLabelView.setText(String.format("pos[%s]\nurl[%s]", item.getText(), item.getUrl()));
    setImageBackground(item.getBackgroundUrl());
  }

  public PlayerTextureView getTextureView() {
    return mTextureView;
  }

  @Override
  public void setChecked(boolean checked) {
    isActive = checked;
    setPlayer(isActive ? ExoHolder.get(getContext(), data.getUrl()) : null);
  }

  @Override
  public boolean isChecked() {
    return isActive;
  }

  @Override
  public void toggle() {
    isActive = !isActive;
  }

/*
* *//** {@inheritDoc} *//*
  @Override
  public void offsetTopAndBottom(int offset) {
    final boolean lower = getTop() > 0;
    final boolean upper = getBottom() < 0;

    super.offsetTopAndBottom(offset);

    final boolean newInline = getTop() < 0 && getBottom() > 0;

    if (newInline) {
      if (lower) setTranslationY(-getTop());
      else if (upper) setTranslationY(-getTop());
      else setTranslationY(getTranslationY() - offset);
    } else {
      setTranslationY(0);
    }
  }

  @Override public void setTranslationY(float translationY) {
    super.setTranslationY(translationY);
    float a = (1.5f * (3000.0f - getBottom()) / 1000.0f) - 0.75f;
    mLabelView.setAlpha(a);
  }
  */
}
