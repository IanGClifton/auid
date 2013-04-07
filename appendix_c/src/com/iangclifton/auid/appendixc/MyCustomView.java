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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * View that demonstrates using custom attributes.
 * 
 * The attributes are defined in <code>res/values/attrs.xml</code>.
 * 
 * @author Ian G. Clifton
 */
public class MyCustomView extends View {

	// Enum values
	private final int ZERO_ENUM = 0;
	private final int ONE_ENUM = 1;
	
	// Flag values
	private final int ONE_FLAG = 1;
	private final int TWO_FLAG = 2;
	private final int FOUR_FLAG = 4;
	
	private final String mDisplayString;
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private StaticLayout mLayout;
	private final TextPaint mTextPaint;

	public MyCustomView(Context context) {
		super(context);
		mDisplayString = "No custom attributes";
		mPaint.setColor(Color.BLACK);
		mTextPaint = new TextPaint(mPaint);
		mTextPaint.setTextSize(context.getResources().getDimension(R.dimen.customViewTextSize));
	}

	public MyCustomView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyCustomView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		final TypedArray customAttrs = context.obtainStyledAttributes(attrs, R.styleable.MyCustomView);
		final StringBuilder sb = new StringBuilder();
		int currentAttribute;
		
		// boolean
		currentAttribute = R.styleable.MyCustomView_booleanExample;
		boolean booleanExample = customAttrs.getBoolean(currentAttribute, false);
		sb.append("Boolean: " ).append(booleanExample).append('\n');
				
		// integer
		currentAttribute = R.styleable.MyCustomView_integerExample;
		int integerExample = customAttrs.getInt(currentAttribute, 0);
		sb.append("Integer: " ).append(integerExample).append('\n');
		
		// float
		currentAttribute = R.styleable.MyCustomView_floatExample;
		float floatExample = customAttrs.getFloat(currentAttribute, 0f);
		sb.append("Float: " ).append(floatExample).append('\n');
		
		// fraction
		currentAttribute = R.styleable.MyCustomView_fractionExample;
		float fractionExample = customAttrs.getFraction(currentAttribute, 1, 1, -1);
		sb.append("Fraction: ").append(fractionExample).append('\n');
		
		// string
		currentAttribute = R.styleable.MyCustomView_stringExample;
		String stringExample = customAttrs.getString(currentAttribute);
		sb.append("String: ").append(stringExample).append('\n');
		
		// color
		currentAttribute = R.styleable.MyCustomView_colorExample;
		int colorExample = customAttrs.getColor(currentAttribute, Color.BLACK);
		sb.append("Color: ")
		  .append(Color.alpha(colorExample))
		  .append("a, ")
		  .append(Color.red(colorExample))
		  .append("r, ")
		  .append(Color.green(colorExample))
		  .append("g, ")
		  .append(Color.blue(colorExample))
		  .append("b ")
		  .append('\n');

		// dimension
		currentAttribute = R.styleable.MyCustomView_dimensionExample;
		float dimensionExample = customAttrs.getDimension(currentAttribute, 0);
		sb.append("Dimension: ").append(dimensionExample).append('\n');
		
		// reference
		currentAttribute = R.styleable.MyCustomView_referenceExample;
		int referenceExample = customAttrs.getResourceId(currentAttribute, 0);
		sb.append("Reference: ").append(referenceExample).append('\n');
		
		// enum
		currentAttribute = R.styleable.MyCustomView_enumExample;
		int enumExample = customAttrs.getInt(R.styleable.MyCustomView_enumExample, -1);
		if (enumExample == ZERO_ENUM) {
			sb.append("Enum: ZERO_ENUM\n");
		} else if (enumExample == ONE_ENUM) {
			sb.append("Enum: ONE_ENUM\n");
		} else {
			sb.append("Enum not specified.\n");			
		}
		
		// flag
		currentAttribute = R.styleable.MyCustomView_flagExample;
		int flagExample = customAttrs.getInt(R.styleable.MyCustomView_flagExample, -1);
		if (flagExample == -1) {
			sb.append("Flag not specified.\n");
		} else {
			if ((flagExample & ONE_FLAG) != 0) {
				sb.append("Flag contains ONE_FLAG.\n");
			}
			if ((flagExample & TWO_FLAG) != 0) {
				sb.append("Flag contains TWO_FLAG.\n");
			}
			if ((flagExample & FOUR_FLAG) != 0) {
				sb.append("Flag contains FOUR_FLAG.\n");
			}
		}
		
		// reference OR color
		currentAttribute = R.styleable.MyCustomView_referenceOrColorExample;
		TypedValue tv = customAttrs.peekValue(currentAttribute);
		if (tv == null) {
			sb.append("Did not contain reference or color.\n");
		} else if (tv.type == TypedValue.TYPE_REFERENCE) {
			sb.append("Contained a reference: ").append(tv.data).append('\n');
		} else {
			sb.append("Contained a color: ").append(tv.coerceToString()).append('\n');
		}
		
		// android:textColor
		currentAttribute = R.styleable.MyCustomView_android_textColor;
		tv = customAttrs.peekValue(currentAttribute);
		if (tv == null) {
			mPaint.setColor(Color.BLACK);
		} else {
			mPaint.setColor(tv.data);
		}
		
		customAttrs.recycle();
		mDisplayString = sb.toString();
		mTextPaint = new TextPaint(mPaint);
		mTextPaint.setTextSize(context.getResources().getDimension(R.dimen.customViewTextSize));
	}

	@Override
	public void onDraw(Canvas canvas) {
		mLayout.draw(canvas);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (mLayout == null || changed) {
			mLayout = new StaticLayout(mDisplayString, mTextPaint, right - left, Alignment.ALIGN_NORMAL, 1, 0, true);
		}
	}
}
