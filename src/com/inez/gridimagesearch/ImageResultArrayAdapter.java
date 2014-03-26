package com.inez.gridimagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.grid_image_result, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = this.getItem(position);
		DynamicHeightSmartImageView ivImage;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			ivImage = (DynamicHeightSmartImageView) inflator.inflate(
					R.layout.grid_image_result, parent, false);
		} else {
			ivImage = (DynamicHeightSmartImageView) convertView;
			ivImage.setImageResource(android.R.color.transparent);
		}
		// Casting int to double is necessary for proper precision ratio
		// calculation
		double ratio = (double) imageInfo.getThumbHeight()
				/ (double) imageInfo.getThumbWidth();
		ivImage.setHeightRatio(ratio);
		// ivImage.setImageUrl(imageInfo.getThumbUrl());
		ImageLoader.getInstance()
				.displayImage(imageInfo.getThumbUrl(), ivImage);
		return ivImage;
	}

}
