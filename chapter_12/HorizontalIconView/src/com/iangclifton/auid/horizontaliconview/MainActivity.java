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

package com.iangclifton.auid.horizontaliconview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

/**
 * Activity that demonstrates the {@link HorizontalIconView}.
 * 
 * @author Ian G. Clifton
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Get a List of Drawables
		final Resources res = getResources();
		final List<Drawable> list = new ArrayList<Drawable>();
		list.add(res.getDrawable(R.drawable.icon_00));
		list.add(res.getDrawable(R.drawable.icon_01));
		list.add(res.getDrawable(R.drawable.icon_02));
		list.add(res.getDrawable(R.drawable.icon_03));
		list.add(res.getDrawable(R.drawable.icon_04));
		list.add(res.getDrawable(R.drawable.icon_05));
		list.add(res.getDrawable(R.drawable.icon_06));
		list.add(res.getDrawable(R.drawable.icon_07));
		list.add(res.getDrawable(R.drawable.icon_08));
		list.add(res.getDrawable(R.drawable.icon_09));
		list.add(res.getDrawable(R.drawable.icon_10));
		list.add(res.getDrawable(R.drawable.icon_11));
		list.add(res.getDrawable(R.drawable.icon_12));
		list.add(res.getDrawable(R.drawable.icon_13));
		list.add(res.getDrawable(R.drawable.icon_14));
		list.add(res.getDrawable(R.drawable.icon_15));
		list.add(res.getDrawable(R.drawable.icon_16));
		list.add(res.getDrawable(R.drawable.icon_17));
		list.add(res.getDrawable(R.drawable.icon_18));
		list.add(res.getDrawable(R.drawable.icon_19));
		
		setContentView(R.layout.activity_main);
		final HorizontalIconView view = (HorizontalIconView) findViewById(R.id.horizontal_icon_view);
		view.setDrawables(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
