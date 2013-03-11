package edu.uchicago.mobile.visual_test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Directions extends Activity implements OnClickListener {


	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//need to figure out which view based on switch
		this.setContentView(R.layout.directions);
		
		Bundle extras = this.getIntent().getExtras();
		if(extras != null) { 
			//do anything in this block 
		}
		
		double lat=(Double) extras.get("lat");
		double lng=(Double) extras.get("lng");
		ArrayList<String> to = (ArrayList<String>) extras.get("to");

		TextView tv = (TextView)findViewById(R.id.directions);
		System.out.println(to);
		String text="";
		int s = to.size();
		System.out.println(to);
		for(int j = 0; j<s; j++){
			text=text+Html.fromHtml(to.get(j))+ '\n';
		}

		tv.setText(text);


		return;

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}
