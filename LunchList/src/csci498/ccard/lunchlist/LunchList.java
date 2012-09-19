/**
 * Chris Card
 * 8/28/12
 * This class contains the main activity for this project
 */
//Test change for second machine
package csci498.ccard.lunchlist;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ListActivity;
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

import csci498.ccard.lunchlist.apt.tutorial.DetailForm;
import csci498.ccard.lunchlist.apt.tutorial.EditPreferences;
import csci498.ccard.lunchlist.apt.tutorial.RestaurantHelper;

public class LunchList extends ListActivity {

	public final static String ID_EXTRA = "apt.tutorial._ID";

	//stores list of restaurants
	private Cursor model = null;
	
	//list of previous addresses 
	public static List<String> addresses = new ArrayList<String>();
	
	//Array adapter for restaurants
	private RestaurantAdapter adapter = null;
	
	private RestaurantHelper helper;

	private SharedPreferences prefs;
	 
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        helper = new RestaurantHelper(this);
        
        model = helper.getAll(prefs.getString("sort_order", "name"));
        startManagingCursor(model);

        //sets adapter with this activity passed in a simple list item
        //and the list of restaurants
        adapter = new RestaurantAdapter(model);
        
        setListAdapter(adapter);
    }

    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	helper.close();
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	new MenuInflater(this).inflate(R.menu.option, menu);

    	return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	if(item.getItemId() == R.id.add)
    	{
    		startActivity(new Intent(LunchList.this, DetailForm.class));
    		return true;
    	}
    	else if(item.getItemId() == R.id.prefs)
    	{
    		startActivity(new Intent(this, EditPreferences.class));
    		return true;
    	}
    	return (super.onOptionsItemSelected(item));
    }

    @Override
   public void onListItemClick(ListView list, View view, int position, long id) {
				
			Intent i = new Intent(LunchList.this, DetailForm.class);

			i.putExtra(ID_EXTRA, String.valueOf(id));
			startActivity(i);
		}
    
    
	/**
	 * This class holds the RestaurantAdapter for populating the listview with the restaurants
	 * @author Chris
	 *
	 */
	class RestaurantAdapter extends CursorAdapter{
		
		RestaurantAdapter(Cursor c)
		{
			super(LunchList.this, c);
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
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.row, parent, false);
			RestaurantHolder holder = new RestaurantHolder(row);

			row.setTag(holder);

			return row;
		}

		
	
	}
	
	/**
	 * This static class is used to populate the RestaurantAdapter rows
	 * @author Chris
	 *
	 */
	static class RestaurantHolder{
		
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
