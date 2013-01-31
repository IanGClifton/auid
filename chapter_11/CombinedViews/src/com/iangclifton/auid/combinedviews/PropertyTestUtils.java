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

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Creates Property objects with test data.
 * 
 * @author Ian G. Clifton
 */
public class PropertyTestUtils {
	
	private static final float[] BATHROOMS = {
		1.0f, 1.25f, 1.5f, 1.5f, 1.75f, 1.75f, 2.0f, 2.25f,
	};
	
	private static final int[] BEDROOMS = {
		1, 2, 2, 3, 3, 3, 4, 4,
	};
	
	private static final String[] CITIES = {
		"Seattle", "Seattle", "Seattle", "Redmond", "Bellevue",
		"Bellevue", "Renton", "Kent",
	};
	
	private static final int FOOTAGE_MIN = 900;
	private static final int FOOTAGE_MAX = 3500;
	
	private static final String[] PRICES = {
		"$325,995", "$400,500", "$425,000", "$495,990", "$550,000",
		"$565,000", "$600,000", "$675,000"
	};
	
	private static final String STATE = "WA";
	
	private static final String[] STREET_ADDRESSES = {
		"20 First Court E.",
		"995 Fifth Avenue S.",
		"2557 Silly Loop SW.",
		"5959 Aggravated Drive NE. A905",
		"7010 Old Cedar Drive",
	};
	
	private final Random mRandom;

	/**
	 * Constructs a new PropertyTestUtils object with the specified seed
	 * 
	 * @param seed long to seed the {@link Random} with
	 */
	public PropertyTestUtils(long seed) {
		mRandom = new Random(seed);
	}
	
	/**
	 * Returns ArrayList of Property objects
	 * 
	 * @param count int number of Property objects to return
	 * @return ArrayList of Property objects
	 */
	public ArrayList<Property> getNewProperties(int count) {
		final ArrayList<Property> list = new ArrayList<Property>();
		for (int i = 0; i < count; i++) {
			list.add(getNewProperty());
		}
		return list;
	}
	
	/**
	 * Returns new Property filled with test data
	 * 
	 * @return new Property filled with test data
	 */
	public Property getNewProperty() {
		final JSONObject json = new JSONObject();
		try {
			int randomValue = mRandom.nextInt(BATHROOMS.length);
	        json.put(Property.JSON_KEY_BATHROOMS, BATHROOMS[randomValue]);
	        randomValue = mRandom.nextInt(BEDROOMS.length);
	        json.put(Property.JSON_KEY_BEDROOMS, BEDROOMS[randomValue]);
	        randomValue = mRandom.nextInt(CITIES.length);
	        json.put(Property.JSON_KEY_CITY, CITIES[randomValue]);
	        randomValue = mRandom.nextInt(FOOTAGE_MAX - FOOTAGE_MIN) + FOOTAGE_MIN;
	        json.put(Property.JSON_KEY_FOOTAGE, randomValue);
	        randomValue = mRandom.nextInt(PRICES.length);
	        json.put(Property.JSON_KEY_PRICE, PRICES[randomValue]);
	        json.put(Property.JSON_KEY_STATE, STATE);
	        randomValue = mRandom.nextInt(STREET_ADDRESSES.length);
	        json.put(Property.JSON_KEY_STREET_ADDRESS, STREET_ADDRESSES[randomValue]);
	        return new Property(json);
        } catch (JSONException e) {
	        // This should never happen
        	throw new RuntimeException(e);
        }
	}
}
