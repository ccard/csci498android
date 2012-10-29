/**
 * Chris Card
 * 8/28/12
 * This class contains the main activity for this project
 */
//Test change for second machine
package csci498.ccard.lunchlist;

import csci498.ccard.lunchlist.apt.tutorial.DetailForm;
import csci498.ccard.lunchlist.apt.tutorial.LunchFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class LunchList extends FragmentActivity implements LunchFragment.OnRestaurantListener 
{
	public final static String ID_EXTRA="apt.tutorial._ID";
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LunchFragment lunch = (LunchFragment)getSupportFragmentManager().findFragmentById(R.id.lunch);
		lunch.setOnRestaurantListener(this);
	}

	public void onRestaurantSelected(long id) 
	{
		if (findViewById(R.id.details) == null) 
		{
			Intent i = new Intent(this, DetailForm.class);
			i.putExtra(ID_EXTRA, String.valueOf(id));
			startActivity(i);
		}
		else
		{
			
		}
		
	}
}
