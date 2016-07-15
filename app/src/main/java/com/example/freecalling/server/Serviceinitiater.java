package com.example.freecalling.server;

import com.example.freecalling.Staticactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Serviceinitiater extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("cheak service is running or not");
		if(Staticactivity.isMyServiceRunning(context,"com.example.Freecalling.Server")==false){
			System.out.println("service is starting");
			Intent service=new Intent(context,Server.class);
			context.startService(service);
		}
		
	}

}
