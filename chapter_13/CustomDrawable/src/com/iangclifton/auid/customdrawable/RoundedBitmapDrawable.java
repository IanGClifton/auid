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

package com.iangclifton.auid.customdrawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

/**
 * Custom Drawable that displays a Bitmap with rounded corners.
 * 
 * You could modify this class to support changing the radius, custom scaling options, etc.
 * 
 * @author Ian G. Clifton
 */
public class RoundedBitmapDrawable extends Drawable {

	private Bitmap mBitmap;
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final int mRadius;
	private RectF mRectF;
	
	public RoundedBitmapDrawable(Bitmap bitmap, int radius) {
		mRadius = radius;
		setBitmap(bitmap);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRoundRect(mRectF, mRadius, mRadius, mPaint);
	}

	@Override
	public int getIntrinsicHeight() {
		if (mBitmap == null) {
			return 0;
		}
		return mBitmap.getHeight();
	}

	@Override
	public int getIntrinsicWidth() {
		if (mBitmap == null) {
			return 0;
		}
		return mBitmap.getWidth();
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
		int oldAlpha = mPaint.getAlpha();
		if (alpha != oldAlpha) {
			mPaint.setAlpha(alpha);
			invalidateSelf();
		}
	}
	
	/**
	 * Sets the {@link Bitmap} to draw and invalidates itself
	 * 
	 * @param bitmap Bitmap to draw with rounded corners
	 */
	public void setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		final Shader shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
		mPaint.setShader(shader);
		invalidateSelf();
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
		invalidateSelf();
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		mRectF = new RectF(bounds);
	}
		
}