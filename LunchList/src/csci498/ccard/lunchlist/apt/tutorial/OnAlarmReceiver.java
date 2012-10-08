/*
*Chris Card
*10/8/12
*/
package csci498.ccard.lunchlist.apt.tutorial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Intent i = new Intent(context, AlarmActivity.class);

		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(i);

	}

}
