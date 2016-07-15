package com.example.freecalling;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.Menu;

import com.example.freecalling.activities.Registration;
import com.example.freecalling.db.Db;

public class MainActivity extends Activity {
	private String id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		
		/*String message[]={"reciverno","senderno","senderip"};
		byte[][] data=new byte[message.length][];
		for(int i=0;i<message.length;i++){
			String msg=message[i];
			data[i]=msg.getBytes();
			System.out.println(data[i]);
		}
		
		File f;
		try {
			Boolean isSDPresent=android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	    	if(isSDPresent==true){
	    		f=new File(Environment.getExternalStorageDirectory()+File.separator+"ab.mp3");
	    		System.out.println("media is available");
	    	}
	    	else{
	    		f=new File(getApplicationContext().getFilesDir()+File.separator+"ab.mp3");
	    		System.out.println("will be writting on internal storage");
	    	}
	    	
	    	byte[] bytes = new byte[(int) f.length()];
	        FileInputStream fis = new FileInputStream(f);
	        BufferedInputStream bis = new BufferedInputStream(fis);
	        BufferedOutputStream out = new BufferedOutputStream(new OutputStream());
	    	
	    	

	        int count;

	        while ((count = bis.read(bytes)) > 0) {
	            out.write(bytes, 0, count);
	        }
		
		String msg[]=new String [data.length];
		
		for(int i=0;i<data.length;i++){
			
			msg[i]=new String(data[i]);
			System.out.println(msg[i]);
		}
		
		
		
		*/
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
		
		id = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID); 
		Db link=new Db(MainActivity.this);
		link.open();
	
		Cursor c=link.mycontactlist();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				System.out.println(c.getString(0)+"  "+c.getString(1));
				c.moveToNext();
			}
		}
		Accuracycheak ac=new Accuracycheak();
		
		String status=link.cheakAppStatus(id);
		//Toast.makeText(MainActivity.this, link.myipudate(ac.ipAddress()), Toast.LENGTH_SHORT).show();
		//Toast.makeText(MainActivity.this, link.myno(), Toast.LENGTH_LONG).show();
		
		link.close();
		if(status.equals("registered"))
		{
			/*
			try {
				System.out.println("calling server");
				new Serverface(6000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			/*new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("hello");
					try {
						new Server(6000);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
				*/	
			finish();	
			
						//startService(new Intent(getBaseContext(),Backgroundservice.class));
			/*new Thread(new Runnable() {
				
				@Override
				public void run() {
					new Invokeserver();
				}
			}).start();
					*/
			
			Intent ia=new Intent(MainActivity.this,GridViewActivity.class);
			startActivity(ia);
		}else
			
		{
			
			Intent ia=new Intent(MainActivity.this,Registration.class);
			startActivity(ia);
			finish();
		}
		
		
		
		
	}	
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
	
}
