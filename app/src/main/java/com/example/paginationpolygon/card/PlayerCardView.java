package com.example.paginationpolygon.card;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.paginationpolygon.R;
import com.example.paginationpolygon.utills.DrawableTarget;
import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * @author Konstantin Epifanov
 * @since 09.07.2019
 */
public class PlayerCardView extends RelativeLayout {

  private SimpleExoPlayer mPlayer;
  private TextureView mTextureView;

  private TextView mLabelView;

  private DrawableTarget background;

  public PlayerCardView(Context context) {
    this(context, null);
  }

  public PlayerCardView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PlayerCardView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    System.out.println("PlayerCardView.PlayerCardView");

    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    inflater.inflate(R.layout.item_player_card, this, true);
    mTextureView = findViewById(R.id.texture);
    mLabelView = findViewById(R.id.label_user_info);

    background = new DrawableTarget(getResources(), 16, -1, -1, Color.GREEN);
    setBackground(background);

    //mTextureView.setOpaque(false);

  }

  public void setImageBackground(String url) {
    background.setData(url.getBytes());
    mLabelView.setText(url);
  }

  public void setPlayer(SimpleExoPlayer player) {
    if (player != null) {
      player.setVideoTextureView(mTextureView);
      mTextureView.animate().alpha(1f).setDuration(300);
    } else {
      //if (mPlayer != null) mPlayer.setVideoTextureView(null);
      mTextureView.animate().alpha(0f).setDuration(300);
    }

    mPlayer = player;
  }

}
