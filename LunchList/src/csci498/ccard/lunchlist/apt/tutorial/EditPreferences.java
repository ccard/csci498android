/*
*Chris Card
*9/19/12
*/
package csci498.ccard.lunchlist.apt.tutorial;

import csci498.ccard.lunchlist.R;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.app.Activity;


public class EditPreferences extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
	}

}
