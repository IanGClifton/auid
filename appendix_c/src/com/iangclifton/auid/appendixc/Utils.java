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

package com.iangclifton.auid.appendixc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.view.WindowManager;

public class Utils {

	private Utils() {
		// This class is not instantiated
	}

	/**
	 * Returns true if the device has a network connection
	 * 
	 * Note that this requires the ACCESS_NETWORK_STATE permission.
	 * 
	 * @param context
	 *            Context to access the ConnectivityManager
	 * @return true if the device has a network connection
	 */
	public static boolean isConnectedToNetwork(Context context) {
		boolean connected = false;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni != null) {
				connected = ni.isConnected();
			}
		}
		return connected;
	}

	/**
	 * Returns true if currently on the UI thread
	 * 
	 * @return true if currently on the UI thread
	 */
	public static boolean isUiThread() {
		return Looper.myLooper() == Looper.getMainLooper();
	}
	
	/**
	 * Enables or disables keeping the screen on
	 * 
	 * @param activity
	 *            Activity to access the Window from
	 * @param keepOn
	 *            boolean true to keep on; false to allow the screen to turn off
	 */
	public static void keepScreenOn(Activity activity, boolean keepOn) {
		if (keepOn) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} else {
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}
}
