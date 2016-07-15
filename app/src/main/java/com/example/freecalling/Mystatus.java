package com.example.freecalling;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.freecalling.db.Db;

public class Mystatus extends Activity {

	TextView name,no,ip,id,status,currentip,wifiname;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mystatus);
		name=(TextView)findViewById(R.id._myname);
		no=(TextView)findViewById(R.id._myno);
		ip=(TextView)findViewById(R.id._myip);
		id=(TextView)findViewById(R.id._deviceid);
		status=(TextView)findViewById(R.id._status);
		currentip=(TextView)findViewById(R.id._currentip);
		wifiname=(TextView)findViewById(R.id._wifiname);
		
		Accuracycheak ac=new Accuracycheak();
		Db db=new Db(Mystatus.this);
		db.open();
		name.setText("I Am-- "+db.myname());
		no.setText("Recoganised by  "+db.myno());
		ip.setText("Device ip-- "+db.myip());
		id.setText("Device id-- "+ac.deviceId(Mystatus.this));
		status.setText("Application status-- "+db.cheakAppStatus(ac.deviceId(Mystatus.this)));
		currentip.setText("Running On-- "+ac.ipAddress());
		wifiname.setText("Connected With--"+getCurrendtSsid(this));
		
		db.close();
		
		
	}
	
	public String getCurrendtSsid(Context context){
		String status= "None";
		ConnectivityManager connmanager=(ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo=connmanager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkinfo.isConnected())
		{
			final WifiManager wifimanager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			final WifiInfo wifiinfo=wifimanager.getConnectionInfo();
			if(wifiinfo!=null && wifiinfo.getSSID()!=""){
				status=wifiinfo.getSSID();
			}
		}
		return status;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mystatus, menu);
		return true;
	}

}
