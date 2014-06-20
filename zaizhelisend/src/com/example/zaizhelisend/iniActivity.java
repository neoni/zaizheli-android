package com.example.zaizhelisend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class iniActivity extends Activity{
	private final int SPLASH_DISPLAY_LENGHT = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ini);
		
		new Handler().postDelayed(new Runnable() {    
			public void run() {     
				Intent mainIntent = new Intent(iniActivity.this,IndexActivity.class);      
				iniActivity.this.startActivity(mainIntent);     
				iniActivity.this.finish();       
			}      
	    }, SPLASH_DISPLAY_LENGHT);
	}
}
