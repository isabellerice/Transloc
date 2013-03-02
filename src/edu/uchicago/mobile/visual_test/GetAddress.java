package edu.uchicago.mobile.visual_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class GetAddress extends Activity implements OnClickListener {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.get_address);

		Button a = (Button) this.findViewById(R.id.Submit);
		a.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		
		Intent i = new Intent(this, Get_Out.class);
		
		
		EditText add = (EditText) this.findViewById(R.id.Address);
		i.putExtra("go_to", add.toString());
		
		this.startActivity(i);
	}
	
}
