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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Demonstrates a simple loading indicator handled by the Activity.
 * 
 * Note that the Activity should call <code>getWindow().requestFeature(Window.FEATURE_PROGRESS);</code> 
 * in order for the indicator to show.
 * 
 * @author Ian G. Clifton
 */
public class LoadingIndicatorFragment extends Fragment {
	
	private WebView mWebView;

	public LoadingIndicatorFragment() {
		// Required default constructor
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// This happens after onCreateView
		mWebView.loadUrl("http://www.google.com");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mWebView = new WebView(inflater.getContext());
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				getActivity().setProgress(progress * 100);
			}
		});
		return mWebView;
	}

	@Override
	public void onDestroyView() {
		mWebView.stopLoading();
		mWebView = null;
		super.onDestroyView();
	}
}
