/*
*Chris Card
*9/17/12
*
*/
package csci498.ccard.lunchlist.apt.tutorial;

import csci498.ccard.lunchlist.LunchList;
import csci498.ccard.lunchlist.R;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DetailFragment extends Fragment 
{
	 
	//these store the access to the fields the user uses to input info
	private EditText name = null;
	private EditText address = null;
	private RadioGroup types = null;
	private EditText notes = null;
    private EditText feed = null;
    private TextView location = null;
    private EditText phone = null;

	private RestaurantHelper helper;

	private String restaurantId = null;

    LocationManager locMgr = null;

    private double latitude = 0.0d;
    private double longitude = 0.0d;

    private static final String ARG_REST_ID = "apt.tutorial.ARG_REST_ID";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        locMgr = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        
        //initialize the view items
        initViewItems();

        Bundle args = getArguments();

        if (args != null) 
        {
            loadRestaurant(args.getString(ARG_REST_ID));    
        }
    }

    public void loadRestaurant(String restaurantId)
    {
        this.restaurantId = restaurantId;

        if (restaurantId != null) 
        {
            load();
        }
    }

    public static DetailFragment newInstance(long id)
    {
        DetailFragment result = new DetailFragment();
        Bundle args = new Bundle();

        args.putString(ARG_REST_ID, String.valueOf(id));
        result.setArguments(args);

        return result;
    }

    public RestaurantHelper getHelper()
    {
        if (helper == null) 
        {
            helper = new RestaurantHelper(getActivity());    
        }

        return helper;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.detail_form, container, false);
    }
    
    /**
     * getActivity() initializes the view items edit text, radiobuttons, and autocomplete
     */
    private void initViewItems()
    {
    	 //initializing the global views to the view from the xml files
        name = (EditText)getView().findViewById(R.id.name);
		address = (EditText)getView().findViewById(R.id.addr);
		types = (RadioGroup)getView().findViewById(R.id.types);
        notes = (EditText)getView().findViewById(R.id.notes);
        feed = (EditText)getView().findViewById(R.id.feed);
        location = (TextView)getView().findViewById(R.id.location);
        phone = (EditText)getView().findViewById(R.id.phone);
    }


    public void load()
    {
    	Cursor c = getHelper().getById(restaurantId);

    	c.moveToFirst();
    	name.setText(getHelper().getName(c));
    	address.setText(getHelper().getAddress(c));
    	notes.setText(getHelper().getNotes(c));
        feed.setText(getHelper().getFeed(c));

    	if(getHelper().getType(c).equals("sit_down"))
    	{
    		types.check(R.id.sit_down);
    	}
    	else if(getHelper().getType(c).equals("take_out"))
    	{
    		types.check(R.id.take_out);
    	}
    	else
    	{
    		types.check(R.id.delivery);
    	}

        latitude = getHelper().getLatitude(c);
        longitude = getHelper().getLongitude(c);
        location.setText(String.valueOf(getHelper().getLatitude(c))+", "+String.valueOf(helper.getLongitude(c)));

    	c.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
       inflater.inflate(R.menu.details_option, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.feed)
        {
            if(isNetworkAvailable())
            {
                Intent i = new Intent(getActivity(), FeedActivity.class);

                i.putExtra(FeedActivity.FEED_URL, feed.getText().toString());
                startActivity(i);
            }
            else
            {
                Toast.makeText(getActivity(), "Sorry , the Internet is not available", Toast.LENGTH_LONG).show();
            }
            return true;
        }
        else if (item.getItemId() == R.id.location)
        {
            locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, onLocationChange);

            return true;
        }
        else if (item.getItemId() == R.id.map) 
        {
            Intent i = new Intent(getActivity(), RestaurantMap.class);

            i.putExtra(RestaurantMap.EXTRA_LATITUDE, latitude);
            i.putExtra(RestaurantMap.EXTRA_LONGITUDE, longitude);
            i.putExtra(RestaurantMap.EXTRA_NAME, name.getText().toString());
            startActivity(i);

            return true;
        }
        else if (item.getItemId() == R.id.help) 
        {
            startActivity(new Intent(getActivity(), HelpPage.class));
            return true;
        }
        else if (item.getItemId()  == R.id.call) 
        {
            String toDial = "tel:"+phone.getText().toString();

            if (toDial.length() > 4) 
            {
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(toDial)));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        if (restaurantId == null)
        {
            menu.findItem(R.id.location).setEnabled(false);
            menu.findItem(R.id.map).setEnabled(false);
        }

        if (isTelephonyAvailable()) 
        {
            menu.findItem(R.id.call).setEnabled(true);
        }

    }

    LocationListener onLocationChange = new LocationListener()
    {
        public void onLocationChanged(Location fix)
        {
            helper.updateLocation(restaurantId, fix.getLatitude(), fix.getLongitude());

            location.setText(String.valueOf(fix.getLatitude())+", "+String.valueOf(fix.getLongitude()));

            locMgr.removeUpdates(onLocationChange);

            Toast.makeText(getActivity(), "Location saved", Toast.LENGTH_LONG).show();
        }

		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
    };

    private boolean isNetworkAvailable()
    {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return (info != null);
    }

    private boolean isTelephonyAvailable()
    {
        return getActivity().getPackageManager().hasSystemFeature("android.hardware.telephony");
    }

    @Override
    public void onSaveInstanceState(Bundle state)
    {
        super.onSaveInstanceState(state);

        state.putString("name", name.getText().toString());
        state.putString("address", address.getText().toString());
        state.putString("notes", notes.getText().toString());
        state.putInt("type", types.getCheckedRadioButtonId());
    }

    @Override
    public void onPause()
    {
        save();
        locMgr.removeUpdates(onLocationChange);
        helper.close();
        super.onPause();
    }

	/**
     * save method to save the restaurants
     */
    private void save()
	{
        if (name.getText().toString().length() > 0)
        {
    	   String type = null;
    		
    		//Determines the type of restaurant and adds the type to r
    		switch(types.getCheckedRadioButtonId())
    		{
    			case R.id.sit_down:
    				type = "sit_down";
    				break;
    			case R.id.take_out:
    				type = "take_out";
    				break;
    			case R.id.delivery:
    				type = "delivery";
    				break;
    		}
    		
    		if(restaurantId == null)
    		{
    			helper.insert(name.getText().toString(), address.getText().toString(), type, notes.getText().toString(), feed.getText().toString(), phone.getText().toString());
    		}
    		else
    		{
    			helper.update(restaurantId, name.getText().toString(), address.getText().toString(), type, notes.getText().toString(), feed.getText().toString(), phone.getText().toString());
    		}
        }
	}
}