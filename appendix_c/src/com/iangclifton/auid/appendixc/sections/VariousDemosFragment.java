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

package com.iangclifton.auid.appendixc.sections;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iangclifton.auid.appendixc.R;
import com.iangclifton.auid.appendixc.Utils;

/**
 * Fragment that demonstrates some of the techniques in Appendix C.
 * 
 * @author Ian G. Clifton
 */
public class VariousDemosFragment extends Fragment {
	
	public VariousDemosFragment() {
		// Required default constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.various_demos, container, false);
		
		final StringBuilder sb = new StringBuilder();
		
		// Create a String for the device physical size
		switch (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
			case Configuration.SCREENLAYOUT_SIZE_XLARGE:
				// Extra large (most 10" tablets)
				sb.append(getString(R.string.configuration_xlarge));
				break;
			case Configuration.SCREENLAYOUT_SIZE_LARGE:
				// Large (most 7" tablets)
				sb.append(getString(R.string.configuration_large));
				break;
			case Configuration.SCREENLAYOUT_SIZE_NORMAL:
				// Normal (most phones)
				sb.append(getString(R.string.configuration_normal));
				break;
			case Configuration.SCREENLAYOUT_SIZE_SMALL:
				// Small (very uncommon)
				sb.append(getString(R.string.configuration_small));
				break;
			default:
				sb.append(getString(R.string.configuration_unknown));
				break;
		}
		sb.append('\n');
		
		// Create a String for the display density
		switch (getResources().getDisplayMetrics().densityDpi) {
			case DisplayMetrics.DENSITY_XXHIGH:
				// Display is around 480 pixels per inch
				sb.append(getString(R.string.density_xxhdpi));
				break;
			case DisplayMetrics.DENSITY_XHIGH:
				// Display is around 320 pixels per inch
				sb.append(getString(R.string.density_xhdpi));
				break;
			case DisplayMetrics.DENSITY_HIGH:
				// Display is around 240 pixels per inch
				sb.append(getString(R.string.density_hdpi));
				break;
			case DisplayMetrics.DENSITY_MEDIUM:
				// Display is around 160 pixels per inch
				sb.append(getString(R.string.density_mdpi));
				break;
			case DisplayMetrics.DENSITY_LOW:
				// Display is around 120 pixels per inch
				sb.append(getString(R.string.density_ldpi));
				break;
			case DisplayMetrics.DENSITY_TV:
				// Display is a 720p TV screen
				// Sometimes also used for 1280x720 7" tablets
				// Rarely should you ever specifically target this density
				sb.append(getString(R.string.density_tv));
				break;
			default:
				sb.append(getString(R.string.density_unknown));
				break;
		}
		sb.append('\n');
		
		// Create a String for the thread we're on
		// Obviously this method is always called on the main thread but this technique can be used anywhere.
		if (Utils.isUiThread()) {
			// UI Thread
			sb.append(getString(R.string.main_thread_true));
		} else {
			// Other Thread
			sb.append(getString(R.string.main_thread_false));
		}
		sb.append(" (Thread name: ").append(Thread.currentThread().getName()).append(')');
		
		// Set text
		final TextView tv = (TextView) rootView.findViewById(R.id.main_text);
		tv.setText(sb);
		
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		final TextView tv = (TextView) getView().findViewById(R.id.connection_status);
		if (Utils.isConnectedToNetwork(getActivity())) {
			tv.setText(R.string.has_connection);
		} else {
			tv.setText(R.string.has_no_connection);
		}
	}

	
}
