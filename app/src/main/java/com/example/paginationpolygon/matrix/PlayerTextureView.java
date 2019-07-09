package com.example.paginationpolygon.matrix;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.TextureView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;

/**
 * @author Konstantin Epifanov
 * @since 04.07.2019
 */
public class PlayerTextureView extends TextureView {

  public PlayerTextureView(Context context) {
    super(context);
  }

  public PlayerTextureView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public PlayerTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    System.out.println("onSizeChanged: w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
  }

  @Override
  public void setBackgroundDrawable(Drawable background) {
    super.setBackgroundDrawable(background);
  }

  @Override
  public void setBackground(Drawable background) {
    super.setBackground(background);

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
