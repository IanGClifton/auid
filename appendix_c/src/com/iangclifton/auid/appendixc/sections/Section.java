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

import java.io.Serializable;

import android.support.v4.app.Fragment;

import com.iangclifton.auid.appendixc.R;

/**
 * Enum representing the sections of this app.
 * 
 * @author Ian G. Clifton
 */
public enum Section implements Serializable {
	LOADING_INDICATOR(R.string.loading_indicator, R.string.loading_indicator_description, LoadingIndicatorFragment.class),
	LOADING_INDICATOR_REFRESH(R.string.loading_indicator_refresh, R.string.loading_indicator_refresh_description, LoadingIndicatorRefreshFragment.class),
	DISMISS_KEYBOARD(R.string.dismiss_keyboard, R.string.dismiss_keyboard_description, DismissKeyboardFragment.class),
	VARIOUS(R.string.various, R.string.various_description, VariousDemosFragment.class),
	CUSTOM_VIEW(R.string.custom_view, R.string.custom_view_description, CustomViewFragment.class);
	
	private final int mDescriptionResourceId;
	private final Class<? extends Fragment> mFragmentClass;
	private final int mTitleResourceId;
	
	private Section(int titleResourceId, int descriptionResourceId, Class<? extends Fragment> fragmentClass) {
		mTitleResourceId = titleResourceId;
		mDescriptionResourceId = descriptionResourceId;
		mFragmentClass = fragmentClass;
	}
	
	public int getDescriptionResourceId() {
		return mDescriptionResourceId;
	}
	
	/**
	 * Returns a new instance of the Fragment this Section represents
	 * 
	 * @return a new instance of the Fragment this Section represents
	 */
	public Fragment getFragment() {
		try {
			return mFragmentClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int getTitleResourceId() {
		return mTitleResourceId;
	}
}
