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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.iangclifton.auid.appendixc.R;

/**
 * An ArrayAdapter for Section objects.
 * 
 * @author Ian G. Clifton
 */
public class SectionAdapter extends ArrayAdapter<Section> {
	
	final LayoutInflater mInflater;

	public SectionAdapter(Context context, Section[] sections) {
		super(context, -1, sections);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.section_list_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.description = (TextView) convertView.findViewById(R.id.description);
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		final Section section = getItem(position);
		viewHolder.title.setText(section.getTitleResourceId());
		viewHolder.description.setText(section.getDescriptionResourceId());
		return convertView;
	}

	private static class ViewHolder {
		/*package*/ TextView description;
		/*package*/ TextView title;
	}
}
