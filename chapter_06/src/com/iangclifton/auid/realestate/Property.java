package com.iangclifton.auid.realestate;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Represents a single Property such as a house or a condo.
 * 
 * This is thread safe and implements Parcelable for passing between Activities.
 * In this case, the Property simply reuses the raw JSON String to pass around
 * so that JSONObject can do the heavy lifting.
 * 
 * In the real world, the data for each of these objects would be coming from
 * a server somewhere, so this object is modeled after a possible JSON
 * representation.
 * 
 * @author Ian G. Clifton
 */
public class Property implements Parcelable {
	private static final String TAG = "Property";
	
	public static final String JSON_KEY_BATHROOMS = "bathroomCount";
	public static final String JSON_KEY_BEDROOMS = "bedroomCount";
	public static final String JSON_KEY_CITY = "city";
	public static final String JSON_KEY_FOOTAGE= "footage";
	public static final String JSON_KEY_PRICE = "displayPrice";
	public static final String JSON_KEY_STATE = "state";
	public static final String JSON_KEY_STREET_ADDRESS = "address";
		
	/**
	 * The number of bathrooms like "1.5"
	 */
	private final float mBathroomCount;
	
	/**
	 * The number of bedrooms
	 */
	private final int mBedroomCount;
	
	/**
	 * The name of the city like "Seattle"
	 */
	private final String mCity;
	
	/**
	 * Square footage of the property like "2200"
	 */
	private final int mFootage;
	
	/**
	 * The displayable price of the property like "$490,000"
	 */
	private final String mPrice;
	
	/**
	 * The raw JSON representation of the object
	 */
	private final String mRawJson;
	
	/**
	 * The abbreviated name of the state like "WA"
	 */
	private final String mState;
	
	/**
	 * The street address like "123 Main St."
	 */
	private final String mStreetAddress;

	public Property(JSONObject json) throws JSONException {
		mBathroomCount = (float) json.getDouble(JSON_KEY_BATHROOMS);
		mBedroomCount = json.getInt(JSON_KEY_BEDROOMS);
		mCity = json.getString(JSON_KEY_CITY);
		mFootage = json.getInt(JSON_KEY_FOOTAGE);
		mPrice = json.getString(JSON_KEY_PRICE);
		mState = json.getString(JSON_KEY_STATE);
		mStreetAddress = json.getString(JSON_KEY_STREET_ADDRESS);
		mRawJson = json.toString();
	}

	@Override
    public int describeContents() {
	    return 0;
    }

	/**
	 * Returns the number of bathrooms like "1.5"
	 * 
	 * @return the bathroomCount
	 */
	public float getBathroomCount() {
		return mBathroomCount;
	}

	/**
	 * Returns the number of bedrooms
	 * 
	 * @return the bedroomCount
	 */
	public int getBedroomCount() {
		return mBedroomCount;
	}

	/**
	 * Returns the name of the city like "Seattle"
	 * 
	 * @return the city
	 */
	public String getCity() {
		return mCity;
	}

	/**
	 * Returns the square footage of the property like "2200"
	 * 
	 * @return the footage
	 */
	public int getFootage() {
		return mFootage;
	}

	/**
	 * Returns the displayable price of the property like "$490,000"
	 * 
	 * @return the price
	 */
	public String getPrice() {
		return mPrice;
	}

	/**
	 * Returns the abbreviated name of the state like "WA"
	 * 
	 * @return the state
	 */
	public String getState() {
		return mState;
	}

	/**
	 * Returns the street address like "123 Main St."
	 * 
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return mStreetAddress;
	}

	@Override
    public void writeToParcel(Parcel dest, int flags) {
	    dest.writeString(mRawJson);
    }
	
	/**
	 * Parcelable.Creator required to construct a Property object from a Parcel.
	 */
	public static final Parcelable.Creator<Property> CREATOR = new Parcelable.Creator<Property>() {

		@Override
        public Property createFromParcel(Parcel source) {
			final String rawJson = source.readString();
            try {
	            final JSONObject jsonObject = new JSONObject(rawJson);
		        return new Property(jsonObject);
            } catch (JSONException e) {
	            // In theory, it's impossible to get here
            	Log.e(TAG, "Failed to create Property from JSON String: " + e.getMessage());
            	return null;
            }
        }

		@Override
        public Property[] newArray(int size) {
	        return new Property[size];
        }
		
	};
}
