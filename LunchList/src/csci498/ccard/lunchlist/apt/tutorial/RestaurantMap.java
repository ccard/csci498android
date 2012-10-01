/*
*Chris Card
*10/1/2012
*/
package csci498.ccard.lunchlist.apt.tutorial;

import android.os.Bundle;

import com.google.android.maps.MapActivity;

import csci498.ccard.lunchlist.R;

public class RestaurantMap extends MapActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
