package com.example.paginationpolygon.player;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.paginationpolygon.R;
import com.google.android.exoplayer2.ui.PlayerView;

/**
 * @author Konstantin Epifanov
 * @since 04.07.2019
 */
public class FullPlayerActivity extends Activity {

  private PlayerView mPlayerView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPlayerView = findViewById(R.id.player_view);
  }
}
