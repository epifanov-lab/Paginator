package com.example.paginationpolygon.matrix;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.paginationpolygon.MainActivity;
import com.example.paginationpolygon.R;
import com.example.paginationpolygon.player.ExoHolder;

/**
 * @author Konstantin Epifanov
 * @since 09.07.2019
 */
public class MatrixFragment extends Fragment {

  private TextureView mTextureView1, mTextureView2, mTextureView3;

  public static MatrixFragment newInstance() {
    return new MatrixFragment();
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_matrix, container, false);

    System.out.println("MatrixFragment.onCreateView");

    mTextureView1 = root.findViewById(R.id.texture_1);
    mTextureView2 = root.findViewById(R.id.texture_2);
    mTextureView3 = root.findViewById(R.id.texture_3);

    mTextureView1.setOnClickListener(v -> ((MainActivity) getActivity()).goToFull(this, v));
    mTextureView2.setOnClickListener(v -> ((MainActivity) getActivity()).goToFull(this, v));
    mTextureView3.setOnClickListener(v -> ((MainActivity) getActivity()).goToFull(this, v));

    /*
    ExoHolder.getPlayerByUrl(getContext(), "https://media.webka.com/hls/vod/39.mp4/index.m3u8").setVideoTextureView(mTextureView1);
    ExoHolder.getPlayerByUrl(getContext(), "https://media.webka.com/hls/vod/30.mp4/index.m3u8").setVideoTextureView(mTextureView2);
    ExoHolder.getPlayerByUrl(getContext(), "https://media.webka.com/hls/vod/37.mp4/index.m3u8").setVideoTextureView(mTextureView3);
    */

    //ExoHolder.get(getContext(), "https://media.webka.com/hls/vod/39.mp4/index.m3u8").setVideoTextureView(mTextureView1);

    return root;
  }

  @Override
  public void onResume() {
    super.onResume();
    System.out.println("MatrixFragment.onCreateView");
  }
}
