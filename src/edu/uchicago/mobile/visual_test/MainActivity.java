package edu.uchicago.mobile.visual_test;


import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		Button a = (Button) this.findViewById(R.id.Get_Home);
		a.setOnClickListener(this);
		
		Button b = (Button) this.findViewById(R.id.Get_Out);
		b.setOnClickListener(this);
		
		Button c = (Button) this.findViewById(R.id.Nor);
		c.setOnClickListener(this);
		
		Button d = (Button) this.findViewById(R.id.sou);
		d.setOnClickListener(this);
		
		Button e = (Button) this.findViewById(R.id.ea);
		e.setOnClickListener(this);
		
		Button f = (Button) this.findViewById(R.id.cent);
		f.setOnClickListener(this);
    }
 
	android.location.LocationListener ll=new android.location.LocationListener() {
		public void onLocationChanged(Location arg0){}
	
		public void onProviderDisabled(String arg0){}
		public void onProviderEnabled(String arg0){}
		public void onStatusChanged(String arg0, int arg1, Bundle arg2){}
	};
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void gethomeclick() {
    	SharedPreferences prefs = getSharedPreferences("default", Context.MODE_PRIVATE);
    	String home = prefs.getString("home_address", "none");

        //if we have an address saved
    	if (home != null && home!= "none") {
        	Intent i = new Intent(this, Get_Home.class);

    		//start activity
        	this.startActivity(i);
        }
    	
    	else{ 
    		//if we don't have an addressed saved, go to set the address
    		Intent i = new Intent(this, SetHome.class);
    		this.startActivity(i);
    	}
        
    }
    
    public void getoutclick() {
    	//set up a new intent
    	Intent i = new Intent(this, GetAddress.class);
		
		//start activity
    	this.startActivity(i);
    }
    
    public void findme(String shuttle) {
    	//set up a new intent
    	Intent i = new Intent(this, Find_Shuttle.class);
    	
		
		//add location and shuttle to intent
		i.putExtra("shuttle", shuttle);

		//start activity
    	this.startActivity(i);
    }


	@Override
	public void onClick(View v) {
	      switch (v.getId()) {
	         case R.id.Get_Home: 
	        	 gethomeclick();
	          break;
	         case R.id.Get_Out:
	        	 getoutclick();
	          break;
	         case R.id.Nor: 
	        	 findme("North");
		        break;
		     case R.id.sou:
		    	 findme("South");
		       break;
	         case R.id.ea: 
	        	 findme("East");
		       break;
		     case R.id.cent:
		    	 findme("Central");
		       break;
	      }
		
	}
    
}
