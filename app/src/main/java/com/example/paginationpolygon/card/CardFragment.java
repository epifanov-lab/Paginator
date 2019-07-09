package com.example.paginationpolygon.card;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.paginationpolygon.MainActivity;
import com.example.paginationpolygon.R;
import com.example.paginationpolygon.player.ExoHolder;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

/**
 * @author Konstantin Epifanov
 * @since 09.07.2019
 */
public class CardFragment extends Fragment {

  private PlayerCardView mPlayerCardView;

  private SimpleExoPlayer player;

  public static CardFragment newInstance() {
    return new CardFragment();
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_card, container, false);

    System.out.println("CardFragment.onCreateView");

    mPlayerCardView = root.findViewById(R.id.player_card);

    root.findViewById(R.id.button_url_1).setOnClickListener(v -> button_url_1());
    root.findViewById(R.id.button_url_2).setOnClickListener(v -> button_url_2());
    root.findViewById(R.id.button_url_3).setOnClickListener(v -> button_utl_3());
    root.findViewById(R.id.button_url_rapid).setOnClickListener(v -> button_utl_rapid());
    root.findViewById(R.id.button_restart).setOnClickListener(v -> button_restart());
    root.findViewById(R.id.button_play).setOnClickListener(v -> button_play());
    root.findViewById(R.id.button_stop).setOnClickListener(v -> button_stop());

    player = ExoHolder.get(getContext(), "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8");

    player.addListener(new Player.EventListener() {
      @Override
      public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
        System.out.println("onTimelineChanged: timeline = [" + timeline + "], manifest = [" + manifest + "], reason = [" + reason + "]");
      }

      @Override
      public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        System.out.println("onTracksChanged: trackGroups = [" + trackGroups + "], trackSelections = [" + trackSelections + "]");
      }

      @Override
      public void onLoadingChanged(boolean isLoading) {
        System.out.println("onLoadingChanged: isLoading = [" + isLoading + "]");
      }

      @Override
      public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        System.out.println("onPlayerStateChanged: playWhenReady = [" + playWhenReady + "], playbackState = [" + playbackState + "]");
      }

      @Override
      public void onRepeatModeChanged(int repeatMode) {
        System.out.println("onRepeatModeChanged: repeatMode = [" + repeatMode + "]");
      }

      @Override
      public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        System.out.println("onShuffleModeEnabledChanged: shuffleModeEnabled = [" + shuffleModeEnabled + "]");
      }

      @Override
      public void onPlayerError(ExoPlaybackException error) {
        System.out.println("onPlayerError: error = [" + error + "]");

        switch (error.type) {
          case ExoPlaybackException.TYPE_SOURCE:
            System.out.println("TYPE_SOURCE: " + error.getSourceException().getMessage());
            break;

          case ExoPlaybackException.TYPE_RENDERER:
            System.out.println("TYPE_RENDERER: " + error.getRendererException().getMessage());
            break;

          case ExoPlaybackException.TYPE_UNEXPECTED:
            System.out.println("TYPE_UNEXPECTED: " + error.getUnexpectedException().getMessage());
            break;
        }

      }

      @Override
      public void onPositionDiscontinuity(int reason) {
        System.out.println("onPositionDiscontinuity: reason = [" + reason + "]");
      }

      @Override
      public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        System.out.println("onPlaybackParametersChanged: playbackParameters = [" + playbackParameters + "]");
      }

      @Override
      public void onSeekProcessed() {
        System.out.println("onSeekProcessed: ");
      }
    });

    return root;
  }

  private void button_play() {
    mPlayerCardView.setPlayer(player);
  }

  private void button_stop() {
    mPlayerCardView.setPlayer(null);
  }

  private void button_url_1() {
    System.out.println("CardFragment.button_url_1");
    mPlayerCardView.setImageBackground(getRandomImageUrl(1));
  }

  private void button_url_2() {
    System.out.println("CardFragment.button_url_2");
    mPlayerCardView.setImageBackground(getRandomImageUrl(2));
  }

  private void button_utl_3() {
    System.out.println("CardFragment.button_utl_3");
    mPlayerCardView.setImageBackground(getRandomImageUrl(3));
  }

  private void button_utl_rapid() {
    System.out.println("CardFragment.button_utl_rapid");
  }

  private void button_restart() {
    ((MainActivity) getActivity()).restart();
  }

  /** Returns random dummy image URL */
  private String getRandomImageUrl(int pagingOffset) {
    pagingOffset += 10;
    return "https://picsum.photos/id/" + pagingOffset + "/600/800";
  }

}
