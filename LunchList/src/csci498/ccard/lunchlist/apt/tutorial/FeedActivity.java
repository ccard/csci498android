/*
 * Chris Card
 * 9/24/2012
 * gets the feeds for restaurants
 */
package csci498.ccard.lunchlist.apt.tutorial;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.mcsoxford.rss.RSSItem;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSFeed;

public class FeedActivity extends ListActivity {

	private InstanceState state = null;

	public static final String FEED_URL = "apt.tutorial.FEED_URL";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		state = (InstanceState)getLastNonConfigurationInstance();

		if(state == null)
		{
			state = new InstanceState();
			state.task = new FeedTask(this);
			state.task.execute(getIntent().getStringExtra(FEED_URL));
		}
		else
		{
			if(state.task != null)
			{
				state.task.attach(this);
			}
			if(state.feed != null)
			{
				setFeed(state.feed);
			}
		}
	}

	@Override
	public Object onRetainNonConfigurationInstance()
	{
		if(state.task != null)
		{
			state.task.detach();
		}
		return (state);
	}


	public void setFeed(RSSFeed feed)
	{
		state.feed = feed;
		setListAdapter(new FeedAdapter(feed));
	}

	/**
	 * this tells the user of the error that occurd will getting the feed
	 * @param t the exception that occured
	 */
	private void goBlooey(Throwable t)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("Exception!").setMessage(t.toString()).setPositiveButton("Ok", null).show();
	}

	/**
	 * This class performs the fetching of the rss feed
	 * @author Chris card
	 *
	 */
	private static class FeedTask extends AsyncTask<String, Void, RSSFeed>
	{
		private RSSReader reader = null;
		private Exception e = null;
		private FeedActivity activity = null;

		FeedTask(FeedActivity activity)
		{
			attach(activity);
		}

		void attach(FeedActivity activity)
		{
			this.activity = activity;
		}

		void detach()
		{
			this.activity = null;
		}

		@Override
		public RSSFeed doInBackground(String... urls)
		{
			RSSFeed result = null;

			try{
				result = reader.load(urls[0]);
			}catch(Exception e){
				this.e = e;
			}

			return (result);
		}

		@Override
		public void onPostExecute(RSSFeed feed)
		{
			//Checks to see if an error has occured in the retreval of the feed
			if(e == null)
			{
				activity.setFeed(feed);
			}
			else
			{
				Log.e("LunchList", "Exception parsing feed", e);
				activity.goBlooey(e);
			}
		}
	}

	/**
	 * This class is the listview adapter to display the feed
	 * @author Chris
	 *
	 */
	private class FeedAdapter extends BaseAdapter 
	{
		RSSFeed feed=null;

		FeedAdapter(RSSFeed feed)
		{
			super();
			this.feed=feed;
		}

		public int getCount() 
		{
			return(feed.getItems().size());
		}

		public Object getItem(int position) 
		{
			return(feed.getItems().get(position));
		}

		public long getItemId(int position) 
		{
			return(position);
		}

		public View getView(int position, View convertView, ViewGroup parent) 
		{
			View row=convertView;
			
			if (row==null) 
			{
				LayoutInflater inflater=getLayoutInflater();
				row=inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
			}
			RSSItem item=(RSSItem)getItem(position);
			((TextView)row).setText(item.getTitle());
			return(row);
		}
	}

	/**
	 * stores the instances and their state so when the activity is rotated
	 * or sum such event the state they are in remains
	 * @author Chris
	 *
	 */
	private static class InstanceState
	{
		RSSFeed feed = null;
		FeedTask task = null;
	}
}
