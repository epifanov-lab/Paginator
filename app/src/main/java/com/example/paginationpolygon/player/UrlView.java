package com.example.paginationpolygon.player;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;

import com.example.paginationpolygon.R;
import com.example.paginationpolygon.pagination.Item;

import java.util.function.Consumer;

/**
 * @author Konstantin Epifanov
 * @since 24.06.2019
 */
public class UrlView extends RelativeLayout implements Consumer<String> {

  /** The default attr resource. */
  @AttrRes
  private static final int DEFAULT_ATTRS = 0;

  /** The empty style resource. */
  @StyleRes
  private static final int DEFAULT_STYLE = 0;

  /** Default styleable attributes */
  @StyleableRes
  private static final int[] DEFAULT_STYLEABLE = new int[0];

  private TextView mTextView;

  public UrlView(Context context) {
    this(context, null);
  }

  public UrlView(Context context, AttributeSet attrs) {
    this(context, attrs, DEFAULT_ATTRS);
  }

  public UrlView(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, DEFAULT_STYLE);
  }

  public UrlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    final Resources.Theme theme = context.getTheme();
    final TypedArray attributes = theme.obtainStyledAttributes(attrs, DEFAULT_STYLEABLE, defStyleAttr, defStyleRes);
    try {
      //ignore
    } finally {
      attributes.recycle();
    }
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    mTextView = findViewById(R.id.text_item);
  }

  @Override
  public void accept(String url) {
    mTextView.setText(url);
  }

}
