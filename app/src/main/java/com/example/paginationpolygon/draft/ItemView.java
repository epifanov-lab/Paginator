package com.example.paginationpolygon.draft;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
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
public class ItemView extends RelativeLayout implements Consumer<Item>, Runnable {

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
  private TextView mTextView;

  private boolean isActive = false;

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
    mTextView = findViewById(R.id.text_item);
  }
  @Override
  public void accept(Item item) {
    //System.out.println("ItemView.accept: " + hashCode() + " || item: " + item.getId());
    mTextView.setText(item.getText());
    mTextView.setBackgroundColor(item.getColor());
  }

  @Override
  public void run() {
    //System.out.println("ItemView.onUnbind: " + this.hashCode());
  }

  public void setActive(boolean state) {
    // TODO ОЧЕНЬ ОПТИМИЗИРОВАННЫЙ МЕТОД
    if (state == isActive) return;
    isActive = state;
    mTextView.setBackgroundColor(isActive ? Color.BLUE : Color.GRAY);
  }

  public void showWindowYPos() {
    int[] pos = new int[2];
    getLocationInWindow(pos);
    int y = pos[1];
    int cy = y + getHeight() / 2;
    mTextView.setText(String.format("y[%d] cy[%d]", y, cy));
  }
}
