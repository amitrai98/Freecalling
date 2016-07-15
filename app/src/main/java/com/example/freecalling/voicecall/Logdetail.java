package com.example.freecalling.voicecall;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;
import com.example.freecalling.db.Db;
import com.example.freecalling.messaging.Sendsms;

public class Logdetail extends Activity {
	
	Button call,sendsms;
	TextView contactname;
	String recivername,reciverip,myno,reciverno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		ArrayList<logdetailbinadapter>list=new ArrayList<logdetailbinadapter>();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logdetail);
		Bundle bdl=getIntent().getExtras();
		final String contactno=bdl.getString("contactno");
		contactname=(TextView) findViewById(R.id._contactname);
		contactname.setText(contactno);
		call=(Button) findViewById(R.id._call);
		sendsms=(Button) findViewById(R.id._sendmessage);
		reciverno=contactno;
		call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Db link=new Db(getApplicationContext());
				try {
					
					link.open();
					recivername=link.getName(contactno);
					reciverip=link.ipaddress(contactno);
					myno=link.myno();
				} catch (Exception e) {
					e.printStackTrace();
					
				}finally{link.close();}
				
			
					
					Accuracycheak ac=new Accuracycheak();
					String connectionState=ac.dataConncetionCheak(getApplicationContext());
					if(connectionState=="connected"){
						Log.e("view activity", ""+recivername+" "+reciverno+" "+reciverip+" "+myno);
						if (!TextUtils.isEmpty(recivername) && !TextUtils.isEmpty(reciverno) && !TextUtils.isEmpty(reciverip) && !TextUtils.isEmpty(myno)) {
							{
						
						finish();
						Intent i=new Intent(Logdetail.this,Call.class);
						i.putExtra("recivername", recivername);
						i.putExtra("reciverno", reciverno);
						i.putExtra("reciverip", reciverip);
						i.putExtra("myno", myno);
						
						startActivity(i);	
						}
					}
					
					else{
						Toast.makeText(Logdetail.this, "You are not connected to a Wifi network please connect and then try..", Toast.LENGTH_SHORT).show();
					}
					}
					else{
						Toast.makeText(Logdetail.this, "please first cheack the contact no ipaddress and contact no of the reciver..", Toast.LENGTH_SHORT).show();
					}
					
			
			}
		});
		
		sendsms.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String cno=contactno;
				//System.out.println(myno);
				if(cno!=null && cno!=myno){
			
				System.out.println(cno);
				if(cno!=null && cno!=" " && cno!=myno){
					finish();
					Intent i=new Intent(Logdetail.this,Sendsms.class);
					i.putExtra("contactno",cno);
					i.putExtra("message", "");
					startActivity(i);
				}
				else{
					Toast.makeText(Logdetail.this, "This is not a valid no.", Toast.LENGTH_SHORT).show();
				}
				}else{
					Toast.makeText(Logdetail.this, "This is not a valid no.", Toast.LENGTH_SHORT).show();
				}
			}
});
		if(!TextUtils.isEmpty(contactno)){
			ImageView contactimage=(ImageView)findViewById(R.id._contactimage);
			TextView contactname=(TextView)findViewById(R.id._contactname);
			ListView lv=(ListView) findViewById(R.id._logdetaillist);
			
			Db link=new Db(Logdetail.this);
			link.open();
			contactname.setText(link.getName(contactno));
			contactimage.setImageResource(R.drawable.defaultcontactimage);
			Cursor c=link.logdata(contactno);
			if(c.moveToFirst()){
				while(!c.isAfterLast())
				{
					String Contactno=c.getString(0);
					String type=c.getString(1);
					String date=c.getString(2);
					
					
					logdetailbinadapter la=new logdetailbinadapter(Contactno, date, "", type);
					list.add(la);
					c.moveToNext();
				}
			}
			c.close();
			link.close();
			LogdetailAdapter ladp=new LogdetailAdapter(Logdetail.this, list);
			if(ladp!=null){
				lv.setAdapter(ladp);
			}
		}
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.logdetail, menu);
		return true;
	}

}
