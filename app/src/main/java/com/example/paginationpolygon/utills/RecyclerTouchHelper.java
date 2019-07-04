/*
 * RecyclerTouchHelper.java
 * widgets
 *
 * Copyright (C) 2018, Gleb Nikitenko. All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.paginationpolygon.utills;

import android.content.Context;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Closeable;
import java.util.function.BiFunction;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

/**
 * Collection Touch Helper.
 *
 * @author Nikitenko Gleb
 * @since 1.0, 16/01/2017
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class RecyclerTouchHelper
  extends RecyclerView.SimpleOnItemTouchListener
  implements IntConsumer, Closeable {

  /** The log-cat tag. */
  private static final String TAG = "RecyclerTouchHelper";

  /** The gesture detector compat. */
  private final Predicate<MotionEvent> mGestureDetector;

  /** Recycler View. */
  private RecyclerView mRecycler;

  /** Callback */
  private IntConsumer mListener;

  /** "CLOSE" flag-state. */
  private volatile boolean mClosed;

  /**
   * Constructs a new {@link RecyclerTouchHelper}
   *
   * @param recycler recycler view widget
   * @param listener click positions listener
   */
  public RecyclerTouchHelper
  (@NonNull RecyclerView recycler, @NonNull IntConsumer listener) {
    this(recycler, listener, (context, callback) ->
      new RecyclerGestureDetector(context, callback)::onTouchEvent);
  }

  /**
   * Constructs a new {@link RecyclerTouchHelper}
   *
   * @param recycler recycler view widget
   * @param listener click positions listener
   * @param factory  gesture detector factory
   */
  public RecyclerTouchHelper(@NonNull RecyclerView recycler, @NonNull IntConsumer listener,
                             @NonNull BiFunction<Context, OnGestureListener, Predicate<MotionEvent>> factory) {
    mGestureDetector = factory.apply(recycler.getContext(),
      new Callback(recycler, mListener = listener));
    (mRecycler = recycler).addOnItemTouchListener(this);
  }

  /** {@inheritDoc} */
  @Override
  public final void close() {
    if (mClosed) return;
    mClosed = true;
    mRecycler.removeOnItemTouchListener(this);
    mRecycler = null;
    mListener.accept(-1);
    mListener = null;
  }

  /** {@inheritDoc} */
  @Override
  protected final void finalize() throws Throwable {
    try {
      close();
    } finally {
      super.finalize();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean onInterceptTouchEvent
  (@NonNull RecyclerView recycler, @NonNull MotionEvent event) {
    return mGestureDetector.test(event);
  }

  /** {@inheritDoc} */
  @Override
  public final void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    super.onRequestDisallowInterceptTouchEvent(disallowIntercept);
  }

  /** {@inheritDoc} */
  @Override
  public final void accept(int value) {
    mListener.accept(value);
  }

  /** Internal callback. */
  private static final class Callback implements OnGestureListener {

    /** Select listener. */
    private final IntConsumer mListener;
    /** The recycler view widget */
    private final RecyclerView mRecycler;
    /** Handle near tap. */
    private boolean mHandle = false;

    /**
     * Constructs a new {@link RecyclerTouchHelper.Callback} .
     *
     * @param listener selection listener
     */
    Callback(@NonNull RecyclerView recycler, @NonNull IntConsumer listener) {
      mRecycler = recycler;
      mListener = listener;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean onSingleTapUp(@NonNull MotionEvent e) {
      if (!mHandle) return false;
      mHandle = false;
      final View childView = mRecycler.findChildViewUnder(e.getX(), e.getY());
      if (childView == null || !childView.isEnabled()) return false;
      final int position = mRecycler.getChildAdapterPosition(childView);
      if (position == RecyclerView.NO_POSITION) return false;
      childView.playSoundEffect(SoundEffectConstants.CLICK);
      childView.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
      mListener.accept(position);
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public final void onShowPress(@NonNull MotionEvent event) {
      mHandle = true;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean onScroll
    (MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
      return false;
    }

    /** {@inheritDoc} */
    @Override
    public final void onLongPress(MotionEvent e) {
    }

    /** {@inheritDoc} */
    @Override
    public final boolean onFling
    (MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
      return false;
    }

    /** {@inheritDoc} */
    @Override
    public final boolean onDown(MotionEvent e) {
      return false;
    }

  }

}
