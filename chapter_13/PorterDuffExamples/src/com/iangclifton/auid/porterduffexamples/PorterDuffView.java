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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * View that can display two images composited together based on a {@link PorterDuffXfermode}.
 * 
 * Use the {@link #setPorterDuffMode(android.graphics.PorterDuff.Mode)} to change the {@link PorterDuff.Mode} at
 * runtime.
 * 
 * This View uses R.drawable.shape1 and R.drawable.shape2 for the compositing, so you can replace those with your
 * own assets to see how other images are affected by the various compositing modes.
 * 
 * @author Ian G. Clifton
 */
public class PorterDuffView extends View {
		
	/**
	 * The Paint used to draw everything
	 */
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	/**
	 * The Bitmap containing the two images blended together
	 */
	private Bitmap mBitmap;
	
	/**
	 * The XferMode to combine the images with
	 */
	private Xfermode mXferMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
	
	public PorterDuffView(Context context) {
		super(context);
	}

	public PorterDuffView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PorterDuffView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Sets the new PorterDuff.Mode, removes the existing Bitmap and invalidates the view
	 * 
	 * @param mode PorterDuff.Mode to use
	 */
	public void setPorterDuffMode(PorterDuff.Mode mode) {
		mXferMode = new PorterDuffXfermode(mode);
		mBitmap = null;
		invalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		if (mBitmap == null) {
			createBitmap();
		}
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		
		// If mBitmap is set, make sure it's the right size
		if (mBitmap != null) {
			final int width = right - left;
			final int height = bottom - top;
			final int minDimension = Math.min(width, height);
			
			final int bitmapWidth = mBitmap.getWidth();
			if (minDimension != bitmapWidth) {
				mBitmap = null;
			}
		}
	}

	/**
	 * Creates mBitmap using the set XferMode
	 */
	private void createBitmap() {
		
		// Prepare the Bitmap
		final int width = getWidth();
		final int height = getHeight();
		final int minDimension = Math.min(width, height);
		final Rect rect = new Rect();
		rect.right = minDimension - 1;
		rect.bottom = rect.right;
		mBitmap = Bitmap.createBitmap(minDimension, minDimension, Bitmap.Config.ARGB_8888);
		final Canvas c = new Canvas(mBitmap);
		
		// Create the destination Bitmap and paint it
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.shape1);
		c.drawBitmap(b, null, rect, mPaint);
		
		// Create the source Bitmap, set XferMode, and paint
		b = BitmapFactory.decodeResource(getResources(), R.drawable.shape2);
		mPaint.setXfermode(mXferMode);
		c.drawBitmap(b, null, rect, mPaint);
		
		// Remove the XferMode
		mPaint.setXfermode(null);
	}
	
}
