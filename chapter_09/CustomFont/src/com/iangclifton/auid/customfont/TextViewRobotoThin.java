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

package com.iangclifton.auid.customfont;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A TextView that uses the Roboto thin font.
 * 
 * @author Ian G. Clifton
 */
public class TextViewRobotoThin extends TextView {
	
	/**
	 * This is the name of the font file within the assets folder
	 */
    public static final String FONT_LOCATION = "roboto_thin.ttf";

    private static Typeface sTypeface;

	public TextViewRobotoThin(Context context) {
		super(context);
		init(context);
	}

	public TextViewRobotoThin(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TextViewRobotoThin(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
    
	/**
	 * Returns the Typeface for Roboto thin
	 * 
	 * @param context Context to access the app's assets
	 * @return Typeface for Roboto thin
	 */
    public static Typeface getTypeface(Context context) {
        if (sTypeface == null) {
            sTypeface = Typeface.createFromAsset(context.getAssets(), FONT_LOCATION);
        }
        return sTypeface;
    }

	/**
	 * Initializes this TextView to the Roboto thin font
	 * 
	 * @param context Context to access AssetManager
	 */
	private void init(Context context) {
		if (isInEditMode()) {
            if (TextUtils.isEmpty(getText())) {
                setText("Roboto thin");
            }
			return;
		}
		
		setTypeface(getTypeface(context));
	}
}
