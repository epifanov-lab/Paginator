package com.example.paginationpolygon.player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;

import com.example.paginationpolygon.R;
import com.example.paginationpolygon.matrix.PlayerTextureView;

import java.util.List;
import java.util.Map;

/**
 * @author Konstantin Epifanov
 * @since 05.07.2019
 */
public class FullPlayerFragment extends Fragment {

  //private PlayerView mPlayerView;

  private View mImageTest;

  private PlayerTextureView mTextureView;

  public static FullPlayerFragment newInstance() {
    return new FullPlayerFragment();
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_full_player, container, false);

    System.out.println("FullPlayerFragment.onCreateView");

    //mPlayerView = root.findViewById(R.id.player_view);
    mTextureView = root.findViewById(R.id.texture_end);

    mImageTest = root.findViewById(R.id.test_end);

    setEnterSharedElementCallback(new SharedElementCallback() {
      @Override
      public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        System.out.println("FULL ENTER: onSharedElementStart: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], sharedElementSnapshots = [" + sharedElementSnapshots + "]");
        super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
      }

      @Override
      public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        System.out.println("FULL ENTER: onSharedElementEnd: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], sharedElementSnapshots = [" + sharedElementSnapshots + "]");
        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
      }

      @Override
      public void onRejectSharedElements(List<View> rejectedSharedElements) {
        System.out.println("FULL ENTER: onRejectSharedElements: rejectedSharedElements = [" + rejectedSharedElements + "]");
        super.onRejectSharedElements(rejectedSharedElements);
      }

      @Override
      public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        System.out.println("FULL ENTER: onMapSharedElements: names = [" + names + "], sharedElements = [" + sharedElements + "]");

        /*if (mPlayerView.getPlayer() == null) {
          mPlayerView.setPlayer(ExoHolder.get(getContext()));
        } else mPlayerView.setPlayer(null);*/

        super.onMapSharedElements(names, sharedElements);
      }

      @Override
      public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
        System.out.println("FULL ENTER: onCaptureSharedElementSnapshot: sharedElement = [" + sharedElement + "], viewToGlobalMatrix = [" + viewToGlobalMatrix + "], screenBounds = [" + screenBounds + "]");
        return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
      }

      @Override
      public View onCreateSnapshotView(Context context, Parcelable snapshot) {
        System.out.println("FULL ENTER: onCreateSnapshotView: context = [" + context + "], snapshot = [" + snapshot + "]");
        return super.onCreateSnapshotView(context, snapshot);
      }

      @Override
      public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
        System.out.println("FULL ENTER: onSharedElementsArrived: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], listener = [" + listener + "]");
        super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
      }
    });

    setExitSharedElementCallback(new SharedElementCallback() {
      @Override
      public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        System.out.println("FULL EXIT: onSharedElementStart: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], sharedElementSnapshots = [" + sharedElementSnapshots + "]");
        super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
      }

      @Override
      public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        System.out.println("FULL EXIT: onSharedElementEnd: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], sharedElementSnapshots = [" + sharedElementSnapshots + "]");
        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
      }

      @Override
      public void onRejectSharedElements(List<View> rejectedSharedElements) {
        System.out.println("FULL EXIT: onRejectSharedElements: rejectedSharedElements = [" + rejectedSharedElements + "]");
        super.onRejectSharedElements(rejectedSharedElements);
      }

      @Override
      public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        System.out.println("FULL EXIT: onMapSharedElements: names = [" + names + "], sharedElements = [" + sharedElements + "]");

        /*new Handler().post(() -> {
          if (mPlayerView.getPlayer() == null) {
            mPlayerView.setPlayer(ExoHolder.get(getContext()));
          } else mPlayerView.setPlayer(null);
        });*/

        super.onMapSharedElements(names, sharedElements);
      }

      @Override
      public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
        System.out.println("FULL EXIT: onCaptureSharedElementSnapshot: sharedElement = [" + sharedElement + "], viewToGlobalMatrix = [" + viewToGlobalMatrix + "], screenBounds = [" + screenBounds + "]");
        return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
      }

      @Override
      public View onCreateSnapshotView(Context context, Parcelable snapshot) {
        System.out.println("FULL EXIT: onCreateSnapshotView: context = [" + context + "], snapshot = [" + snapshot + "]");
        return super.onCreateSnapshotView(context, snapshot);
      }

      @Override
      public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
        System.out.println("FULL EXIT: onSharedElementsArrived: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], listener = [" + listener + "]");
        super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
      }
    });

    System.out.println("FullPlayerFragment.onCreateView: Enter " + getSharedElementEnterTransition());
    System.out.println("FullPlayerFragment.onCreateView: Return " + getSharedElementReturnTransition());

    return root;
  }

  @Override
  public void onStart() {
    super.onStart();
    System.out.println("FullPlayerFragment.onStart");
    //mPlayerView.setPlayer(ExoHolder.get(getContext()));
  }

  @Override
  public void onResume() {
    super.onResume();
    System.out.println("FullPlayerFragment.onResume");
    //mPlayerView.setPlayer(ExoHolder.get(getContext()));
    ExoHolder.get(getContext(), null).setVideoTextureView(mTextureView);
  }

  @Override
  public void onPause() {
    super.onPause();
    System.out.println("FullPlayerFragment.onPause");
  }

  @Override
  public void onStop() {
    super.onStop();
    System.out.println("FullPlayerFragment.onStop");
    //mPlayerView.setPlayer(null);
  }
}
