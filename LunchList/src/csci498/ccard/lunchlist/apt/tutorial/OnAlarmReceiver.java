/*
*Chris Card
*10/8/12
*/
package csci498.ccard.lunchlist.apt.tutorial;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class OnAlarmReceiver extends BroadcastReceiver {

	private static final int NOTIFY_ME_ID = 1337;

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		boolean useNotification = prefs.getBoolean("use_notification", true);

		if (useNotification) 
		{
			NotificationManager mgr = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

			Notification note = new Notification(R.drawable.stat_notify_chat, "It's time for lunch!", System.currentTimeMillis());
			PendingIntent i = PendingIntent.getActivity(context,0, new Intent(context, AlarmActivity.class),0);

			note.setLatestEventInfo(context, "LunchList", "It's time for lunch! Aren't you hungry?", i);

			note.flags |= Notification.FLAG_AUTO_CANCEL;

			mgr.notify(NOTIFY_ME_ID, note);	
		}
		else
		{
			Intent i = new Intent(context, AlarmActivity.class);

			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			context.startActivity(i);
		}

	}

}
