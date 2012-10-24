/*
*Chris Card
*10/24/12
*/
package csci498.ccard.lunchlist.apt.tutorial;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class ListWidgetService extends RemoteViewsService {

	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) 
	{
		return(new ListViewsFactory(this.getApplicationContext, this));
	}

}
