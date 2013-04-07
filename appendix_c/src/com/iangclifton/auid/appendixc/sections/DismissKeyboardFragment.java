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

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.iangclifton.auid.appendixc.R;

/**
 * Fragment that demonstrates how to dismiss the keyboard.
 * 
 * @author Ian G. Clifton
 */
public class DismissKeyboardFragment extends Fragment {

	public DismissKeyboardFragment() {
		// Required default constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.dismiss_keyboard, container, false);
		final EditText editText = (EditText) rootView.findViewById(R.id.editText);
		final CharSequence dismissText = getString(R.string.dismiss);
		editText.addTextChangedListener(new KeyboardDismissWatcher(editText, dismissText));
		
		return rootView;
	}

	/**
	 * TextWatcher that listens for the dismiss text and dismisses the keyboard.
	 * 
	 * @author Ian G. Clifton
	 */
	private class KeyboardDismissWatcher implements TextWatcher {
		
		private final int mDismissTextLength;
		private final CharSequence mDismissText;
		private final EditText mEditText;
		
		/*package*/ KeyboardDismissWatcher(EditText editText, CharSequence dismissText) {
			mDismissText = dismissText;
			mDismissTextLength = mDismissText.length();
			mEditText = editText;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// Ignore
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// Ignore
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// Look for dismiss word
			final int length = s.length();
			if (length < mDismissTextLength) {
				// Text is too short, ignore
				return;
			}
			
			// Text long enough
			final CharSequence lastChars = s.subSequence(length - mDismissTextLength, length);
			if (TextUtils.equals(lastChars, mDismissText)) {
				// Text matches, dismiss keyboard
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
			} else {
				Log.e("", lastChars + " did not match " + mDismissText);
			}
		}
		
	}
}
