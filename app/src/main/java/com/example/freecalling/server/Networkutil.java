package com.example.freecalling.server;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Networkutil {

	public static int TYPE_WIFI=1;
	public static int TYPE_MOBILE=2;
	public static int TYPE_NOT_CONNECTED=0;
	
	public static int getconnectivitystatus(Context context)
	{
		ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activenetwork=cm.getActiveNetworkInfo();
		if(null != activenetwork){
			if (activenetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;
			
			if (activenetwork.getType() == ConnectivityManager.TYPE_MOBILE) 
				return TYPE_MOBILE;
			
		}
		return TYPE_NOT_CONNECTED;
	}
	public static String getConnectivityStatus(Context context){
		int conn=Networkutil.getconnectivitystatus(context);
		String status=null;
		if (conn==Networkutil.TYPE_WIFI) {
			status="wifi enabled";
		}
		else if (conn==Networkutil.TYPE_MOBILE) {
			status="mobile enabled";
		}
		else if (conn==Networkutil.TYPE_NOT_CONNECTED) {
			status="not connected to internet";
		}
		return status;
		
	}
	
}
