package com.example.paginationpolygon;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Map;

/**
 * @author Konstantin Epifanov
 * @since 08.07.2019
 */
public class PagerFragment extends Fragment {

  private ViewPager mPager;

  public static PagerFragment newInstance() {
    return new PagerFragment();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_pager, container, false);
    mPager = root.findViewById(R.id.view_pager);

    mPager.setAdapter(
      new PagerAdapter() {
        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
          LayoutInflater inflater = LayoutInflater.from(getContext());
          ViewGroup layout = (ViewGroup) inflater.inflate(tabs.values()[position].layout, collection, false);
          collection.addView(layout);
          return layout;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
          collection.removeView((View) view);
        }

        @Override
        public int getCount() {
          return tabs.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
          return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
          return tabs.values()[position].name();
        }
      }
    );


    setEnterSharedElementCallback(new SharedElementCallback() {
      @Override
      public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        System.out.println("PAGER ENTER: onSharedElementStart: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], sharedElementSnapshots = [" + sharedElementSnapshots + "]");
        super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
      }

      @Override
      public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        System.out.println("PAGER ENTER: onSharedElementEnd: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], sharedElementSnapshots = [" + sharedElementSnapshots + "]");
        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
      }

      @Override
      public void onRejectSharedElements(List<View> rejectedSharedElements) {
        System.out.println("PAGER ENTER: onRejectSharedElements: rejectedSharedElements = [" + rejectedSharedElements + "]");
        super.onRejectSharedElements(rejectedSharedElements);
      }

      @Override
      public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        System.out.println("PAGER ENTER: onMapSharedElements: names = [" + names + "], sharedElements = [" + sharedElements + "]");
        super.onMapSharedElements(names, sharedElements);
      }

      @Override
      public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
        System.out.println("PAGER ENTER: onCaptureSharedElementSnapshot: sharedElement = [" + sharedElement + "], viewToGlobalMatrix = [" + viewToGlobalMatrix + "], screenBounds = [" + screenBounds + "]");
        return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
      }

      @Override
      public View onCreateSnapshotView(Context context, Parcelable snapshot) {
        System.out.println("PAGER ENTER: onCreateSnapshotView: context = [" + context + "], snapshot = [" + snapshot + "]");
        return super.onCreateSnapshotView(context, snapshot);
      }

      @Override
      public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
        System.out.println("PAGER ENTER: onSharedElementsArrived: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], listener = [" + listener + "]");
        super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
      }
    });

    setExitSharedElementCallback(new SharedElementCallback() {
      @Override
      public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        System.out.println("PAGER EXIT: onSharedElementStart: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], sharedElementSnapshots = [" + sharedElementSnapshots + "]");
        super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
      }

      @Override
      public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
        System.out.println("PAGER EXIT: onSharedElementEnd: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], sharedElementSnapshots = [" + sharedElementSnapshots + "]");
        super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
      }

      @Override
      public void onRejectSharedElements(List<View> rejectedSharedElements) {
        System.out.println("PAGER EXIT: onRejectSharedElements: rejectedSharedElements = [" + rejectedSharedElements + "]");
        super.onRejectSharedElements(rejectedSharedElements);
      }

      @Override
      public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        System.out.println("PAGER EXIT: onMapSharedElements: names = [" + names + "], sharedElements = [" + sharedElements + "]");


        /*for (String name : names) {
          View view = sharedElements.get(name);
          if (view instanceof PlayerView) {
            PlayerView player = (PlayerView) view;
            if (player.getPlayer() == null) {
              player.setPlayer(ExoHolder.get(getContext()));
            } else player.setPlayer(null);
          }
        }*/

        super.onMapSharedElements(names, sharedElements);
      }

      @Override
      public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
        System.out.println("PAGER EXIT: onCaptureSharedElementSnapshot: sharedElement = [" + sharedElement + "], viewToGlobalMatrix = [" + viewToGlobalMatrix + "], screenBounds = [" + screenBounds + "]");
        return super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
      }

      @Override
      public View onCreateSnapshotView(Context context, Parcelable snapshot) {
        System.out.println("PAGER EXIT: onCreateSnapshotView: context = [" + context + "], snapshot = [" + snapshot + "]");
        return super.onCreateSnapshotView(context, snapshot);
      }

      @Override
      public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
        System.out.println("PAGER EXIT: onSharedElementsArrived: sharedElementNames = [" + sharedElementNames + "], sharedElements = [" + sharedElements + "], listener = [" + listener + "]");
        super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
      }
    });

    return root;
  }

  @Override
  public void onStart() {
    super.onStart();
    System.out.println("PagerFragment.onStart");
  }

  @Override
  public void onResume() {
    super.onResume();
    System.out.println("PagerFragment.onResume");
  }

  @Override
  public void onPause() {
    super.onPause();
    System.out.println("PagerFragment.onPause");
  }

  @Override
  public void onStop() {
    super.onStop();
    System.out.println("PagerFragment.onStop");
  }

  private enum tabs {

    PAGINATION(R.layout.tab_pagination),
    PLAYERS(R.layout.tab_players);

    int layout;

    tabs(int layout) {
      this.layout = layout;
    }
  }

}
