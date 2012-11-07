/**
 * Chris Card
 * 8/28/12
 * This class contains the main activity for this project
 */
//Test change for second machine
package csci498.ccard.lunchlist.apt.tutorial;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.widget.CursorAdapter;


import java.util.*;

import csci498.ccard.lunchlist.R;
import csci498.ccard.lunchlist.apt.tutorial.DetailForm;
import csci498.ccard.lunchlist.apt.tutorial.EditPreferences;
import csci498.ccard.lunchlist.apt.tutorial.RestaurantHelper;

public class LunchFragment extends ListFragment 
{

	public final static String ID_EXTRA = "apt.tutorial._ID";

	//stores list of restaurants
	private Cursor model = null;
	
	//list of previous addresses 
	public static List<String> addresses = new ArrayList<String>();
	
	//Array adapter for restaurants
	private RestaurantAdapter adapter = null;
	
	private RestaurantHelper helper;

	private SharedPreferences prefs;

	private OnRestaurantListener listener = null;
	 
	 
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setHasOptionsMenu(true);         
    }

    /**
    * this initializes the list from the cursor
    */
    private void initList()
    {
    	if(model != null)
    	{
    		model.close();
    	}

    	model = helper.getAll(prefs.getString("sort_order", "name"));

        //sets adapter with this activity passed in a simple list item
        //and the list of restaurants
        adapter = new RestaurantAdapter(model);
        
        setListAdapter(adapter);
    }

    @Override
    public void onResume()
    {
    	super.onResume();

    	helper = new RestaurantHelper(getActivity());

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        initList();
        prefs.registerOnSharedPreferenceChangeListener(prefListener);
    }

    @Override
    public void onPause()
    {
    	helper.close();
    	super.onPause();
    }
    

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
    	inflater.inflate(R.menu.option, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	if(item.getItemId() == R.id.add)
    	{
    		startActivity(new Intent(getActivity(), DetailForm.class));
    		return true;
    	}
    	else if(item.getItemId() == R.id.prefs)
    	{
    		startActivity(new Intent(getActivity(), EditPreferences.class));
    		return true;
    	}
    	else if (item.getItemId() == R.id.help) 
    	{
    		startActivity(new Intent(getActivity(), HelpPage.class));
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }

    @Override
   	public void onListItemClick(ListView list, View view, int position, long id) 
   	{
		if (listener != null) 
		{
			listener.onRestaurantSelected(id);		
		}		
	}

	public void setOnRestaurantListener(OnRestaurantListener listener)
	{
		this.listener = listener;
	}
    
    /**
     * This is a listener for a preference change from editpreferences
     */
   	private SharedPreferences.OnSharedPreferenceChangeListener prefListener= new SharedPreferences.OnSharedPreferenceChangeListener()
   	{
   		public void onSharedPreferenceChanged(SharedPreferences sharedPrefs, String key)
   		{
   			if(key.equals("sort_order"))
   			{
   				initList();
   			}
   		}
   	};

	/**
	 * This class holds the RestaurantAdapter for populating the listview with the restaurants
	 * @author Chris
	 *
	 */
	class RestaurantAdapter extends CursorAdapter
	{
		
		RestaurantAdapter(Cursor c)
		{
			super(getActivity(), c);
		}
		
		@Override
		public void bindView(View row, Context ctxt, Cursor c)
		{
			RestaurantHolder holder = (RestaurantHolder)row.getTag();

			holder.populateForm(c, helper);
		}

		@Override
		public View newView(Context ctxt, Cursor c, ViewGroup parent)
		{
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			RestaurantHolder holder = new RestaurantHolder(row);

			row.setTag(holder);

			return row;
		}
	}

	public interface OnRestaurantListener
	{
		void onRestaurantSelected(long id);
	}
	
	/**
	 * This static class is used to populate the RestaurantAdapter rows
	 * @author Chris
	 *
	 */
	static class RestaurantHolder
	{
		
		private TextView name = null;
		private TextView address = null;
		private ImageView icon = null;
		
		RestaurantHolder(View row)
		{
			name = (TextView)row.findViewById(R.id.title);
			address = (TextView)row.findViewById(R.id.address);
			icon = (ImageView)row.findViewById(R.id.icon);
		}
		
		void populateForm(Cursor c, RestaurantHelper helper)
		{
			name.setText(helper.getName(c));
			address.setText(helper.getAddress(c));
			
			if(helper.getType(c).equals("sit_down"))
			{
				name.setTextColor(Color.RED);
				icon.setImageResource(R.drawable.ball_red);
			}
			else if(helper.getType(c).equals("take_out"))
			{
				name.setTextColor(Color.MAGENTA);
				icon.setImageResource(R.drawable.ball_yellow);
			}
			else
			{
				name.setTextColor(Color.GREEN);
				icon.setImageResource(R.drawable.ball_green);
			}
		}
	}
}
