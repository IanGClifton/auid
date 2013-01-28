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

package com.iangclifton.auid.ch10.autoloadinglistview;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ListFragment that demonstrates autoloading behavior.
 * 
 * @author Ian G. Clifton
 */
public class AutoloadingListFragment extends ListFragment implements OnScrollListener {
	
	private final int AUTOLOAD_THRESHOLD = 4;
	private final int MAXIMUM_ITEMS = 52;
	private SimpleAdapter mAdapter;
	private View mFooterView;
	private Handler mHandler;
	private boolean mIsLoading = false;
	private boolean mMoreDataAvailable = true;
	private boolean mWasLoading = false;
	
	private Runnable mAddItemsRunnable = new Runnable() {
		@Override
		public void run() {
			mAdapter.addMoreItems(10);
			mIsLoading = false;
		}
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Context context = getActivity();
		mHandler = new Handler();
		mAdapter = new SimpleAdapter(context, android.R.layout.simple_list_item_1);
		mFooterView = LayoutInflater.from(context).inflate(R.layout.loading_view, null);
		getListView().addFooterView(mFooterView, null, false);
		setListAdapter(mAdapter);
		getListView().setOnScrollListener(this);
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (!mIsLoading && mMoreDataAvailable) {
			if (totalItemCount >= MAXIMUM_ITEMS) {
				mMoreDataAvailable = false;
				getListView().removeFooterView(mFooterView);
			} else if (totalItemCount - AUTOLOAD_THRESHOLD <= firstVisibleItem + visibleItemCount) {
				mIsLoading = true;
				mHandler.postDelayed(mAddItemsRunnable, 1000);
			}
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Ignore
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (mWasLoading) {
			mWasLoading = false;
			mIsLoading = true;
			mHandler.postDelayed(mAddItemsRunnable, 1000);
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mHandler.removeCallbacks(mAddItemsRunnable);
		mWasLoading = mIsLoading;
		mIsLoading = false;
	}

	private static class SimpleAdapter extends BaseAdapter {
		
		private int mCount = 20;
		private final LayoutInflater mLayoutInflater;
		private final String mPositionString;
		private final int mTextViewResourceId;
		
		/*package*/ SimpleAdapter(Context context, int textViewResourceId) {
			mLayoutInflater = LayoutInflater.from(context);
			mPositionString = context.getString(R.string.position) + " ";
			mTextViewResourceId = textViewResourceId;
		}
		
		public void addMoreItems(int count) {
			mCount += count;
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			return mCount;
		}

		@Override
		public String getItem(int position) {
			return mPositionString + position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final TextView tv;
			if (convertView == null) {
				tv = (TextView) mLayoutInflater.inflate(mTextViewResourceId, null);
			} else {
				tv = (TextView) convertView;
			}
			
			tv.setText(getItem(position));
			return tv;
		}
	}
}
