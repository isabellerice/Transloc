package edu.uchicago.mobile.visual_test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Find_Shuttle extends Activity implements OnClickListener {
	public static String shuttle;
	public static int calls;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.basic_info);
		
    	LocationManager locationManager= (LocationManager) 
				this.getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,(long) 0, (float) 0,  ll);
				locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 0, 0, ll);
				
		Button b = (Button) this.findViewById(R.id.back);
		b.setOnClickListener(this);		
		
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) { 
			//do anything in this block 
		}
		
		shuttle= extras.getString("shuttle");
		calls=0;
		
			
	}
	
	public void once_changed(Location loc)
	{
	//	if (calls!=1)
		//	return;
		
		Api_use api_class= new Api_use();
		String info[]= api_class.find_shuttle(loc.getLatitude(), loc.getLongitude(), shuttle);
		
		String nearest_stop=info[0];
		String time=info[1];
		
		TextView set=(TextView)this.findViewById(R.id.pickup_at);
		set.setText(nearest_stop);
		
		set=(TextView)this.findViewById(R.id.time_basic);
		set.setText(time);
		
		return;
		
	}
	
	android.location.LocationListener ll=new android.location.LocationListener() {
		public void onLocationChanged(Location arg0){
			calls++;
			once_changed(arg0);
			return;
		}
	
		public void onProviderDisabled(String arg0){}
		public void onProviderEnabled(String arg0){}
		public void onStatusChanged(String arg0, int arg1, Bundle arg2){}
	};
	
	public void onClick(View arg0) {
    	Intent j = new Intent(this, MainActivity.class);
    	this.startActivity(j);
	}

}
