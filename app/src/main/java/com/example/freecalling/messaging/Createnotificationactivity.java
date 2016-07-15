package com.example.freecalling.messaging;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.freecalling.R;

public class Createnotificationactivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createnotificationactivity);
	}
public void createnotification(View view){
	String ns=Context.NOTIFICATION_SERVICE;
	NotificationManager notimanager=(NotificationManager)getSystemService(ns);
	int icon=R.drawable.sms;
	CharSequence tickertext="new message";
	
	Intent notificationintent=new Intent(this,Smslist.class);
	PendingIntent contentintent=PendingIntent.getActivity(this, 0, notificationintent, 0);
	Notification notification=new Notification(icon,"hello",10);
	notification.setLatestEventInfo(getBaseContext(), "hello", "welcome", contentintent);
	notimanager.notify(1,notification);
}
	

}
