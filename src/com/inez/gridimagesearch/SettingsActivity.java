package com.inez.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		Intent intent = getIntent();
		Settings settings = (Settings) intent
				.getSerializableExtra(GridImageSearchActivity.SETTINGS_KEY);

		if (settings.imageSize != null) {
			setSpinnerToValue((Spinner) findViewById(R.id.s_imageSize),
					settings.imageSize);
		}
		if (settings.colorFilter != null) {
			setSpinnerToValue((Spinner) findViewById(R.id.s_colorFilter),
					settings.colorFilter);
		}
		if (settings.imageType != null) {
			setSpinnerToValue((Spinner) findViewById(R.id.s_imageType),
					settings.imageType);
		}
		if (settings.siteFilter != null) {
			((EditText) findViewById(R.id.et_siteFilter))
					.setText(settings.siteFilter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	private void setSpinnerToValue(Spinner spinner, String value) {
		int index = 0;
		SpinnerAdapter adapter = spinner.getAdapter();
		for (int i = 0; i < adapter.getCount(); i++) {
			if (adapter.getItem(i).equals(value)) {
				index = i;
			}
		}
		spinner.setSelection(index);
	}

	public void onSavePress(MenuItem miSave) {
		Settings settings = new Settings();

		String imageSize = ((Spinner) findViewById(R.id.s_imageSize))
				.getSelectedItem().toString();
		if (!imageSize.equals("-")) {
			settings.imageSize = imageSize;
		}
		String colorFilter = ((Spinner) findViewById(R.id.s_colorFilter))
				.getSelectedItem().toString();
		if (!colorFilter.equals("-")) {
			settings.colorFilter = colorFilter;
		}
		String imageType = ((Spinner) findViewById(R.id.s_imageType))
				.getSelectedItem().toString();
		if (!imageType.equals("-")) {
			settings.imageType = imageType;
		}
		String siteFilter = ((EditText) findViewById(R.id.et_siteFilter))
				.getText().toString().trim();
		if (siteFilter.length() > 0) {
			settings.siteFilter = siteFilter;
		}

		Intent intent = new Intent();
		intent.putExtra(GridImageSearchActivity.SETTINGS_KEY, settings);
		setResult(RESULT_OK, intent);
		finish();
	}

}
