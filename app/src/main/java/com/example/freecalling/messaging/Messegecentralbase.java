package com.example.freecalling.messaging;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.Staticactivity;
import com.example.freecalling.db.Db;
import com.example.freecalling.server.Sendsmsserver;

public class Messegecentralbase {
	Context ctx;
	
	public Messegecentralbase(Context ctx){
		this.ctx=ctx;
	}
	
	public void messagesender(String reciverno, String message) {
		Db db=new Db(ctx);
		Accuracycheak ac=new Accuracycheak();
		db.open();
		String reciverip = db.ipaddress(reciverno);
		if (reciverip != null) {
			Accuracycheak acc = new Accuracycheak();
			Sendsmsserver ss = new Sendsmsserver(ctx);
			String smspacket[] = { "sms", reciverno, db.myno(), message,
					acc.ipAddress(),ac.deviceId(ctx) };
			ss.msgsender(reciverip, smspacket);
		}
		db.close();

	}

	public void smsdbupdate(String reciverno, String message) {
		Accuracycheak ac = new Accuracycheak();
		Db db = new Db(ctx);
		db.open();
		String reciverip = db.ipaddress(reciverno);
		if (reciverip != null) {
			String status=db.sendSms(reciverno, db.ipaddress(reciverno),
					ac.deviceId(ctx), message, "smssent",
					db.myno(), db.myip());
			if (status=="sent"){
				
				if(Staticactivity.activitystatuscheak(ctx,"ComponentInfo{com.example.Freecalling/com.example.Freecalling.Sendsms}").equals("running")){
					//System.out.println(Staticactivity.activitystatuscheak(ctx,"ComponentInfo{com.example.Freecalling/com.example.Freecalling.Sendsms}"));
					ctx.sendBroadcast(new Intent("updatelist"));
					ctx.sendBroadcast(new Intent("updatesmslist"));
					
				}
				
			}
		} else {
			toaster("this contact no do not have any ip address please provide an ip");
		}
		db.close();
	}

	public void toaster(String messgae) {
		Toast.makeText(ctx, messgae, Toast.LENGTH_SHORT).show();
	}

}
