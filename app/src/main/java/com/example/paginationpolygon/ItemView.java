package com.example.paginationpolygon;

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

import java.util.function.Consumer;

/**
 * @author Konstantin Epifanov
 * @since 24.06.2019
 */
public class ItemView extends RelativeLayout implements Consumer<DataService.Content> {

  /** The default attr resource. */
  @AttrRes
  private static final int DEFAULT_ATTRS = 0;

  /** The empty style resource. */
  @StyleRes
  private static final int DEFAULT_STYLE = 0;

  /** Default styleable attributes */
  @StyleableRes
  private static final int[] DEFAULT_STYLEABLE = new int[0];

  private ViewGroup mContainerItem;
  private TextView mContent;

  public ItemView(Context context) {
    this(context, null);
  }

  public ItemView(Context context, AttributeSet attrs) {
    this(context, attrs, DEFAULT_ATTRS);
  }

  public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, DEFAULT_STYLE);
  }

  public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
    mContainerItem = findViewById(R.id.container_item);
    mContent = findViewById(R.id.content);
  }

  @Override
  public void accept(DataService.Content content) {
    //System.out.println("item.accept: " + content);
    mContent.setText(content.getText());
    mContent.setBackgroundColor(content.getColor());
  }
}
