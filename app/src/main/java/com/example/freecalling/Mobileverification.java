package com.example.freecalling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Mobileverification extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mobileverification);
		Button submit=(Button)findViewById(R.id._submit);
		final EditText verificationcode=(EditText) findViewById(R.id._verificationcode);
		
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String code=verificationcode.getText().toString().trim();
				if(code.trim().equals("asdf")){
				//Toast.makeText(Mobileverification.this, "validation was sucessfull you may proceed", Toast.LENGTH_LONG).show();
				
				Intent i=new Intent(Mobileverification.this,GridViewActivity.class);
				startActivity(i);
				
			}
				else
				{
					//Toast.makeText(Mobileverification.this, "validation was unsucessfull try again", Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mobileverification, menu);
		return true;
	}

}
