package com.example.freecalling.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.freecalling.R;
import com.example.freecalling.activities.Registration;

public class Splash extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main) ;
		moveToNextActivity(3);
	}
	
	
	public void moveToNextActivity(int time_in_sec){
//		startActivity(new Intent(Splash.this,Registration.class));
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				finish();
				startActivity(new Intent(Splash.this,Registration.class));
			}
		}, time_in_sec*1000);
	}
}
