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

package com.iangclifton.auid.combinedviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Custom RelativeLayout that simplifies displaying {@link Property} objects.
 * 
 * @author Ian G. Clifton
 */
public class PropertyView extends RelativeLayout {
	
	private TextView mAddressTextView;
	private TextView mPriceTextView;
	private ImageView mThumbnailImageView;

	public PropertyView(Context context) {
		super(context);
		init(context);
	}

	public PropertyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PropertyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	/**
	 * Updates this View to display the passed Property
	 * 
	 * @param property Property to display
	 */
	public void setProperty(Property property) {
		StringBuilder sb = new StringBuilder();
		sb.append(property.getStreetAddress())
		  .append('\n')
		  .append(property.getCity())
		  .append(", ")
		  .append(property.getState());
		mAddressTextView.setText(sb.toString());
		
		mPriceTextView.setText(property.getPrice());
		mThumbnailImageView.setImageResource(R.drawable.house);
	}

	private void init(Context context) {
		inflate(context, R.layout.property, this);
		mAddressTextView = (TextView) findViewById(R.id.address);
		mPriceTextView = (TextView) findViewById(R.id.price);
		mThumbnailImageView = (ImageView) findViewById(R.id.thumbnail);
	}
}
