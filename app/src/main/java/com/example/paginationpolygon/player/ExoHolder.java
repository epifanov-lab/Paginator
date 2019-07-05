package com.example.paginationpolygon.player;

import android.content.Context;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;

/**
 * @author Konstantin Epifanov
 * @since 05.07.2019
 */
public class ExoHolder {

  private static ExoHolder instance = null;
  private ExoPlayer player;

  private ExoHolder(Context context) {
    player = ExoPlayerFactory.newSimpleInstance(context);
    player.setPlayWhenReady(true);
  }

  public static ExoPlayer get(Context context) {
    if (instance == null) {
      instance = new ExoHolder(context);
    }

    return instance.player;
  }

}
