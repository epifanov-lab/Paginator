package com.example.paginationpolygon.player;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.paginationpolygon.R;
import com.google.android.exoplayer2.ui.PlayerView;

/**
 * @author Konstantin Epifanov
 * @since 05.07.2019
 */
public class FullPlayerFragment extends Fragment {

  private PlayerView mPlayerView;

  public static FullPlayerFragment newInstance() {
    return new FullPlayerFragment();
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_full_player, null);

    mPlayerView = root.findViewById(R.id.player_view);

    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }
}
