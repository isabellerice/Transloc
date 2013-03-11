package edu.uchicago.mobile.visual_test;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;


public class Get_Home extends Activity implements OnClickListener{

	int calls=0;
	String end_time;
	double lat, lng;
	public HashMap stop;
	
	@SuppressLint("NewApi") public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//need to figure out which view based on switch
		this.setContentView(R.layout.go_home);
		
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
		
    	LocationManager locationManager= (LocationManager) 
				this.getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,(long) 0, (float) 0,  ll);
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, ll, null);

    	
		Button a = (Button) this.findViewById(R.id.change_address);
		a.setOnClickListener(this);
		
		Button b = (Button) this.findViewById(R.id.back);
		b.setOnClickListener(this);
		
		Button c = (Button) this.findViewById(R.id.directions);
		c.setOnClickListener(this);
		
		Button d = (Button) this.findViewById(R.id.Alert);
		d.setOnClickListener(this);
		return;
		
	}
	
	public void once_changed(Location loc) throws IOException, JSONException
	{

		
		SharedPreferences myPrefs = getSharedPreferences("default", Context.MODE_PRIVATE);
		String home = myPrefs.getString("home_address", "none");

		Trip t = Api_use.travel_info_home_hash(loc.getLatitude(), loc.getLongitude(), home);
		lat=loc.getLatitude();
		lng=loc.getLongitude();
		stop=t.I_STOP;
		
		if (Api_use.need_switch(loc.getLatitude(),loc.getLongitude(), stop))
		{
			long time_now = System.currentTimeMillis();
			Time tn = new Time(time_now);
			
			this.setContentView(R.layout.full_directions_switch);
			//make two calls, set variables
			String info_one[]=Api_use.travel_info_home(loc.getLatitude(), loc.getLongitude(), "1100 East 57th Street Chicago");
			
			
			String info_two[]=Api_use.travel_info_home(41.791537, -87.599723, home);
			
			Button s = (Button)findViewById(R.id.Get_On);
			s.setText("Get on the " + info_one[1] + " at:");
			
			TextView et = (TextView)findViewById(R.id.pickup_at);
			et.setText(info_one[0]);
			
			et= (TextView)findViewById(R.id.switch_location);
			et.setText(info_one[2]);
			
			s = (Button)findViewById(R.id.Get_Off);
			s.setText("Get on "+info_two[1]);
			
			et = (TextView)findViewById(R.id.dropoff_at);
			et.setText(info_two[2]);
			
			et= (TextView)findViewById(R.id.arrival_time);
			et.setText(String.valueOf((Api_use.time_diff_sec(tn, Time.valueOf(info_one[3]))/60)) + " minutes");

			end_time=info_two[4];
			
		}
		else
		{
			long time_now = System.currentTimeMillis();
			Time tn = new Time(time_now);
			
			String info[]=Api_use.travel_info_home(loc.getLatitude(), loc.getLongitude(), home);
			//returns start name, route name, end name
			String start_name=info[0];
			String route_name=info[1];
			String end_name=info[2];
			
			//RESET
			String time=String.valueOf((Api_use.time_diff_sec(tn, Time.valueOf(info[3]))/60)) + " minutes";

			TextView ct = (TextView)findViewById(R.id.pickup_at);
			ct.setText(start_name);

			ct = (TextView)findViewById(R.id.pickup_at);
			ct.setText("Get on the "+route_name+ " at " +start_name);
			ct = (TextView)findViewById(R.id.time_home);
			ct.setText(time);
			
			//RESET
			end_time=info[4];  //Integer.parseInt(info[3]);
		}
		return;
	}
	
	android.location.LocationListener ll=new android.location.LocationListener() {
		public void onLocationChanged(Location arg0){
			calls++;
			if (calls==1){
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
			return;
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
        	case R.id.Alert:
        		//get time to arrival
        		long time_now = System.currentTimeMillis();
    			Time tn = new Time(time_now);
    			long till = 1000*(Api_use.time_diff_sec(tn, Time.valueOf(end_time)));
    			
        		
        		
        		Button d = (Button) this.findViewById(R.id.Alert);
        		d.setText("We'll alert you when there's a minute left");
        		
		    	 final Notification noti = new NotificationCompat.Builder(this).setContentTitle("You're Close!")
			        .setContentText("You're about a minute away").setSmallIcon(android.R.drawable.ic_menu_compass)
			        .build();
			    
			  
					final NotificationManager notificationManager = 
			  		(NotificationManager) getSystemService(NOTIFICATION_SERVICE);

					// Hide the notification after its selected
					noti.flags |= Notification.FLAG_AUTO_CANCEL;

					 
				 

					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					Get_Home.this);
	 
						// set title
						alertDialogBuilder.setTitle("Alert");
	 
						// set dialog message
						alertDialogBuilder
							.setMessage("You're close!")
							.setCancelable(false)
							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,int id) {
									// if this button is clicked, close
									// current activity
									dialog.cancel();
									Get_Home.this.finish();
								}
					  	});
	 
						// create alert dialog
						final AlertDialog alertDialog = alertDialogBuilder.create();
	 
						// show it
						
        		 new CountDownTimer(till, 1000) {

        		     public void onTick(long millisUntilFinished) {
        		     }

        		     public void onFinish() {
        		    	 alertDialog.show();
        		    	 notificationManager.notify(0, noti);
        		     }
        		  }.start();
        		  break;
        	case R.id.directions:
        		Intent k = new Intent(this, Directions.class);
        		//add lat, long, and route
        		k.putExtra("lat", lat);
        		k.putExtra("lng", lng);
        		try {
    				ArrayList<String> to = Walking.walkTo(lat, lng, stop);
    				k.putStringArrayListExtra("to", to);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		this.startActivity(k);
        		
        		break;
		
		}
		return;
	}
	
}
