package com.example.freecalling.voicecall;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.example.freecalling.R;

public class Blocklist extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blocklist);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.blocklist, menu);
		return true;
	}

}
