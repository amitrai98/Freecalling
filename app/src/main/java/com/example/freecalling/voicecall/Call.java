package com.example.freecalling.voicecall;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;
import com.example.freecalling.db.Db;

public class Call extends Activity {

	Button cancel;
	String recivername,reciverno,reciverip,myno;
	TextView contactname,contactno;
	static Socket sc=null;
	static ObjectOutputStream out; 
	static int time=0;
	public static Call call;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
		Bundle bdl = getIntent().getExtras();
		recivername = bdl.getString("recivername");
		reciverno=bdl.getString("reciverno");
		reciverip=bdl.getString("reciverip");
		myno=bdl.getString("myno");
		PowerManager pm;
		call=this;
		
		Db db=new Db(getApplicationContext());
		Accuracycheak ac=new Accuracycheak();
		try {
			db.open();
			db.logintery(reciverno, "dialed", "",ac.date());	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{db.close();}
		
		
		new Thread(new  Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(time);
					finish();
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
			}
		});
		
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		contactname=(TextView) findViewById(R.id._contactname);
		contactno=(TextView) findViewById(R.id._contactnos);
		
		
		if(recivername!=reciverno){
			contactname.setText(recivername);
			contactno.setText(reciverno);
		}
		else{
			contactname.setText("Unknown");
			contactname.setText(reciverno);
		}
		
		
		
		cancel=(Button) findViewById(R.id._cancel);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				/*new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							
							Socket sc1=new Socket(reciverip, 4444);
							
							if(out!=null){
								String datapacket[]={"call end request",reciverno,myno,reciverip};
								out.writeObject(datapacket);
							}
							else{
								ObjectOutputStream out= new ObjectOutputStream(sc1.getOutputStream());
								String datapacket[]={"call end request",reciverno,myno,reciverip};
								out.writeObject(datapacket);
							}
							
							
							sc1.close();
								
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
						
						}
					}
				});*/
					try {
						Accuracycheak ac=new Accuracycheak();
						sc=new Socket(reciverip, 4444);
						
						out= new ObjectOutputStream(sc.getOutputStream());
						
						String datapacket[]={"call end request",reciverno,myno,ac.ipAddress()};
						
							
							out.writeObject(datapacket);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				
				
				finish();
				
				Toast.makeText(getApplicationContext(), "call canceled", Toast.LENGTH_SHORT).show();
			}
		});
		
	
		
		new taskhandler().execute();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if(call!=null){
			call=null;
		}
		finish();
		
		Toast.makeText(getApplicationContext(), "call ended", Toast.LENGTH_SHORT).show();
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.call, menu);
		return true;
	}
	
	class taskhandler extends AsyncTask<Void, Void, String>
	{
		
		
		@Override
		protected void onPreExecute() {
			
			super.onPreExecute();
			
			
		}
		
		@Override
		protected String doInBackground(Void... params) {
			String state=null;
			
			state=backgroundmethod();
			
			return state;
		}
		
		@Override
		protected void onPostExecute(String result) {
			if(result!=null&&result.equals("conformed")){
				finish();
				Intent i=new Intent(getApplicationContext(),AudioRecord.class);
				
				i.putExtra("recivername", reciverno);
				i.putExtra("reciverno", reciverno);
				i.putExtra("reciverip", reciverip);
				i.putExtra("myno", myno);
				startActivity(i);
			}else{
				finish();
			}
			super.onPostExecute(result);
		}
		
		
		
		
		String backgroundmethod(){
			
			String status=null;
			
			try {
				Db db=new Db(getApplicationContext());
				db.open();
				String myip=db.myip();
				Accuracycheak ac=new Accuracycheak();
				if(reciverip!="noip" && reciverip!=null && ac.ipAddress()!=null && ac.ipAddress()!="errorfetchingip"){
				
				sc=new Socket(reciverip, 4444);
				
				out= new ObjectOutputStream(sc.getOutputStream());
				
				String datapacket[]={"call request",reciverno,myno,myip,ac.deviceId(getApplicationContext())};
				
					
					out.writeObject(datapacket);
					
					ObjectInputStream msg = new ObjectInputStream(sc.getInputStream());
					String mess = (String) msg.readObject();
					out.close();
					sc.close();
					
					if(mess.equals("conformed")){
						status="conformed";
					}
					else{
						status="declined";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return status;
			}
	}

}
