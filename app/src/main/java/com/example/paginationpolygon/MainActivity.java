package com.example.paginationpolygon;


import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeImageTransform;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.paginationpolygon.pagination.PaginationFragment;
import com.example.paginationpolygon.player.FullPlayerFragment;
import com.example.paginationpolygon.player.PlayerFragment;

/**
 * @author Konstantin Epifanov
 * @since 05.07.2019
 */
public class MainActivity extends AppCompatActivity {

  private ViewPager mPager;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mPager = findViewById(R.id.view_pager);

    mPager.setAdapter(
      new FragmentPagerAdapter(getSupportFragmentManager(),
        FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        @NonNull
        @Override
        public Fragment getItem(int position) {
          Fragment page = PaginationFragment.newInstance();
          switch (position) {
            case 0:
              page = PaginationFragment.newInstance();
              break;
            case 1:
              page = PlayerFragment.newInstance();
              break;
          }
          return page;
        }

        @Override
        public int getCount() {
          return 2;
        }
      });

    mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override
      public void onPageSelected(int position) {
        System.out.println("onPageSelected: position = [" + position + "]");
      }

      @Override
      public void onPageScrolled(int position, float positionOffset,
                                 int positionOffsetPixels) {
        System.out.println("onPageScrolled: position = [" + position + "], positionOffset = [" + positionOffset + "], positionOffsetPixels = [" + positionOffsetPixels + "]");
      }

      @Override
      public void onPageScrollStateChanged(int state) {
        System.out.println("onPageScrollStateChanged: state = [" + state + "]");
      }
    });
  }

  public void transitionToFullPlayer(View view) {
    FullPlayerFragment fragment = FullPlayerFragment.newInstance();

    fragment.setSharedElementEnterTransition(new ChangeImageTransform().setDuration(1000));
    fragment.setSharedElementReturnTransition(new ChangeImageTransform().setDuration(1000));

    System.out.println("MainActivity.transitionToFullPlayer: " + view.getTransitionName());

    getSupportFragmentManager().beginTransaction()
      .addSharedElement(view, view.getTransitionName())
      .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
      .addToBackStack(null)
      .commit();
  }

  public void restart() {
    Intent intent = getIntent();
    finish();
    startActivity(intent);
  }
}
