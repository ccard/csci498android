/*
* Chris Card
*10/19/12
*/
package csci498.ccard.lunchlist.apt.tutorial;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class AppWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context ctxt, AppWidgetManager mgr, int[] appWidgetIds)
	{
		ctxt.startService(new Intent(ctxt, WidgetService.class));
	}

}
