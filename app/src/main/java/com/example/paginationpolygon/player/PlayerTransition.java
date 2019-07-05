package com.example.paginationpolygon.player;

import android.content.Context;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.AttributeSet;

/**
 * Transition that performs almost exactly like {@link android.transition.AutoTransition}, but has an
 * added {@link ChangeImageTransform} to support properly scaling up our gorgeous kittens.
 *
 * @author bherbst
 */
public class PlayerTransition extends TransitionSet {
  public PlayerTransition() {
    init();
  }

  /**
   * This constructor allows us to use this transition in XML
   */
  public PlayerTransition(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    setOrdering(ORDERING_TOGETHER);
    addTransition(new ChangeBounds())
      .addTransition(new ChangeTransform()
        .setDuration(1000));
  }
}
