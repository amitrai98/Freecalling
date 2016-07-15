package com.example.freecalling.messaging;

import android.app.Activity;
import android.os.Bundle;

import com.example.freecalling.R;
import com.example.freecalling.db.Db;

public class Messageserver extends Activity {
	Db db;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messageserver);
		db = new Db(this);
		//new GetDataDromDB(this,"").execute();
	}


	
	
	/*
	class GetDataDromDB extends AsyncTask<String, Void, Object> {
		Context ctx;
		String data;
		 public GetDataDromDB(	Context ctx,String data){
			this. ctx=ctx
			this.data
		 }
		

		
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			redd.don
		}

		@Override
		protected Object doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
			ss
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messageserver, menu);
		return true;
	}*/

}
