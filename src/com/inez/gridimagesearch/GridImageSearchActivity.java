package com.inez.gridimagesearch;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.etsy.android.grid.StaggeredGridView;

public class GridImageSearchActivity extends Activity {

	private StaggeredGridView mGridView;
	private View mSticky;
	private ArrayList<ImageResult> mData;
	private ImageResultArrayAdapter mAdapter;
	private static final int STATE_ONSCREEN = 0;
	private static final int STATE_OFFSCREEN = 1;
	private static final int STATE_RETURNING = 2;
	private int mState = STATE_ONSCREEN;
	private int mMinRawY = 0;
	private int mStickyHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// hide title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// hide notification area
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_grid_image_search);
		
		mGridView = (StaggeredGridView) findViewById(R.id.grid_view);

		// Grid header
		LayoutInflater layoutInflater = getLayoutInflater();
		View header = layoutInflater.inflate(R.layout.grid_header, null);
		mGridView.addHeaderView(header);

		// Grid data
		ArrayList<ImageResult> mData = SampleImageResult.generateSampleImageResult();
		ImageResultArrayAdapter mAdapter = new ImageResultArrayAdapter(this, mData);
		mGridView.setAdapter(mAdapter);
		
		// Sticky
		mSticky = (View) findViewById(R.id.sticky);

		mGridView.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						mStickyHeight = mSticky.getHeight();
					}
				});
		
		mGridView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				int translationY = 0;
				int scrollY = mGridView.getDistanceToTop();
				
				int rawY = scrollY;
				
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
						translationY = (rawY - mMinRawY) - mStickyHeight;
						if (translationY > 0) {
							translationY = 0;
							mMinRawY = rawY - mStickyHeight;
						}

						if (rawY > 0) {
							mState = STATE_ONSCREEN;
							translationY = rawY;
						}

						if (translationY < -mStickyHeight) {
							mState = STATE_OFFSCREEN;
							mMinRawY = rawY;
						}
						break;
						
				}
				
				//mSticky.setTranslationY(translationY);
				mSticky.setTranslationY(Math.min(translationY, 0));
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
