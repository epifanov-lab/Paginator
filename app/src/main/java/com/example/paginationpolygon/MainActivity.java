package com.example.paginationpolygon;


import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.paginationpolygon.card.CardFragment;
import com.example.paginationpolygon.player.FullPlayerFragment;

/**
 * @author Konstantin Epifanov
 * @since 05.07.2019
 */
public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getSupportFragmentManager().beginTransaction()
      .replace(R.id.fragment_container,
        PagerFragment.newInstance(),
        PagerFragment.class.getSimpleName())
      .commit();
  }

  public void goToFull(Fragment host, View... shared) {
    Fragment fragment = FullPlayerFragment.newInstance();

    final int duration = 2000;

    Transition scale = new ChangeBounds().setDuration(duration);
    Transition fade = new Fade().setDuration(duration / 2);

    TransitionSet combo = new TransitionSet()
      .addTransition(new ChangeBounds())
      //.addTransition(new ChangeImageTransform())
      //.addTransition(new ChangeClipBounds())
      //.addTransition(new ChangeTransform())
      .setDuration(duration);

    fragment.setSharedElementEnterTransition(scale);
    fragment.setSharedElementReturnTransition(scale);

    host.setExitTransition(fade);
    host.setEnterTransition(fade);

    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment_container, fragment);

    for (View v : shared) {
      transaction.addSharedElement(v, v.getTransitionName());
    }

    transaction.addToBackStack(null);
    transaction.commit();

  }

  public void restart() {
    Intent intent = getIntent();
    finish();
    startActivity(intent);
  }

}
