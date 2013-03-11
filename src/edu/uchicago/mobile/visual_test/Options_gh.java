package edu.uchicago.mobile.visual_test;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Options_gh extends Activity implements OnClickListener {

	ArrayList<JSONObject> choose;
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//need to figure out which view based on switch
		this.setContentView(R.layout.options);
		int q;
		
		StrictMode.ThreadPolicy policy = new StrictMode.
				ThreadPolicy.Builder().permitAll().build();
				StrictMode.setThreadPolicy(policy);
		
		Button a = (Button) this.findViewById(R.id.stop_1);
		a.setOnClickListener(this);

		Button b = (Button) this.findViewById(R.id.stop_2);
		b.setOnClickListener(this);

		Button c = (Button) this.findViewById(R.id.stop_3);
		c.setOnClickListener(this);

		Bundle extras = this.getIntent().getExtras();
		if(extras != null) { 
			//do anything in this block 
		}
		
		String s = extras.getString("ender");
		s = s + " chicago";

		String address;
			try {
				choose= Api_use.travel_choices(s);
				int len = choose.size();
				address = ((JSONObject)choose.get(0)).getString("formatted_address");
				a.setText(address);
				System.out.println(len);
				if(len>1)
				{
					address = ((JSONObject)choose.get(1)).getString("formatted_address");
					b.setText(address);

				}
				else{
					b.setVisibility(View.GONE);
				}
				if(len>2)
				{
					address = ((JSONObject)choose.get(2)).getString("formatted_address");
					c.setText(address);

				}
				else{
					c.setVisibility(View.GONE);
				}
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return;

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.stop_1: 
			Intent i = new Intent(this, Get_Out.class);
			//add location and shuttle to intent
			Button a = (Button) this.findViewById(R.id.stop_1);
			i.putExtra("go_to", a.getText().toString());
			//start activity
			this.startActivity(i);
			break;
		case R.id.stop_2:
			Intent j = new Intent(this, Get_Out.class);
			//add location and shuttle to intent
			Button b = (Button) this.findViewById(R.id.stop_2);
			j.putExtra("go_to", b.getText().toString());
			//start activity
			this.startActivity(j);
			break;
		case R.id.stop_3: 
			Intent k = new Intent(this, Get_Out.class);
			//add location and shuttle to intent
			Button e = (Button) this.findViewById(R.id.stop_3);
			k.putExtra("go_to", e.getText().toString());
			//start activity
			this.startActivity(k);
			break;

		}
		return;
	}



}
