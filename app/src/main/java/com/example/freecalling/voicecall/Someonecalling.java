package com.example.freecalling.voicecall;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;
import com.example.freecalling.db.Db;
import com.example.freecalling.server.Server;

public class Someonecalling extends Activity {
	

	ImageView contactimage;
	TextView contactname,contactno;
	Button accept,decline,block;
	static Context ctx;
	String callername,callerno,callerip,myno;
	Socket socket=null;
	ObjectOutputStream out=null;
	public static String oncallwith=null;
	public static Someonecalling someonecalling;
	public Uri currenturi;
	 Ringtone ringtone;
	 KeyguardManager kmanager;
	 KeyguardLock lock;
	 public static Vibrator vb=null; 
	 WindowManager.LayoutParams window;
	 int i=0;
	 int callhistory;
	
	 /*
	  * missedcall=0
	  * recivedcall=1
	  * rejectedcall=2
	  * blockedcall=3
	 
	 */
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_someonecalling);
		Bundle bdl=getIntent().getExtras();
		callerno=bdl.getString("callerno");
		callerip=bdl.getString("callerip");
		oncallwith=callerno;
		socket=Server.socket;
		out=Server.out;
		ctx=this;
		someonecalling=this;
		callhistory=0;
		
		  kmanager=(KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		 lock=kmanager.newKeyguardLock(KEYGUARD_SERVICE);
		
		window=this.getWindow().getAttributes();
		new Ringtonemanager().execute();
		
		
	
		
		
		
		
		final Db db=new Db(getApplicationContext());
		try {
			db.open();
			myno=db.myno();
			callername=db.getName(callerno);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{db.close();}
		
		contactimage=(ImageView) findViewById(R.id._contactimage);
		contactname=(TextView) findViewById(R.id._contactname);
		contactno=(TextView) findViewById(R.id._contactno);
		accept=(Button) findViewById(R.id._accept);
		decline=(Button) findViewById(R.id._decline);
		block=(Button) findViewById(R.id._block);
		
		if(!callername.equals(callerno)){
			contactname.setText(callername);
			contactno.setText(callerno);
			
		}else {
			contactname.setText("Unknown");
			contactno.setText(callerno);
		}
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

		boolean isScreenOn = pm.isScreenOn();

		if (isScreenOn == false) {

			WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
					| PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.ON_AFTER_RELEASE, "MyLock");

			wl.acquire(5000);
			WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
					"MyCpuLock");
			lock.disableKeyguard();
			wl_cpu.acquire(10000);
		}
		else{
			lock.disableKeyguard();
		}
		
		decline.setOnClickListener(new OnClickListener() {
			
			
			
			@Override
			public void onClick(View v) {
				callhistory=2;
				try {
					if(vb!=null){
						i=10;
						vb.cancel();
						
					}
					Log.d("cheaking out values", ""+out);
					out.writeObject("declined");
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				
					
					finish();
					
					lock.reenableKeyguard();
					lock=null;
					
				
			}
		});
		
		accept.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callhistory=1;
				try {
					
					if(vb!=null){
						
						vb.cancel();
						
					}
					
					out.writeObject("conformed");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				finish();
				Intent i=new Intent(getApplicationContext(),AudioRecord.class);
				
				
				i.putExtra("recivername", callername);
				i.putExtra("reciverno", callerno);
				i.putExtra("reciverip", callerip);
				i.putExtra("myno", myno);
				startActivity(i);
				
				
			}
		});
		
		block.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					db.open();
					String blockstate=db.blocker(callerno);
					Log.e("someonecalling", blockstate);

					callhistory=3;
					try {
						if(vb!=null){
							i=10;
							vb.cancel();
							
						}
						Log.d("cheaking out values", ""+out);
						out.writeObject("declined");
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					
					
						
						finish();
						
						lock.reenableKeyguard();
						lock=null;
						
					
				
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally {db.close();}
				
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(ringtone!=null){
			ringtone.stop();
			if(vb!=null){
				vb.cancel();
			}
		}
		if(vb!=null){
			vb.cancel();
		}
		
		try {
			
			out.writeObject("declined");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			oncallwith=null;
			socket=null;
			out=null;
			ctx=null;
			
		}
		if(someonecalling!=null)
		someonecalling=null;
		
		 Db db=new Db(getApplicationContext());
		Accuracycheak ac=new Accuracycheak();
		try{
			db.open();
		switch (callhistory) {
		case 0:
				db.logintery(callerno, "missed", "",ac.date());	
			break;
		case 1:
			db.logintery(callerno, "recived", "",ac.date());
			break;
		case 2:
			db.logintery(callerno, "rejected", "",ac.date());
			break;

		case 3:
			db.logintery(callerno, "blocked", "",ac.date());
			break;

		
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			db.close();
		}
	}
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
/*@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Someonecalling.someonecalling.finish();
		
	}
*/	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		Toast.makeText(getApplicationContext(), "call canceled", Toast.LENGTH_SHORT).show();

			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
					out.writeObject("declined");
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			});
			
		
	}

	public class Ringtonemanager extends AsyncTask<Void, Void, String>{

		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			
		}
		
		
		@Override
		protected String doInBackground(Void... params) {
			String result="no answer";
			
			AudioManager audio=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
			
			switch (audio.getRingerMode()) {
			case AudioManager.RINGER_MODE_NORMAL:
				currenturi=RingtoneManager.getActualDefaultRingtoneUri(ctx,RingtoneManager.TYPE_RINGTONE);
				ringtone=RingtoneManager.getRingtone(ctx, currenturi);
				if(ringtone!=null){
					ringtone.play();
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
					break;
			
			/*case AudioManager.RINGER_MODE_SILENT:
				int iac=0;
				PowerManager pm = null;
				WakeLock wl=pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "calling");
				
				
				while(true){
					iac++;
					wl.release();
					
					try {
						Thread.sleep(1000);
						wl.acquire();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(iac>30){
						break;
					}		
				}
			
					break;
			
			
					
					*/
					
			case AudioManager.RINGER_MODE_VIBRATE:
				
				
				vb=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				
				while(Someonecalling.someonecalling!=null && i<10){
					vb.vibrate(1000);
							
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							}
							
							
							i++;
						}
					
				
			default:
				break;
			}
			
			
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(someonecalling!=null){someonecalling.finish();}
			
			
		}
		
	}
	

}
