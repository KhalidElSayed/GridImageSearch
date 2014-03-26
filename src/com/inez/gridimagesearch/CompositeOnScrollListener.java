package com.inez.gridimagesearch;

import java.util.HashSet;
import java.util.Set;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class CompositeOnScrollListener implements OnScrollListener {

	private final Set<OnScrollListener> delegates = new HashSet<OnScrollListener>();

	public void registerListener(OnScrollListener listener) {
		delegates.add(listener);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		for (OnScrollListener listener : delegates) {
			listener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		for (OnScrollListener listener : delegates) {
			listener.onScrollStateChanged(view, scrollState);
		}

	}

}
