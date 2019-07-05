package com.example.paginationpolygon.player;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.TextureView;

import androidx.annotation.AttrRes;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;

/**
 * @author Konstantin Epifanov
 * @since 04.07.2019
 */
public class PlayerTextureView extends TextureView {

  /** The default attr resource. */
  @AttrRes
  private static final int DEFAULT_ATTRS = 0;

  /** The empty style resource. */
  @StyleRes
  private static final int DEFAULT_STYLE = 0;

  /** Default styleable attributes */
  @StyleableRes
  private static final int[] DEFAULT_STYLEABLE = new int[0];

  public PlayerTextureView(Context context) {
    this(context, null);
  }

  public PlayerTextureView(Context context, AttributeSet attrs) {
    this(context, attrs, DEFAULT_ATTRS);
  }

  public PlayerTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, DEFAULT_STYLE);
  }

  PlayerTextureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    final Resources.Theme theme = context.getTheme();
    final TypedArray attributes = theme.obtainStyledAttributes(attrs, DEFAULT_STYLEABLE, defStyleAttr, defStyleRes);
    try {
      //ignore
    } finally {
      attributes.recycle();
    }
  }
}
