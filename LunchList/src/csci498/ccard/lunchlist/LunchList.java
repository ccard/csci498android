/**
 * Chris Card
 * 8/28/12
 * This class contains the main activity for this project
 */
//Test change for second machine
package csci498.ccard.lunchlist;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.*;
import android.widget.TabHost;

public class LunchList extends TabActivity {

	//stores list of restaurants
	List<Restaurant> model = new ArrayList<Restaurant>();
	
	//list of previous addresses 
	public static List<String> addresses = new ArrayList<String>();
	
	//Array adapter for restaurants
	RestaurantAdapter adapter = null;
	
	//Array adapter for autocomplete adress
	ArrayAdapter<String> autoAdapter = null;
	
	//access to the auto complete text view so we
	//can update it with reacently added addresses
	 AutoCompleteTextView acText = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //this sets the tab 1 up to display the list of restaurants
        TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.restaurants);
        spec.setIndicator("List", getResources().getDrawable(R.drawable.list));
        getTabHost().addTab(spec);
        
        //this sets tab 2 up to get the users information for the restaurants
        spec = getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("Details", getResources().getDrawable(R.drawable.restaurant));
        getTabHost().addTab(spec);
        getTabHost().setCurrentTab(0);
        
        //stores the save button from the main.xml file
        Button save = (Button)findViewById(R.id.save);
        
        //adds and on click listener
        save.setOnClickListener(onSave);
        
        ListView list = (ListView)findViewById(R.id.restaurants);
        
        //sets addapter with this activity passed in a simple list item
        //and the list of restaurants
        adapter = new RestaurantAdapter();
        
        list.setAdapter(adapter);
        
        acText = (AutoCompleteTextView)findViewById(R.id.addr);
        
        addresses.add("the Mall");
        //creates new list addapter for autocomplete text view
        autoAdapter = new ArrayAdapter<String>(this,
        							android.R.layout.simple_dropdown_item_1line, addresses);
        
        acText.setAdapter(autoAdapter);
        
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
		
		public void onClick(View v) {
			
			Restaurant r = new Restaurant();
			
			EditText name = (EditText)findViewById(R.id.name);
			AutoCompleteTextView address = (AutoCompleteTextView)findViewById(R.id.addr);
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			
			//creates functionality for radiogroup added via xml
			RadioGroup types = (RadioGroup)findViewById(R.id.types);
			//Determines the type of restaurant and adds the type to r
			switch(types.getCheckedRadioButtonId())
			{
				case R.id.sit_down:
					r.setType("sit_down");
					break;
				case R.id.take_out:
					r.setType("take_out");
					break;
				case R.id.delivery:
					r.setType("delivery");
					break;
			}
			name.setText("");
			address.setText("");
		
			//adds new component to the adapter then sets acText's adapter to the
			//newly modified adapter
			autoAdapter.add(r.getAddress());
			acText.setAdapter(autoAdapter);
			
			//adds the restaurant the user just created to the adapter
			adapter.add(r);
		}
	};
    
	/**
	 * This class holds the RestaurantAdapter for populating the listview with the restaurants
	 * @author Chris
	 *
	 */
	class RestaurantAdapter extends ArrayAdapter<Restaurant>{
		
		RestaurantAdapter()
		{
			super(LunchList.this, android.R.layout.simple_list_item_1, model);
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View row = convertView;
			RestaurantHolder holder = null;
			
			if(row == null)
			{
				LayoutInflater inflater = getLayoutInflater();
				
				/*
				 * determines what kind of row layout should be used
				 */
		/*		switch (getItemViewType(position))
				{
					case 1:
						row = inflater.inflate(R.layout.row, null);
						holder = new RestaurantHolder(row);
						row.setTag(holder);
						break;
					case 2:
						row = inflater.inflate(R.layout.row_takeout, null);
						holder = new RestaurantHolder(row);
						row.setTag(holder);
						break;
					default:
						row = inflater.inflate(R.layout.row_delivery, null);
						holder = new RestaurantHolder(row);
						row.setTag(holder);
						break;
				}*/
				row = inflater.inflate(R.layout.row, null);
				holder = new RestaurantHolder(row);
				row.setTag(holder);
			}
			else
			{
				holder = (RestaurantHolder)row.getTag();
			}
			
			holder.populateForm(model.get(position));
			
			return row;
		}
		
		/**
		 * this overrides getviewitemtype to return a number based on the
		 * type of restaurant
		 * @param position of the item in the list
		 * @return 1 for sit_down, 2 for take_out, 3 for delivery
		 */
	/*	public int getItemViewType(int position)
		{
			if(model.get(position).getType().equals("sit_down"))
			{
				return 1;
			}
			else if(model.get(position).getType().equals("take_out"))
			{
				return 2;
			}
			else
			{
				return 3;
			}
		}
		
		public int getViewTypeCount()
		{
			return 3;
		}*/
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
		
		void populateForm(Restaurant r)
		{
			name.setText(r.getName());
			address.setText(r.getAddress());
			
			if(r.getType().equals("sit_down"))
			{
				name.setTextColor(Color.RED);
				icon.setImageResource(R.drawable.ball_red);
			}
			else if(r.getType().equals("take_out"))
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
