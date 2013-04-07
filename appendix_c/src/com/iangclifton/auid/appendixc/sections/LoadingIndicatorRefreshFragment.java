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

import com.iangclifton.auid.appendixc.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoadingIndicatorRefreshFragment extends Fragment {

	private FakeTask mFakeTask;
	private MenuItem mRefreshMenuItem;
	private TextView mTextView;
	
	public LoadingIndicatorRefreshFragment() {
		// Required default constructor
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.activity_main, menu);
		mRefreshMenuItem = menu.findItem(R.id.menu_refresh);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		mTextView = (TextView) inflater.inflate(R.layout.centered_textview, null);
		mTextView.setText(R.string.refresh_directions);
		return mTextView;
	}

	@Override
	public void onDestroyView() {
		mTextView = null;
		mRefreshMenuItem = null;
		super.onDestroyView();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item == mRefreshMenuItem) {
			if (mFakeTask == null) {
				mFakeTask = new FakeTask();
				mFakeTask.execute();
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();
		
		if (mFakeTask != null) {
			mFakeTask.cancel(true);
			mFakeTask = null;
		}
	}

	/**
	 * Shows and hides the loading indicator
	 * 
	 * @param show boolean true to show the indicator; false to hide it
	 */
	public void showLoadingIndicator(boolean show) {
		if (show) {
			mTextView.setText(R.string.loading);
			mRefreshMenuItem.setEnabled(false);
			mRefreshMenuItem.setActionView(R.layout.actionbar_indeterminate_progress);
		} else {
			mTextView.setText(R.string.loading_complete);
			mRefreshMenuItem.setActionView(null);
			mRefreshMenuItem.setEnabled(true);
		}
	}
	
	/**
	 * AsyncTask that fakes 2 seconds of work
	 */
	private class FakeTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showLoadingIndicator(true);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// If the Thread is interrupted, just return
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			mFakeTask = null;
			showLoadingIndicator(false);
		}
		
	}
}
