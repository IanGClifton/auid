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

package com.iangclifton.auid.ch10.splashscreenfragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.TextView;

import com.iangclifton.auid.ch10.splashscreenfragment.DataLoaderFragment.ProgressListener;

/**
 * Simple Activity that demonstrates loading in the background and showing a splash screen.
 * 
 * @author Ian G. Clifton
 */
public class MainActivity extends Activity implements ProgressListener {
	
	private static final String TAG_DATA_LOADER = "dataLoader";
	private static final String TAG_SPLASH_SCREEN = "splashScreen";
	
	private DataLoaderFragment mDataLoaderFragment;
	private SplashScreenFragment mSplashScreenFragment;
	
	@Override
	public void onCompletion(Double result) {
		// For the sake of brevity, we just show a TextView with the result
		TextView tv = new TextView(this);
		tv.setText(String.valueOf(result));
		setContentView(tv);
		mDataLoaderFragment = null;
	}

	@Override
	public void onProgressUpdate(int progress) {
		mSplashScreenFragment.setProgress(progress);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final FragmentManager fm = getFragmentManager();
		mDataLoaderFragment = (DataLoaderFragment) fm.findFragmentByTag(TAG_DATA_LOADER);
		if (mDataLoaderFragment == null) {
			mDataLoaderFragment = new DataLoaderFragment();
			mDataLoaderFragment.setProgressListener(this);
			mDataLoaderFragment.startLoading();
			fm.beginTransaction().add(mDataLoaderFragment, TAG_DATA_LOADER).commit();
		} else {
			if (checkCompletionStatus()) {
				return;
			}
		}
		
		// Show loading fragment
		mSplashScreenFragment = (SplashScreenFragment) fm.findFragmentByTag(TAG_SPLASH_SCREEN);
		if (mSplashScreenFragment == null) {
			mSplashScreenFragment = new SplashScreenFragment();
			fm.beginTransaction().add(android.R.id.content, mSplashScreenFragment, TAG_SPLASH_SCREEN).commit();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (mDataLoaderFragment != null) {
			checkCompletionStatus();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mDataLoaderFragment != null) {
			mDataLoaderFragment.removeProgressListener();
		}
	}
	
	/**
	 * Checks if data is done loading, if it is, the result is handled
	 * 
	 * @return true if data is done loading
	 */
	private boolean checkCompletionStatus() {
		if (mDataLoaderFragment.hasResult()) {
			onCompletion(mDataLoaderFragment.getResult());
			FragmentManager fm = getFragmentManager();
			mSplashScreenFragment = (SplashScreenFragment) fm.findFragmentByTag(TAG_SPLASH_SCREEN);
			if (mSplashScreenFragment != null) {
				fm.beginTransaction().remove(mSplashScreenFragment).commit();
			}
			return true;
		}
		mDataLoaderFragment.setProgressListener(this);
		return false;
	}
}
