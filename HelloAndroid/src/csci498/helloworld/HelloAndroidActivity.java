package csci498.helloworld;

import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.*;

public class HelloAndroidActivity extends Activity implements View.OnClickListener {
	Button btn;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        btn = new Button(this);
        btn.setOnClickListener(this);
        updateTime();
        
        setContentView(btn);
    }
    
    public void onClick(View view)
    {
    	updateTime();
    }
    
    public void updateTime()
    {
    	btn.setText(new Date().toString());
    }
    
}
