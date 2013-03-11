package edu.uchicago.mobile.visual_test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Get_Out extends Activity implements OnClickListener {
	
	public static String end;
	public int calls=0;
	int end_time=0;
	double lat, lng;
	HashMap stop;
	
	@SuppressLint("NewApi") public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//May have to change view based on switch
		this.setContentView(R.layout.full_directions);
		
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
		
		
    	LocationManager locationManager= (LocationManager) 
				this.getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,(long) 0, (float) 0,  ll);
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
		locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, ll, null);

		Button b = (Button) this.findViewById(R.id.back);
		b.setOnClickListener(this);
		
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) { 
			//do anything in this block 
		}
		
		
		
		end= extras.get("go_to").toString();
		
		Button c = (Button) this.findViewById(R.id.directions);
		c.setOnClickListener(this);
		
		Button d = (Button) this.findViewById(R.id.Alert);
		d.setOnClickListener(this);

		return;
		
 
		
	}
	
	public void once_changed(Location loc) throws IOException, JSONException
	{
	//	if(calls!=1)
	//		return;
		
		Api_use api_class = new Api_use();
		end = this.getIntent().getExtras().get("go_to").toString();
			
		
		Trip t = Api_use.travel_info_home_hash(loc.getLatitude(), loc.getLongitude(), end);
		lat=loc.getLatitude();
		lng=loc.getLongitude();
		stop=t.I_STOP;
		

		
		if (Api_use.need_switch(loc.getLatitude(),loc.getLongitude(), stop))
		{
			this.setContentView(R.layout.full_directions_switch);
			//make two calls, set variables
			String info_one[]=Api_use.travel_info_home(loc.getLatitude(), loc.getLongitude(), "1100 East 57th Street Chicago");
			
			String info_two[]=Api_use.travel_info_home(41.791537, -87.599723, end);
			
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

			end_time=30000;

		}
		else{

			String info[]=api_class.travel_info_home(loc.getLatitude(), loc.getLongitude(), end);

			if(info[0]=="Walking will be faster!")
			{
				Intent j = new Intent(this, Walk_it.class);
				this.startActivity(j);
			}
			else if (info[0]=="No shuttle servce.")
			{
				Intent j = new Intent(this, Not_Running.class);
				this.startActivity(j);
			}
			else{

				//returns start name, route name, end name
				String start_name=info[0];
				String route_name=info[1];
				String end_name=info[2];
				String time = info[3];

				Button change = (Button)findViewById(R.id.Get_On);
				String set = "Get on the " + route_name + " at";
				change.setText(set);

				TextView ct = (TextView)findViewById(R.id.pickup_at);
				ct.setText(start_name);

				ct = (TextView)findViewById(R.id.dropoff_at);
				ct.setText(end_name);


				ct = (TextView)findViewById(R.id.arrival_time);
				ct.setText(time);
				end_time=30000;
			}
		}
		return;

	}

	android.location.LocationListener ll=new android.location.LocationListener() {
		public void onLocationChanged(Location arg0){
			calls++;

			if(calls<2){	
			try {
				
				once_changed(arg0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
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
	
	public void onClick(View arg0) {
		switch (arg0.getId()) {
    	case R.id.back: 
        	Intent j = new Intent(this, MainActivity.class);
        	this.startActivity(j);
        	break;
    	case R.id.Alert:
    		//get time to arrival
    		
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
				Get_Out.this);
 
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
								Get_Out.this.finish();
							}
				  	});
 
					// create alert dialog
					final AlertDialog alertDialog = alertDialogBuilder.create();
 
					// show it
					
    		 new CountDownTimer(end_time, 1000) {

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
