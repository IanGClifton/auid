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

package com.iangclifton.auid.viewanimations;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Activity that demonstrates a basic View Animation.
 * 
 * @author Ian G. Clifton
 */
public class MainActivity extends Activity implements OnClickListener {

	private Animation mAnimation;
	private View mAnimationTarget;
	
	@Override
	public void onClick(View v) {
		mAnimationTarget.startAnimation(mAnimation);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Load the Animation
		mAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
		
		// Get a reference to the target
		mAnimationTarget = findViewById(R.id.animation_target);
		
		// Set OnClickListener on the button
		findViewById(R.id.button_animate).setOnClickListener(this);
	}
}
