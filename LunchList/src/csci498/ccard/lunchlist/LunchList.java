/**
 * Chris Card
 * 8/28/12
 * This class contains the main activity for this project
 */
//Test change for second machine
package csci498.ccard.lunchlist;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.atomic.AtomicBoolean;


import java.util.*;
import android.widget.TabHost;

public class LunchList extends TabActivity {

	//stores list of restaurants
	private List<Restaurant> model = new ArrayList<Restaurant>();
	
	//list of previous addresses 
	public static List<String> addresses = new ArrayList<String>();
	
	//Array adapter for restaurants
	private RestaurantAdapter adapter = null;
	
	//Array adapter for autocomplete adress
	private ArrayAdapter<String> autoAdapter = null;
	 
	//these store the access to the fields the user uses to input info
	private EditText name = null;
	private AutoCompleteTextView address = null;
	private RadioGroup types = null;
	private EditText notes = null;
	private Restaurant current = null;
	 
	 private AtomicBoolean isActive = new AtomicBoolean(true);
	 
	 //storage for the progress of the progress bar
	 private int progress;
	 
	 //Extra Credit
	 private Bundle thisInstance;
	 
	 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_main);
        
        //Extra credit
        if(savedInstanceState != null)
        {
        	progress = savedInstanceState.getInt("progress");
        		startWork();
        	thisInstance = savedInstanceState;
        }
        else
        {
        	thisInstance = new Bundle();
        }
        
     
        
        //initialize the view items
        initViewItems();
	
        //initializing the tabhosts
        initTabHost();
        
        //stores the save button from the main.xml file
        Button save = (Button)findViewById(R.id.save);
        
        //adds and on click listener
        save.setOnClickListener(onSave);
        
        ListView list = (ListView)findViewById(R.id.restaurants);
        list.setOnItemClickListener(onListClick);
        
        //sets adapter with this activity passed in a simple list item
        //and the list of restaurants
        adapter = new RestaurantAdapter();
        
        list.setAdapter(adapter);
        
        //addes initail item to addresses
        addresses.add("the Mall");
        //creates new list adapter for autocomplete text view
        autoAdapter = new ArrayAdapter<String>(this,
        							android.R.layout.simple_dropdown_item_1line, addresses);
        
        address.setAdapter(autoAdapter);
        
    }
    
    //Extra Credit
    public void onSaveIntstanceState(Bundle bundle)
    {
    	super.onSaveInstanceState(bundle);
    	bundle.putInt("progress", progress);
    }
    
    /**
     * this initializes the view items edit text, radiobuttons, and autocomplete
     */
     private void initViewItems()
    {
    	 //initializing the global views to the view from the xml files
        name = (EditText)findViewById(R.id.name);
		address = (AutoCompleteTextView)findViewById(R.id.addr);
		types = (RadioGroup)findViewById(R.id.types);
        notes = (EditText)findViewById(R.id.notes);
    }

    /**
     * This method initializes the tabhost of the main view
     */
    private void initTabHost()
    {
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
    }
    
    
   @Override
    public void onResume()
   {
	   super.onResume();
	   isActive.set(true);
	   
	   if(progress > 0)
	   {
		   startWork();
	   }
   }
   
   private void startWork()
   {
	   setProgressBarVisibility(true);
	   new Thread(longTask).start();
   }
    
    private void doSomeLongWork(final int incr) {
    	runOnUiThread(new Runnable() {
    		public void run() {
    		progress+=incr;
    		setProgress(progress);
    		
    		}
    	});
    	SystemClock.sleep(250); 
    }
    
    private Runnable longTask=new Runnable() {
    	public void run() {
    		for (int i=progress;i<10000 && isActive.get();i+=200) {
    				doSomeLongWork(200);
    		}
    		if(isActive.get())
    		{
    		runOnUiThread(new Runnable() {
    			public void run() {
    			 setProgressBarVisibility(false);
    			 progress = 0;
    			}
    		});
    		}
    	}	
    	
    };
    
    //Extra Credit
    @Override
    public void onStop()
    {
    	super.onStop();
    	onSaveInstanceState(thisInstance);
    }
    
    @Override
    public void onPause()
    {
    	super.onPause();
    	isActive.set(false);
    }
    
    /**
     * This method displays the menu so that the user can see it
     */
    @Override 
    public boolean onCreateOptionsMenu(Menu menu){
    	//this inflates the menu so that it can be seen
    	new MenuInflater(this).inflate(R.menu.option, menu);
    	
    	return (super.onCreateOptionsMenu(menu));
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	//checks for the menu item being selected
    	if(item.getItemId() == R.id.toast)
    	{
    		String message = "No restaurant selected";
    		
    		if(current != null)
    		{
    			message = current.getNotes();
    		}
    		
    		//primary tutorial
    		Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    		
    		return true;
    	}
    	else if(item.getItemId() == R.id.run )
    	{
    		startWork();
    		return true;
    	}
    	
    	return (super.onOptionsItemSelected(item));
    }

    /**
     * stores the item click listener for list view in the list tab
     */
    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
				
			current = model.get(position);
			
			//sets the details tab fields to the current name address and notes
			name.setText(current.getName());
			address.setText(current.getAddress());
			notes.setText(current.getNotes());
			
			//selects the apropriate radio button
			if(current.getType().equals("sit_down"))
			{
				types.check(R.id.sit_down);
			}
			else if(current.getType().equals("take_out"))
			{
				types.check(R.id.take_out);
			}
			else
			{
				types.check(R.id.delivery);
			}
			
			//switches the tab to the details tab
			getTabHost().setCurrentTab(1);
		}
    	
	};
    
    /**
     * this stores the onclicklistener for the save button
     */
    private View.OnClickListener onSave = new View.OnClickListener() {
		
		public void onClick(View v) {
			
			current = new Restaurant();
			//intializes the fields of the new current restaurant
			current.setName(name.getText().toString());
			current.setAddress(address.getText().toString());
			current.setNotes(notes.getText().toString());
			
			//Determines the type of restaurant and adds the type to r
			switch(types.getCheckedRadioButtonId())
			{
				case R.id.sit_down:
					current.setType("sit_down");
					break;
				case R.id.take_out:
					current.setType("take_out");
					break;
				case R.id.delivery:
					current.setType("delivery");
					break;
			}
			//clears the name fields
			name.setText("");
			address.setText("");
			notes.setText("");
		
			//adds new component to the adapter then sets acText's adapter to the
			//newly modified adapter
			autoAdapter.add(current.getAddress());
			address.setAdapter(autoAdapter);
			
			//adds the restaurant the user just created to the adapter
			adapter.add(current);
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
