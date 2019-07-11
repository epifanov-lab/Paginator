package com.example.paginationpolygon.draft;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

import com.example.paginationpolygon.utills.RatioKeeper;

public final class MyTexture extends TextureView {

  /** Aspect Ratio Keeper. */
  private final RatioKeeper mRatioKeeper =
    new RatioKeeper(this::setTransform);

  public MyTexture(Context context) {
    super(context);init();
  }

  public MyTexture(Context context, AttributeSet attrs) {
    super(context, attrs);init();
  }

  public MyTexture(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);init();
  }

  public MyTexture(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    /*postDelayed(new Runnable() {
      @Override
      public void run() {*/
        mRatioKeeper.videoSize(1920, 1080);
      /*}
    }, 3000);*/
  }

  /** {@inheritDoc} */
  @Override protected final void onSizeChanged(int nw, int nh, int ow, int oh) {
    mRatioKeeper.viewPort(nw, nh);
    super.onSizeChanged(nw, nh, ow, oh);
  }

  /** {@inheritDoc} */
  @Override public final void setScaleX(float value) {
    mRatioKeeper.scaleX(value);
    super.setScaleX(value);
  }

  @Override public final void setScaleY(float value) {
    mRatioKeeper.scaleY(value);
    super.setScaleY(value);
  }


}
