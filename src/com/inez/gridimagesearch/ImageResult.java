package com.inez.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult {
	private String fullUrl;
	private int fullWidth;
	private int fullHeight;

	private String thumbUrl;
	private int thumbWidth;
	private int thumbHeight;

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public int getFullWidth() {
		return fullWidth;
	}

	public void setFullWidth(int fullWidth) {
		this.fullWidth = fullWidth;
	}

	public int getFullHeight() {
		return fullHeight;
	}

	public void setFullHeight(int fullHeight) {
		this.fullHeight = fullHeight;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public int getThumbWidth() {
		return thumbWidth;
	}

	public void setThumbWidth(int thumbWidth) {
		this.thumbWidth = thumbWidth;
	}

	public int getThumbHeight() {
		return thumbHeight;
	}

	public void setThumbHeight(int thumbHeight) {
		this.thumbHeight = thumbHeight;
	}

	public ImageResult(JSONObject json) {
		try {
			// https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android
			this.fullUrl = json.getString("url");
			this.fullWidth = json.getInt("width");
			this.fullHeight = json.getInt("height");
			this.thumbUrl = json.getString("tbUrl");
			this.thumbWidth = json.getInt("tbWidth");
			this.thumbHeight = json.getInt("tbHeight");
		} catch (JSONException e) {
			// TODO: Handle error
			e.printStackTrace();
		}
	}

	public ImageResult(String fullUrl, int fullWidth, int fullHeight,
			String thumbUrl, int thumbWidth, int thumbHeight) {
		this.fullUrl = fullUrl;
		this.fullWidth = fullWidth;
		this.fullHeight = fullHeight;
		this.thumbUrl = thumbUrl;
		this.thumbWidth = thumbWidth;
		this.thumbHeight = thumbHeight;
	}

	public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int i = 0; i < array.length(); i++) {
			try {
				results.add(new ImageResult(array.getJSONObject(i)));
			} catch (JSONException e) {
				// TODO: Handle error
				e.printStackTrace();
			}
		}
		return results;
	}
}
