package com.inez.gridimagesearch;

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
	
	public ImageResult(String fullUrl, int fullWidth, int fullHeight, String thumbUrl, int thumbWidth, int thumbHeight) {
		this.fullUrl = fullUrl;
		this.fullWidth = fullWidth;
		this.fullHeight = fullHeight;
		this.thumbUrl = thumbUrl;
		this.thumbWidth = thumbWidth;
		this.thumbHeight = thumbHeight;
	}
}
