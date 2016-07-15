package com.example.freecalling.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.db.Db;

public class NetworkChangeReciver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service=new Intent(context,Server.class);
		context.startService(service);
		
		//Toast.makeText(context, "on recived called ", Toast.LENGTH_SHORT).show();
		Log.d("on recived call", "freecalling");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Accuracycheak ac=new Accuracycheak();
				Db db=new Db(context);
				db.open();
				String ip=db.myip();
				//Toast.makeText(context, ip+" "+ac.ipAddress(), Toast.LENGTH_SHORT).show();
				Log.d("ipAddress ", "freecalling");
				if(ac.ipAddress()!="errorfetchingip" && ac.ipAddress()!=ip){
					
					//Toast.makeText(context, "sending ip broadcast", Toast.LENGTH_SHORT).show();
					Log.d("sending ip", "freecalling");
					
					String currentip=ac.ipAddress();
					String deviceid=ac.deviceId(context);
					db.myipudate(currentip);
					
					Cursor c=db.mycontactlist();
					if (c.moveToFirst()) 
					{
						while(!c.isAfterLast()){
							String contactno=c.getString(0);
							final String ipaddress=c.getString(1);
							
							if(ipaddress!=currentip){
								System.out.println(currentip);
								if(contactno!=null){
									final String ippacket[]={"ipbroadcast",contactno,db.myno(),currentip,deviceid};
									//Toast.makeText(context, "broadcast sent", Toast.LENGTH_SHORT).show();
									Log.d("broadcast sent", "freecalling");
									
											Sendsmsserver ss=new Sendsmsserver(context);
											ss.msgsender(ipaddress, ippacket);
									
								}
								
							}
							
							c.moveToNext();
						}
					
					
				}
				
				
			}
			
		
				db.close();	
		}
		
	
	
	}