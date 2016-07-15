package com.example.freecalling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freecalling.db.Db;
import com.example.freecalling.messaging.Sendsms;
import com.example.freecalling.voicecall.Call;

public class Viewactivity extends Activity {
	TextView contactname,contactno,email,ip, call,sendmessage;
	ImageView contactimage;
	String reciverno,recivername,reciverip,myno;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle bd=getIntent().getExtras();
		String no=bd.getString("contactno");
		String name=bd.getString("name");
		//String image=bd.getString("image");
		String emailid=bd.getString("email");
		String ipadd=bd.getString("ip");
		Db link=new Db(getApplicationContext());
		
		
	/*	try {
			link.open();
			myno=link.myno();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			link.close();
		}*/
		
		setContentView(R.layout.activity_viewactivity);
		contactname=(TextView) findViewById(R.id._contactname);
		contactno=(TextView) findViewById(R.id._contactno);
		email=(TextView) findViewById(R.id._emailid);
		ip=(TextView) findViewById(R.id._ipaddress);
		contactimage=(ImageView) findViewById(R.id._contactimage);
		call=(TextView) findViewById(R.id._call);
		sendmessage=(TextView) findViewById(R.id._sendmessage);
		
		if(!TextUtils.isEmpty(name)){contactname.setText(name);}
		if(!TextUtils.isEmpty(no)){contactno.setText(no);}
		if(!TextUtils.isEmpty(emailid)){email.setText(emailid);}
		if(!TextUtils.isEmpty(ipadd)){ip.setText(ipadd);}
		
call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Db link=new Db(getApplicationContext());
				try {
					reciverno=contactno.getText().toString().trim();
					link.open();
					recivername=link.getName(reciverno);
					reciverip=link.ipaddress(reciverno);
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
						Intent i=new Intent(Viewactivity.this,Call.class);
						i.putExtra("recivername", recivername);
						i.putExtra("reciverno", reciverno);
						i.putExtra("reciverip", reciverip);
						i.putExtra("myno", myno);
						
						startActivity(i);	
						}
					}
					
					else{
						Toast.makeText(Viewactivity.this, "You are not connected to a Wifi network please connect and then try..", Toast.LENGTH_SHORT).show();
					}
					}
					else{
						Toast.makeText(Viewactivity.this, "please first cheack the contact no ipaddress and contact no of the reciver..", Toast.LENGTH_SHORT).show();
					}
					
			
			}
		});
		
sendmessage.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		
		String cno=contactno.getText().toString().trim();
	
		if(cno!=null && cno!=myno){
	
		System.out.println(cno);
		if(cno!=null && cno!=" " && cno!=myno){
			finish();
			Intent i=new Intent(Viewactivity.this,Sendsms.class);
			i.putExtra("contactno",cno);
			i.putExtra("message", "");
			startActivity(i);
		}
		else{
			Toast.makeText(Viewactivity.this, "This is not a valid no.", Toast.LENGTH_SHORT).show();
		}
		}else{
			Toast.makeText(Viewactivity.this, "This is not a valid no.", Toast.LENGTH_SHORT).show();
		}
	}
});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.viewactivity, menu);
		return true;
	}

}
