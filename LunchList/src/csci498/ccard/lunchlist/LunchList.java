package csci498.ccard.lunchlist;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableRow;
import java.util.*;

public class LunchList extends Activity {

	List<Restaurant> model = new ArrayList<Restaurant>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button save = (Button)findViewById(R.id.save);
        
        save.setOnClickListener(onSave);
        
//        //creating new radio buttons
//        RadioGroup g = new RadioGroup(this);
//        RadioButton take = new RadioButton(this);
//        RadioButton site = new RadioButton(this);
//        RadioButton deliv = new RadioButton(this);
//        //names new radio buttons
//        take.setText("Take-Out2");
//        site.setText("Sit-down2");
//        deliv.setText("Delivery 2");
//        //adds radiobuttons to group and to the table row
//        g.addView(take);
//        g.addView(site);
//        g.addView(deliv);
//        TableRow t = (TableRow)findViewById(R.id.tableRow1);
//        t.addView(g);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
		
		public void onClick(View v) {
			Restaurant r = new Restaurant();
			EditText name=(EditText)findViewById(R.id.name);
			EditText address=(EditText)findViewById(R.id.addr);
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			
			//creates functionality for radiogroup added via xml
			RadioGroup types = (RadioGroup)findViewById(R.id.types);
			
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
		}
	};
    
}
