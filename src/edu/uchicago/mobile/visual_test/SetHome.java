package edu.uchicago.mobile.visual_test;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View.OnClickListener;

public class SetHome extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.set_home);
		
		Button b = (Button) this.findViewById(R.id.Submit_Home);
		b.setOnClickListener(this);
		
		Button c = (Button) this.findViewById(R.id.back);
		c.setOnClickListener(this);
		
	}
	
    public void onClick(View v) {
    		
    	EditText add = (EditText) this.findViewById(R.id.Home_Address);
    	
    	SharedPreferences prefs = getSharedPreferences("default", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString("home_address", add.toString());
        prefsEditor.commit();
        
        Intent i = new Intent(this, MainActivity.class);
        this.startActivity(i);
    	
    }

}
