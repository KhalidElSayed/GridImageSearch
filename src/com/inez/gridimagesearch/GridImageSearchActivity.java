package com.inez.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import com.etsy.android.grid.StaggeredGridView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

@SuppressLint("NewApi")
public class GridImageSearchActivity extends Activity {

	private StaggeredGridView mGrid;

	private ArrayList<ImageResult> mResults;

	private ImageResultArrayAdapter mAdapter;

	private EditText mEtQuery;

	private String mQuery;

	private Settings mSettings;

	private SharedPreferences mPrefs;

	public static final int SETTINGS_REQUEST = 1;

	public static final String SETTINGS_KEY = "settings";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// hide title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_grid_image_search);

		loadSettings();

		configureImageLoader();

		mEtQuery = (EditText) findViewById(R.id.et_query);
		mGrid = (StaggeredGridView) findViewById(R.id.grid_view);
		mResults = new ArrayList<ImageResult>();
		mAdapter = new ImageResultArrayAdapter(this, mResults);
		mGrid.addHeaderView(getLayoutInflater().inflate(R.layout.grid_header,
				null));
		mGrid.setAdapter(mAdapter);

		setupGridListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void loadSettings() {
		mPrefs = getSharedPreferences("com.inez.gridimagesearch",
				Context.MODE_PRIVATE);
		mSettings = new Settings();
		mSettings.imageSize = mPrefs.getString("imageSize", null);
		mSettings.colorFilter = mPrefs.getString("colorFilter", null);
		mSettings.imageType = mPrefs.getString("imageType", null);
		mSettings.siteFilter = mPrefs.getString("siteFilter", null);
	}

	private void saveSettings() {
		mPrefs.edit().putString("imageSize", mSettings.imageSize)
				.putString("colorFilter", mSettings.colorFilter)
				.putString("imageType", mSettings.imageType)
				.putString("siteFilter", mSettings.siteFilter).commit();
	}

	private void configureImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();

		ImageLoader.getInstance().init(config);
	}

	private void setupGridListeners() {
		// Use composite pattern
		// (http://en.wikipedia.org/wiki/Composite_pattern) to attach two
		// different listeners to the same event
		CompositeOnScrollListener compositeOnScrollListener = new CompositeOnScrollListener();

		compositeOnScrollListener
				.registerListener(new QuickReturnScrollListener(
						findViewById(R.id.sticky)));

		compositeOnScrollListener.registerListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadData(mQuery, totalItemsCount - 1, false);
			}
		});

		mGrid.setOnScrollListener(compositeOnScrollListener);

		mGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent,
					int position, long rowId) {
				// Subtract 1 from the position because our grid has a header
				// which counts as a position
				// but is not present in results
				ImageResult imageResult = mResults.get(position - 1);
				Intent intent = new Intent(getApplicationContext(),
						ImageDisplayActivity.class);
				intent.putExtra("url", imageResult.getFullUrl());
				startActivity(intent);
			}
		});
	}

	private String getParametrizedApiUrl(String query, int offset) {
		String apiUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
		apiUrl += "&q=" + Uri.encode(query);
		apiUrl += "&start=" + offset;
		if (mSettings.imageSize != null) {
			apiUrl += "&imgsz=" + mSettings.imageSize;
		}
		if (mSettings.colorFilter != null) {
			apiUrl += "&imgcolor=" + mSettings.colorFilter;
		}
		if (mSettings.imageType != null) {
			apiUrl += "&imgtype=" + mSettings.imageType;
		}
		if (mSettings.siteFilter != null) {
			apiUrl += "&as_sitesearch=" + mSettings.siteFilter;
		}
		return apiUrl;
	}

	private void loadData(String query, int offset, final Boolean tillFull) {
		Log.d("loadData", "query: " + query + ", offset: " + offset + ", tillFull: " + tillFull);
		if (offset != -1) {
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(getParametrizedApiUrl(query, offset),
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject response) {
							JSONArray imageJsonResults = null;
							try {
								imageJsonResults = response.getJSONObject(
										"responseData").getJSONArray("results");
								mAdapter.addAll(ImageResult
										.fromJSONArray(imageJsonResults));

								if (tillFull) {
									mGrid.getViewTreeObserver()
											.addOnGlobalLayoutListener(
													new OnGlobalLayoutListener() {

														@Override
														public void onGlobalLayout() {
															mGrid.getViewTreeObserver()
																	.removeOnGlobalLayoutListener(
																			this);
															if (mGrid
																	.getLastVisiblePosition() + 4 > mGrid
																	.getCount()) {
																loadData(
																		mQuery,
																		mGrid.getCount() - 1,
																		true);
															}
														}

													});
								}
							} catch (JSONException e) {
								// TODO: Some better handling for this situation
								// (perhaps display information that there are
								// no more results)
							}
						}
					});
		}
	}

	public void onSearchClick(View view) {
		mQuery = mEtQuery.getText().toString();
		mAdapter.clear();
		loadData(mQuery, 0, true);
	}

	public void onSettingsClick(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		intent.putExtra(SETTINGS_KEY, mSettings);
		startActivityForResult(intent, SETTINGS_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SETTINGS_REQUEST) {
			if (resultCode == RESULT_OK) {
				mSettings = (Settings) data.getSerializableExtra(SETTINGS_KEY);
				saveSettings();
				mAdapter.clear();
				loadData(mQuery, 0, true);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
