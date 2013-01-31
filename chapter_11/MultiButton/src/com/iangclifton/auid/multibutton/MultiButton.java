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

package com.iangclifton.auid.multibutton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

/**
 * A LinearLayout that creates multiple ToggleButtons, only one of which can
 * be selected at a time.
 * 
 * @author Ian G. Clifton
 */
public class MultiButton extends LinearLayout implements OnClickListener {
	
	private static final int DEFAULT = -1;

	private OnMultiButtonCheckedListener mListener;
	private CompoundButton mSelectedButton;
	
	/**
	 * Interface that listens to the MultiButton being checked
	 */
	public interface OnMultiButtonCheckedListener {
		
		/**
		 * Notifies the interface that a new button has been checked
		 * 
		 * @param checked CompoundButton that is now checked
		 * @param unchecked CompoundButton that was checked previously
		 */
		public void onMultiButtonChecked(CompoundButton checked, CompoundButton unchecked);
	}
	
	public MultiButton(Context context, int textArrayResourceId) {
		super(context);
		init(context, null, textArrayResourceId);
	}

	public MultiButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, -1);
	}

	@SuppressLint("NewApi")
    public MultiButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, -1);
	}
	
	/**
	 * Returns the currently checked button
	 * 
	 * @return CompoundButton that is currently checked
	 */
	public CompoundButton getCheckedButton() {
		return mSelectedButton;
	}

	@Override
	public void onClick(View v) {
		final CompoundButton button = (CompoundButton) v;
		button.setChecked(true);

		if (v != mSelectedButton) {
			mSelectedButton.setChecked(false);
			if (mListener != null) {
				mListener.onMultiButtonChecked(button, mSelectedButton);
			}
			mSelectedButton = button;
		}
	}

	/**
	 * Sets the listener to be notified of a new button being checked
	 * 
	 * @param listener OnMultiButtonCheckedListener to notify, can be null
	 */
	public void setOnMultiButtonCheckedListener(OnMultiButtonCheckedListener listener) {
		mListener = listener;
	}
	
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
    	if (!(state instanceof SavedState)) {
    		// Not our SavedState, so call super and be done with it
    		super.onRestoreInstanceState(state);
    		return;
    	}
    	
    	final SavedState ourState = (SavedState) state;
	    super.onRestoreInstanceState(ourState.getSuperState());
	    mSelectedButton.setChecked(false);
	    mSelectedButton = (CompoundButton) getChildAt(ourState.selectedPosition);
	    mSelectedButton.setChecked(true);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
    	final Parcelable superState = super.onSaveInstanceState();
    	final SavedState ourState = new SavedState(superState);
    	final int childCount = getChildCount();
    	for (int i = 0; i < childCount; i++) {
    		if (getChildAt(i) == mSelectedButton) {
    			ourState.selectedPosition = i;
    			break;
    		}
    	}
    	return ourState;
    }

	/**
	 * Initializes the Buttons inside this View
	 * 
	 * @param context Context to create Buttons with
	 * @param attrs AttributeSet or null
	 */
	private void init(Context context, AttributeSet attrs, int textArrayResourceId) {
		setSaveEnabled(true);
		
		final CharSequence[] textArray;
		int buttonBackground = DEFAULT;
		int buttonBackgroundEnd = DEFAULT;
		ColorStateList textColors = null;
		if (attrs == null) {
			textArray = context.getResources().getTextArray(textArrayResourceId);
		} else {
			// Load custom attributes
			final TypedArray customAttrs = context.obtainStyledAttributes(attrs, R.styleable.MultiButton);
			buttonBackground = customAttrs.getResourceId(R.styleable.MultiButton_buttonBackground, DEFAULT);
			buttonBackgroundEnd = customAttrs.getResourceId(R.styleable.MultiButton_buttonBackgroundEnd, DEFAULT);
			if (buttonBackgroundEnd == DEFAULT) {
				buttonBackgroundEnd = buttonBackground;
			}
			textColors = customAttrs.getColorStateList(R.styleable.MultiButton_android_textColor);
			textArray = customAttrs.getTextArray(R.styleable.MultiButton_textArray);
			customAttrs.recycle();
		}
		
		if (textArray == null) {
			throw new InflateException("MultiButton requires a textArray");
		}
		
		// Loop through CharSequences to create ToggleButtons
		for (int i = 0; i < textArray.length; i++) {
			final CharSequence text = textArray[i];
			final ToggleButton button = new ToggleButton(context);
			addView(button);
			button.setOnClickListener(this);
			button.setText(text);
			button.setTextOff(text);
			button.setTextOn(text);
			
			// Add weight
			LayoutParams lp = (LayoutParams) button.getLayoutParams();
			lp.weight = 1f;
			
			// Assign text color
			if (textColors != null) {
				button.setTextColor(textColors);
			}
			
			// Assign custom backgrounds
			if (i == textArray.length - 1) {
				if (buttonBackgroundEnd != DEFAULT) {
					button.setBackgroundResource(buttonBackgroundEnd);
				}
			} else {
				if (buttonBackground != DEFAULT) {
					button.setBackgroundResource(buttonBackground);
				}
			}
			
			// Obtain reference to first button
			if (mSelectedButton == null) {
				mSelectedButton = button;
				mSelectedButton.setChecked(true);
			}
		}
	}
	
	private static class SavedState extends BaseSavedState {
		
		private int selectedPosition;

		@SuppressWarnings("unused")
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};

		public SavedState(Parcelable superState) {
	        super(superState);
        }
		
		private SavedState(Parcel in) {
			super(in);
			selectedPosition = in.readInt();
		}
		
        @Override
        public void writeToParcel(Parcel dest, int flags) {
	        super.writeToParcel(dest, flags);
	        dest.writeInt(selectedPosition);
        }
	}
}
