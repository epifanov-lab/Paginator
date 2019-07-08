package com.example.paginationpolygon;


import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
      .replace(R.id.fragment_container, PagerFragment.newInstance())
      .commit();
  }

  public void goToNextFragment(View... shared) {
    Fragment fragment = FullPlayerFragment.newInstance();

    Transition scale = new ChangeBounds();
    Transition fade = new Fade();

    TransitionSet set = new TransitionSet()
      .addTransition(new ChangeBounds())
      .addTransition(new ChangeTransform());

    fragment.setSharedElementEnterTransition(set);
    fragment.setSharedElementReturnTransition(set);

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
