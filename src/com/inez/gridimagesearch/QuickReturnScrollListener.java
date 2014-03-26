package com.inez.gridimagesearch;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.etsy.android.grid.StaggeredGridView;

public class QuickReturnScrollListener implements OnScrollListener {
	private static final int STATE_ONSCREEN = 0;
	private static final int STATE_OFFSCREEN = 1;
	private static final int STATE_RETURNING = 2;
	private static final int STATE_EXPANDED = 3;
	private int mState = STATE_ONSCREEN;
	private int mMinRawY = 0;
	private int rawY;
	private boolean noAnimation = false;
	private TranslateAnimation anim;
	private View mSticky;
	private int mStickyHeight;

	public QuickReturnScrollListener(View sticky) {
		mSticky = sticky;
		mSticky.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						mStickyHeight = mSticky.getHeight();
					}
				});
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		int translationY = 0;
		int scrollY = ((StaggeredGridView) view).getDistanceToTop();
		rawY = scrollY;

		switch (mState) {
		case STATE_OFFSCREEN:
			if (rawY <= mMinRawY) {
				mMinRawY = rawY;
			} else {
				mState = STATE_RETURNING;
			}
			translationY = rawY;
			break;
		case STATE_ONSCREEN:
			if (rawY < -mStickyHeight) {
				mState = STATE_OFFSCREEN;
				mMinRawY = rawY;
			}
			translationY = rawY;
			break;
		case STATE_RETURNING:

			if (translationY > 0) {
				translationY = 0;
				mMinRawY = rawY - mStickyHeight;
			}

			else if (rawY > 0) {
				mState = STATE_ONSCREEN;
				translationY = rawY;
			}

			else if (translationY < -mStickyHeight) {
				mState = STATE_OFFSCREEN;
				mMinRawY = rawY;

			} else if (mSticky.getTranslationY() != 0 && !noAnimation) {
				noAnimation = true;
				anim = new TranslateAnimation(0, 0, -mStickyHeight, 0);
				anim.setFillAfter(true);
				anim.setDuration(250);
				mSticky.startAnimation(anim);
				anim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						noAnimation = false;
						mMinRawY = rawY;
						mState = STATE_EXPANDED;
					}
				});
			}
			break;
		case STATE_EXPANDED:
			if (rawY < mMinRawY - 2 && !noAnimation) {
				noAnimation = true;
				anim = new TranslateAnimation(0, 0, 0, -mStickyHeight);
				anim.setFillAfter(true);
				anim.setDuration(250);
				anim.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						noAnimation = false;
						mState = STATE_OFFSCREEN;
					}
				});
				mSticky.startAnimation(anim);
			} else if (translationY > 0) {
				translationY = 0;
				mMinRawY = rawY - mStickyHeight;
			}

			else if (rawY > 0) {
				mState = STATE_ONSCREEN;
				translationY = rawY;
			}

			else if (translationY < -mStickyHeight) {
				mState = STATE_OFFSCREEN;
				mMinRawY = rawY;
			} else {
				mMinRawY = rawY;
			}

		}
		mSticky.setTranslationY(Math.min(translationY, 0));
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	public void setStickyHeight(int stickyHeight) {
		mStickyHeight = stickyHeight;
	}
}
