package com.example.paginationpolygon.draft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.TextureView;

import androidx.annotation.NonNull;

/**
 * @author Konstantin Epifanov
 * @since 04.07.2019
 */
public class PlayerTextureView2 extends TextureView {

  public PlayerTextureView2(Context context) {
    super(context);
  }

  public PlayerTextureView2(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public PlayerTextureView2(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    Matrix old = getTransform(null);
    matrix(old, new PointF(1920, 1280), new PointF(w, h));

    setTransform(old);

    System.out.println("w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
  }

  @Override
  public void setBackgroundDrawable(Drawable background) {
    super.setBackgroundDrawable(background);
  }

  @Override
  public void setBackground(Drawable background) {
    super.setBackground(background);
  }


  @NonNull
  static void matrix(Matrix matrix, @NonNull PointF src, @NonNull PointF dst) {

      final float
        sw = src.x,
        sh = src.y,
        dw = dst.x,
        dh = dst.y;

      float scale, dx = 0, dy = 0;

      if (sw * dh > dw * sh) {
        scale = dh / sh;
        dx = (dw - sw * scale) * 0.5f;
      } else {
        scale = dw / sw;
        dy = (dh - sh * scale) * 0.5f;
      }


      matrix.setScale(scale, scale);
      matrix.postTranslate(dx, dy);

    System.out.println("PlayerTextureView.matrix " + scale);
    System.out.println("PlayerTextureView.matrix " + dx + " " + dy);
  }

  /**
   * @param bitmap picture bitmap
   * @param bounds picture bounds
   *
   * @return fit matrix
   */
  @NonNull
  static Matrix matrix
  (@NonNull Bitmap bitmap, @NonNull RectF bounds) {
    return new Matrix() {{
      final float
        sw = bitmap.getWidth(),
        sh = bitmap.getHeight(),
        dw = bounds.width(),
        dh = bounds.height();

      float scale, dx = 0, dy = 0;

      if (sw * dh > dw * sh) {
        scale = dh / sh;
        dx = (dw - sw * scale) * 0.5f;
      } else {
        scale = dw / sw;
        dy = (dh - sh * scale) * 0.5f;
      }

      setScale(scale, scale);
      postTranslate(dx, dy);
    }};
  }
}
