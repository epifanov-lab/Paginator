package com.example.paginationpolygon.utills;

import android.graphics.Matrix;

import java.util.function.Consumer;

/**
 * Aspect Ratio Keeper.
 *
 * @author Gleb Nikitenko
 * @since 10.07.19
 */
public final class RatioKeeper {

  /** Transformation Matrix. */
  private final Matrix mMatrix = new Matrix();

  /** Sizes container */
  private final float[] mSizes;

  /** Matrix applier. */
  private final Consumer<Matrix> mApplier;

  /**
   * Constructs a new {@link RatioKeeper}.
   *
   * @param applier matrix applier.
   */
  public RatioKeeper(Consumer<Matrix> applier) {
    mSizes = new float[]{0f, 0f, 0f, 0f, 1f, 1f};
    mApplier = applier;
  }

  /**
   * @param width  video width
   * @param height video height
   */
  public final void videoSize(int width, int height) {
    if ((int) mSizes[0] != width || (int) mSizes[1] != height) {
      mSizes[0] = width;
      mSizes[1] = height;
      invalidate();
    }
  }

  /**
   * @param width  view width
   * @param height view height
   */
  public final void viewPort(int width, int height) {
    if ((int) mSizes[2] != width || (int) mSizes[3] != height) {
      mSizes[2] = width;
      mSizes[3] = height;
      invalidate();
    }
  }

  /** @param value horizontal scale */
  public final void scaleX(float value) {
    if (mSizes[4] != value) {
      mSizes[4] = value;
      invalidate();
    }
  }

  /** @param value vertical scale */
  public final void scaleY(float value) {
    if (mSizes[5] != value) {
      mSizes[5] = value;
      invalidate();
    }
  }

  /** Invalidate the matrix */
  private void invalidate() {

    // Backup sizes before fit
    final float
      videoWidth = mSizes[0],
      videoHeight = mSizes[1],
      viewWidth = mSizes[2],
      viewHeight = mSizes[3];

    // Calc fitting for matrix
    fit(mSizes);

    // Apply fitting on matrix
    mMatrix.setScale(
      mSizes[0],
      mSizes[1],
      mSizes[2],
      mSizes[3]
    );

    // Restore sizes back to transform params
    mSizes[0] = videoWidth;
    mSizes[1] = videoHeight;
    mSizes[2] = viewWidth;
    mSizes[3] = viewHeight;

    // Apply new modified matrix for rendering
    mApplier.accept(mMatrix);
  }

  /** @param value floats container */
  private static void fit(float[] value) {
    float
      a = value[0] * value[3],
      b = value[2] * value[1];
    value[0] = value[4];
    value[1] = value[5];
    value[2] *= 0.5f;
    value[3] *= 0.5f;
    if (a > b)
      value[0] = a / b;
    else value[1] = b / a;
  }
}
