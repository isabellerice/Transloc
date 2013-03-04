package edu.uchicago.mobile.visual_test;

import java.io.IOException;

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
	public static int calls;
	public boolean set_alert=false;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//May have to change view based on switch
		this.setContentView(R.layout.full_directions);
		
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

		end = extras.getString("go_to");
		calls=0;

 
		
	}
	
	public void once_changed(Location loc) throws IOException, JSONException
	{
	//	if(calls!=1)
	//		return;
		
		
		Api_use api_class = new Api_use();
		
		String info[]=api_class.travel_info(loc.getLatitude(), loc.getLongitude(), end);
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
		return;
		
	}

	android.location.LocationListener ll=new android.location.LocationListener() {
		public void onLocationChanged(Location arg0){
			calls++;
			/*Api_use api_class = new Api_use();
			 
		// Build notification
		// Actions are just fake
		Notification noti = new NotificationCompat.Builder(this)
		        .setContentTitle("You're within 50 meters")
		        .setContentText("You're Close!").setSmallIcon(android.R.drawable.ic_menu_compass)
		        .build();
		    
		  
		NotificationManager notificationManager = 
		  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// Hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti); 
			 
			if(set_alert)
			{
				if(api_class.get_distance(api_class.end_loc(end), arg0)<50)
				{
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				Get_Out.this);
 
			// set title
			alertDialogBuilder.setTitle("Your Title");
 
			// set dialog message
			alertDialogBuilder
				.setMessage("You're within 50 meters of your stop")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						dialog.cancel();
					}
				  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
					return;
				}
			}*/
				
			try {
				once_changed(arg0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
    		set_alert=true;
    		break;
		}
		
	}

	
	
}
