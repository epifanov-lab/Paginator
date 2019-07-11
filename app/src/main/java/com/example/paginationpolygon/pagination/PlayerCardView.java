package com.example.paginationpolygon.pagination;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.TextureView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.paginationpolygon.R;
import com.example.paginationpolygon.player.ExoHolder;
import com.example.paginationpolygon.utills.DrawableTarget;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.function.Consumer;

/**
 * @author Konstantin Epifanov
 * @since 09.07.2019
 */
public class PlayerCardView extends FrameLayout implements Consumer<Item> {

  private TextureView mTextureView;
  private TextView mLabelView;
  private DrawableTarget background;

  private Item data = null;
  private boolean isActive = false;

  public PlayerCardView(Context context) {
    this(context, null);
  }

  public PlayerCardView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PlayerCardView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

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
    if (player != null) {
      player.setVideoTextureView(mTextureView);
      mTextureView.animate().alpha(1f).setDuration(300);
    } else {
      mTextureView.animate().alpha(0f).setDuration(300);
    }
  }

  public void setActive(boolean state) {
    // TODO ОЧЕНЬ ОПТИМИЗИРОВАННЫЙ МЕТОД
    if (state == isActive) return;
    isActive = state;
    setPlayer(isActive ? ExoHolder.get(getContext(), data.getUrl()) : null);
  }

  @Override
  public void accept(Item item) {
    System.out.println("data = [" + item + "]");
    this.data = item;
    mLabelView.setText(String.format("pos[%s]\nurl[%s]", item.getText(), item.getUrl()));
    setImageBackground(item.getBackgroundUrl());
  }
}
