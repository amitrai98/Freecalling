package com.example.freecalling;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

public class Staticactivity extends Activity {
	static boolean state=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_staticactivity);
	}
	
	public static String activitystatuscheak(Context ctx,String activityname)
	{
		String status="not running";
		ActivityManager acvmanager=(ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo>services=acvmanager.getRunningTasks(Integer.MAX_VALUE);
		for(int i=0;i<services.size();i++){
			if(services.get(i).topActivity.toString().equalsIgnoreCase(activityname))
			{
				status="running";
			}
		}
		return status;
		
	}
	
	public static boolean isMyServiceRunning(Context ctx,String servicename) {
	    ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	    	System.out.println(service.service.getClassName());
	 if (servicename.equals(service.service.getClassName())) {
	     return true;
	 }
	    }
	    return false;
	}
	
	public static boolean isServerRunning() {
		final Accuracycheak ac=new Accuracycheak();
		
		new Thread(new Runnable() {
			
			@Override
			public synchronized void run() {
				try {
					Socket sc = new Socket(ac.ipAddress(), 4444);
					ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream());
					
					String messagepacket[]={"pingrequest",ac.ipAddress(),};
		            out.writeObject(messagepacket);
					out.close();
					sc.close();
					state=true;
				} catch (Exception e) {
					e.printStackTrace();
					state=false;
				}
				
			}
		}).start();
		return state;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.staticactivity, menu);
		return true;
	}

}
