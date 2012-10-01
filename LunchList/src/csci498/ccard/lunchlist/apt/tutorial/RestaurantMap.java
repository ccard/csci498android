/*
*Chris Card
*10/1/2012
*/
package csci498.ccard.lunchlist.apt.tutorial;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import csci498.ccard.lunchlist.R;

public class RestaurantMap extends MapActivity
{
    public static final String EXTRA_LATITUDE = "apt.tutorial.EXTRA_LATITUDE";
    public static final String EXTRA_LONGITUDE= "apt.tutorial.EXTRA_LONGITUDE";
    public static final String EXTRA_NAME = "apt.tutorial.EXTRA_NAME";

    private double lat;
    private double lon;

    private MapView map = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		lat = getIntent().getDoubleExtra(EXTRA_LATITUDE, 0);
		lon = getIntent().getDoubleExtra(EXTRA_LONGITUDE, 0);

		map = (MapView)findViewById(R.id.map);

		map.getController().setZoom(17);

		GeoPoint status = new GeoPoint((int)(lat*1000000.0), (int)(lon*1000000.0));

		map.getController().setCenter(status);
		map.setBuiltInZoomControls(true);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	private class RestaurantOverlay extends ItemizedOverlay<OverlayItem>
	{
		private OverlayItem item = null;

		public RestaurantOverlay(Drawable marker, GeoPoint point, String name)
		{
			super(marker);

			boundCenterBottom(marker);
		}
	}
}
