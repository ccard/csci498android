/*
*Chris Card
*9/17/12
*
*/
package csci498.ccard.lunchlist.apt.tutorial;

import csci498.ccard.lunchlist.R;
import csci498.ccard.lunchlist.Restaurant;
import android.app.Activity;
import android.os.Bundle;
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
	private Restaurant current = null;
	
	private RestaurantHelper helper;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_form);

		 helper = new RestaurantHelper(this);
        
        //initialize the view items
        initViewItems();
        
        //stores the save button from the main.xml file
        Button save = (Button)findViewById(R.id.save);
        
        //adds and on click listener
        save.setOnClickListener(onSave);
        
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
		
			
		}
	};

}
