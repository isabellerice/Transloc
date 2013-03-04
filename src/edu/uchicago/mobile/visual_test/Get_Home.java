package edu.uchicago.mobile.visual_test;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Get_Home extends Activity implements OnClickListener{

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//need to figure out which view based on switch
		this.setContentView(R.layout.go_home);
		
    	LocationManager locationManager= (LocationManager) 
				this.getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,(long) 0, (float) 0,  ll);
				locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 0, 0, ll);

    	
		Button a = (Button) this.findViewById(R.id.change_address);
		a.setOnClickListener(this);
		
		Button b = (Button) this.findViewById(R.id.back);
		b.setOnClickListener(this);
		
	}
	
	public void once_changed(Location loc) throws IOException, JSONException
	{

		
		SharedPreferences myPrefs = getSharedPreferences("default", Context.MODE_PRIVATE);
		String home = myPrefs.getString("home_address", "none");
		
		
		Api_use api_class= new Api_use();
		
		
		String info[]=api_class.travel_info(loc.getLatitude(), loc.getLongitude(), home);
		
		//returns start name, route name, end name
		String start_name=info[0];
		String route_name=info[1];
		String end_name=info[2];
		String time=info[3];
		
		TextView ct = (TextView)findViewById(R.id.pickup_at);
		ct.setText(start_name);
		
		ct = (TextView)findViewById(R.id.pickup_at);
		ct.setText("Get on the "+route_name+ " at " +start_name);
		
		
		
		ct = (TextView)findViewById(R.id.time_home);

		ct.setText(time);
		return;
	}
	
	android.location.LocationListener ll=new android.location.LocationListener() {
		public void onLocationChanged(Location arg0){
			try {
				once_changed(arg0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//return;
 catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		public void onProviderDisabled(String arg0){}
		public void onProviderEnabled(String arg0){}
		public void onStatusChanged(String arg0, int arg1, Bundle arg2){}
	};


	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
        	case R.id.change_address: 
        		Intent i = new Intent(this, SetHome.class);
        		this.startActivity(i);
        		break;
        	case R.id.back:
        		Intent j = new Intent(this, MainActivity.class);
        		this.startActivity(j);
        		break;
		
		}
	}
	
}
