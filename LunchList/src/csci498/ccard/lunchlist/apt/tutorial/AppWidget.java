/*
* Chris Card
*10/19/12
*/
package csci498.ccard.lunchlist.apt.tutorial;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class AppWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context ctxt, AppWidgetManager mgr, int[] appWidgetIds)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
		{
			onHCUpdate(ctxt, mgr, appWidgetIds);	
		}
		else
		{
			ctxt.startService(new Intent(ctxt, WidgetService.class));
		}
	}

	public void onHCUpdate(Context ctxt, AppWidgetManager appWidgetManager, int[] appWidgetIds)
	{
		for (int i = 0; i < appWidgetIds.length; i++) 
		{
			Intent svcIntent = new Intent(ctxt, ListWidgetService.class);

			svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
			svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
			Intent clickIntent = new Intent(ctxt, DetailForm.class);
			PendingIntent clickPI = PendingIntent.getActivity(ctxt, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

			widget.setPendingIntentTemplate(R.id.restaurants, clickPI);
			appWidgetManager.updateAppWidget(appWidgetIds[i], widget);	
		}
		super.onUpdate(ctxt, appWidgetManager, appWidgetIds);
	}

}