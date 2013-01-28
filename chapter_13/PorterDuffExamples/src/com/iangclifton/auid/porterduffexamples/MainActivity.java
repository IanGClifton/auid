/*
 * Copyright (C) 2013 Ian G. Clifton
 * Code featured in Android User Interface Design: Turning Ideas and
 * Sketches into Beautifully Designed Apps (ISBN-10: 0321886739;
 * ISBN-13: 978-0321886736).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iangclifton.auid.porterduffexamples;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Simple Activity that displays a PorterDuffView and a Spinner.
 * 
 * @author Ian G. Clifton
 */
public class MainActivity extends Activity implements OnItemSelectedListener  {
	
	private ArrayAdapter<PorterDuff.Mode> mAdapter;
	private PorterDuffView mPorterDuffView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get reference to the PorterDuffView
		mPorterDuffView = (PorterDuffView) findViewById(R.id.porter_duff_view);
		
		// Create array of PorterDuff.Modes
		final PorterDuff.Mode[] porterDuffModes = PorterDuff.Mode.values();
		mAdapter = new ArrayAdapter<PorterDuff.Mode>(this, android.R.layout.simple_spinner_item, porterDuffModes);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		final Spinner spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setAdapter(mAdapter);
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		mPorterDuffView.setPorterDuffMode(mAdapter.getItem(position));
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// Ignored
	}
}
