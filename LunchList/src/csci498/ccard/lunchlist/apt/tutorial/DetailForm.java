/*
*Chris Card
*9/17/12
*
*/
package csci498.ccard.lunchlist.apt.tutorial;

import csci498.ccard.lunchlist.LunchList;
import csci498.ccard.lunchlist.R;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class DetailForm extends Activity 
{
	 
	//these store the access to the fields the user uses to input info
	private EditText name = null;
	private EditText address = null;
	private RadioGroup types = null;
	private EditText notes = null;
    private EditText feed = null;

	private RestaurantHelper helper;

	private String restaurantId = null;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_form);

		 helper = new RestaurantHelper(this);
        
        //initialize the view items
        initViewItems();

        restaurantId = getIntent().getStringExtra(LunchList.ID_EXTRA);
        
        //stores the save button from the main.xml file
        Button save = (Button)findViewById(R.id.save);
        
        //adds and on click listener
        save.setOnClickListener(onSave);
        
        if(restaurantId != null)
        {
        	load();
        }
    }
    
    /**
     * this initializes the view items edit text, radiobuttons, and autocomplete
     */
    private void initViewItems()
    {
    	 //initializing the global views to the view from the xml files
        name = (EditText)findViewById(R.id.name);
		address = (EditText)findViewById(R.id.addr);
		types = (RadioGroup)findViewById(R.id.types);
        notes = (EditText)findViewById(R.id.notes);
        feed = (EditText)findViewById(R.id.feed);
    }


    public void load()
    {
    	Cursor c = helper.getById(restaurantId);

    	c.moveToFirst();
    	name.setText(helper.getName(c));
    	address.setText(helper.getAddress(c));
    	notes.setText(helper.getNotes(c));
        feed.setText(helper.getFeed(c));

    	if(helper.getType(c).equals("sit_down"))
    	{
    		types.check(R.id.sit_down);
    	}
    	else if(helper.getType(c).equals("take_out"))
    	{
    		types.check(R.id.take_out);
    	}
    	else
    	{
    		types.check(R.id.delivery);
    	}

    	c.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        new MenuInflater(this).inflate(R.menu.details_option, menu);

        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public void onDestroy()
    {
    	super.onDestroy();
    	helper.close();
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
    public void onRestoreInstanceState(Bundle state)
    {
        super.onRestoreInstanceState(state);

        name.setText(state.getString("name"));
        address.setText(state.getString("address"));
        notes.setText(state.getString("notes"));
        types.check(state.getInt("type"));
    }

	/**
     * this stores the onclicklistener for the save button
     */
    private View.OnClickListener onSave = new View.OnClickListener() {
		
		public void onClick(View v) {
			
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
				helper.insert(name.getText().toString(), address.getText().toString(), type, notes.getText().toString(), feed.getText().toString());
			}
			else
			{
				helper.update(restaurantId, name.getText().toString(), address.getText().toString(), type, notes.getText().toString(), feed.getText().toString());
			}

			finish();
			
		}
	};

}
