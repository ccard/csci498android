/*
*Chris Card
*11/7/12
*/
package csci498.ccard.lunchlist.apt.tutorial;

import csci498.ccard.lunchlist.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class HelpPage extends Activity {

	private WebView browser;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.help);

		browser = (WebView)findViewById(R.id.webkit);
		browser.loadUrl("file:///android_asset/help.html");
	}
}
