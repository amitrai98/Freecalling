package com.example.freecalling.messaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freecalling.R;
import com.example.freecalling.db.Db;
import com.example.freecalling.voicecall.AddNewContact;

public class Smslist extends Activity {
	Context ctx;
	ListView list;
	Myreciver myreciver;
	public static Smslist smslist;
	 ArrayList<Smsinitializer> object=new ArrayList<Smsinitializer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smslist);
		ctx=this;
		smslist=this;
		myreciver=new Myreciver(new Handler());
		registerReceiver(myreciver,new IntentFilter("updatesmslist"));
		updatesmslist();
		
		
		TextView newmsg=(TextView)findViewById(R.id._newmessage);
		newmsg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i=new Intent(Smslist.this,Sendsms.class);
				i.putExtra("contactno", "");
				i.putExtra("message","");
				startActivity(i);
			}
		});
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Smsinitializer bin=object.get(arg2);
				
				Intent i=new Intent(Smslist.this,Sendsms.class);
				i.putExtra("contactno",object.get(arg2).getNumber());
				startActivity(i);
			}
		});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			String name;
			final ArrayAdapter<String> choice=new ArrayAdapter<String>(Smslist.this, android.R.layout.select_dialog_item);
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Smsinitializer bin=object.get(arg2);
				 name=bin.getNumber();
				AlertDialog.Builder builder=new AlertDialog.Builder(Smslist.this);
				builder.setTitle("select");
				choice.add("delete thread");
				Db link=new Db(Smslist.this);
				try {
					
					link.open();
					if(object.get(arg2).getNumber().equals(link.getName(object.get(arg2).getNumber()))){
						choice.add("add to contact");
					}
					else
					{
						choice.add("update contact");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally{link.close();}
			
				
				
				builder.setAdapter(choice, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(choice.getItem(which).equals("delete thread"))
						{
							Db link=new Db(Smslist.this);
							try {
								
								link.open();
								
								Toast.makeText(Smslist.this, link.deletesms(name), Toast.LENGTH_SHORT).show();
								object.clear();
								updatesmslist();
							
							link.close();
								
							} catch (Exception e) {
								e.printStackTrace();
							}finally{link.close();}
							
							
							
						}
						else if(choice.getItem(which).equals("update contact")|| choice.getItem(which).equals("add to contact")){
							Intent i=new Intent(Smslist.this,AddNewContact.class);
							i.putExtra("contactno", object.get(arg2).getNumber());
							startActivity(i);
							
						}
					}
				});
				
				/*@Override
					public void onClick(DialogInterface dialog, int which) {
						Databasehelper link=new Databasehelper(Smslist.this);
						link.open();
						
							Toast.makeText(Smslist.this, link.deletesms(name), Toast.LENGTH_SHORT).show();
							object.clear();
							updatesmslist();
						
						link.close();
						
						
					}
				});*/
				builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				AlertDialog alert=builder.create();
				alert.show();

				return true;
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.smslist, menu);
		return true;
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		
		
		updatesmslist();
		
			//Toast.makeText(this, "resumed called", Toast.LENGTH_SHORT).show();
		
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(myreciver);
	}
	
	
	
	public synchronized void updatesmslist()
	{
		
			object.clear();
			String contactname = null,image = null,type = null,status = null,contactno;
			String date = null;
			list =(ListView) findViewById(R.id._msglist);
			Db link=new Db(Smslist.this);
			Cursor c1 = null;
			Cursor c=null;
			
			try{
					
					link.open();
					 c1=link.distinctmsglog();
					if(c1.moveToFirst())
					{
						while(!c1.isAfterLast()){
							String cno=c1.getString(0);
							 c=link.inbox(cno);
							if (c.moveToFirst() ) 
							{
								while(!c.isAfterLast())
								{
									contactno=c.getString(0);
									contactname=link.getName(contactno);
									status=c.getString(1);
									type=c.getString(2);
									date=c.getString(3);
									image=link.image(contactno);
								
									Smsinitializer obj=new Smsinitializer(contactno,image, contactname, status, type, date);
									object.add(obj);
									c.moveToLast();
									c.moveToNext();
								}
								
								c.close();	
							}
							else
							{
								contactname="no message to display";
								image="defaultimage";
								status="";
								type="";
								final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								date=parser.format(new Date());
								
							}
							
							c1.moveToNext();
							
						}
						
					}
					else{
						Smsinitializer obj=new Smsinitializer("","", "no messages", "", "", "");
						object.add(obj);
						Smsadapter s=new Smsadapter(this, object);
						if(s!=null ){
						list.setAdapter(s);
					}
					//c1.close();
					link.close();
				
				}
			}
					
				catch (Exception e) {
					c.close();	
					c1.close();
				e.printStackTrace();
			}finally{link.close();
			
			c1.close();
			}
			
			Smsadapter s=new Smsadapter(this, object);
			if(s!=null ){
				list.setAdapter(s);
			}
			
		
		
	
	}
	
	public class Myreciver extends BroadcastReceiver {

		private final Handler handler;

		public Myreciver(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void onReceive(final Context context, Intent intent) {
			handler.post(new Runnable() {

				@Override
				public void run() {
					updatesmslist();
				}
			});
		}

	}

	/*class a {
		public void doSomthing() {
			
		}
	}*/
}

