package com.example.freecalling.voicecall;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;
import com.example.freecalling.Viewactivity;
import com.example.freecalling.db.Db;
import com.example.freecalling.messaging.Sendsms;
import com.example.freecalling.server.Server;

public class Calllist extends Activity {

	final ArrayList<ContactBin> list=new ArrayList<ContactBin>();
	ListView lm;
	String name, number, image, email, ip;
	TabHost callfilter;
	AlertDialog ad;
	String myno = null,myip = null,mydeviceid = null,reciverno = null,reciverip=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calllist);
		lm=(ListView) findViewById(R.id._contactlist);
		//callfilter=getTabHost();
		
		AutoCompleteTextView find=(AutoCompleteTextView)findViewById(R.id._findcontact);
		
		find.setFocusable(false);
		find.setFocusableInTouchMode(true);
		
		Button add=(Button)findViewById(R.id._addnew);
		add.requestFocus();
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				Intent i=new Intent(Calllist.this,AddNewContact.class);
				i.putExtra("contactno", "");
				i.putExtra("contactname", "");
				i.putExtra("image", "");
				i.putExtra("email", "");
				i.putExtra("ip", "");
				startActivity(i);
			}
		});
		
		lm.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ContactBin bin =list.get(arg2);
				
				//Toast.makeText(Calllist.this, bin.getName(), Toast.LENGTH_SHORT).show();
				
				Intent i=new Intent(Calllist.this,Viewactivity.class);
				i.putExtra("contactno", bin.getNumber());
				i.putExtra("name", bin.getName());
				i.putExtra("image", bin.getImage());
				i.putExtra("email", bin.getEmail());
				if(bin.getIp().equals("noip")){
					i.putExtra("ip", "");
				}
				else
				i.putExtra("ip", bin.getIp());
				startActivity(i);
				
			}
		});
		
		lm.setOnItemLongClickListener(new OnItemLongClickListener() {
			String namee;
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				ContactBin bin=list.get(arg2);
				 namee=bin.getNumber();
				AlertDialog.Builder builder=new AlertDialog.Builder(Calllist.this);
				builder.setTitle("select ");
				final ArrayAdapter<String>array=new ArrayAdapter<String>(Calllist.this, android.R.layout.select_dialog_item);
				array.add("Send Sms");
				array.add("Call");
				array.add("locate");
				array.add("Block");
				array.add("Delete");
				builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.setAdapter(array, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String name=array.getItem(which);
						if(name.equals("Send Sms")){
							finish();
							Intent i=new Intent(Calllist.this,Sendsms.class);
							i.putExtra("contactno", list.get(arg2).getNumber().trim());
							i.putExtra("message", "");
							startActivity(i);
						}
						else if(name.equals("Call")){
							Accuracycheak ac=new Accuracycheak();
							String connectionState=ac.dataConncetionCheak(getApplicationContext());
							if(connectionState=="connected"){
								finish();
								Intent i=new Intent(Calllist.this,Call.class);
								i.putExtra("contactname", list.get(arg2).getNumber().trim());
								
								startActivity(i);	
							}
							else{
								Toast.makeText(getApplicationContext(), "You are not connected to a Wifi network please connect and then try..", Toast.LENGTH_SHORT).show();
							}
							
									
							
							}
						
						else if(name.equals("locate")){


							
							
							//signature of location packet "show your location",reciverno,myno,myip,mydevid
							Accuracycheak ac=new Accuracycheak();
							Db db1=new Db(getApplicationContext());
							
							try {
								db1.open();
								 reciverip=db1.ipaddress(list.get(arg2).getNumber().trim());
								 myno=db1.myno();
								 myip=ac.ipAddress();
								 mydeviceid=ac.deviceId(getApplicationContext());
								 reciverno=list.get(arg2).getNumber().trim();
							} catch (Exception e) {
								e.printStackTrace();
							}
							finally{db1.close();}
							
							
							if( myno != null && myip != null && mydeviceid != null && reciverno != null){
								String[] locationrequest={"show your location",reciverno,myno,myip,mydeviceid};
								Locationasync la=new Locationasync();
								la.execute(locationrequest);
								
								if(reciverip!=null && list.get(arg2).getNumber().trim()!=null){
									
								}
								else{
								AlertDialog.Builder a=new AlertDialog.Builder(Calllist.this);
								a.setTitle("error");
								a.setMessage("ip address not found for this contact no");
								a.setNegativeButton("cancel", null);
								a.show();
								}
							}else{
								
							}
								
							
							
						
						}
						else if(name.equals("Block")){
							Db link=new Db(Calllist.this);
							link.open();
							Toast.makeText(Calllist.this, link.blocker(list.get(arg2).getNumber()), Toast.LENGTH_SHORT).show();
							link.close();
							list.clear();
							updatelist();
							
						}
						else if(name.equals("Delete")){
							Db link=new Db(Calllist.this);
							link.open();
							
							Toast.makeText(Calllist.this, link.deleteContact(list.get(arg2).getNumber()), Toast.LENGTH_SHORT).show();
							link.close();
							list.clear();
							updatelist();
							
						}

					}
				});
				
				AlertDialog alert=builder.create();
				alert.show();
				
				return true;
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		list.clear();
		updatelist();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calllist, menu);
		return true;
	}
	public void updatelist(){
		String contactno;
		Db db=new Db(Calllist.this);
		db.open();
		Cursor c=db.getContactNo();
		
		
		if(c.moveToFirst()){
			
			while(!c.isAfterLast()){
				
				contactno=c.getString(c.getColumnIndex("contactno"));
				Cursor cc=db.contactdata(contactno);
				if(cc.moveToFirst()){
					while(!cc.isAfterLast())
					{
						
						name=cc.getString(0);
						image=cc.getString(1);
						email=cc.getString(2);
						ip=db.ipaddress(contactno);
						
						cc.moveToLast();
						cc.moveToNext();
					}
					cc.close();
				}
					
				ContactBin cbin=new ContactBin(name, contactno, image, email, ip);
				
				list.add(cbin);
				
			c.moveToNext();
			}
			c.close();
			
			//ArrayAdapter cdd=new ArrayAdapter(this,android.R.layout.simple_list_item_1,abc);
			//find.setAdapter(cdd);
		}
		
		ContactAdapter ca=new ContactAdapter(this, list);
		if(ca!=null){
		lm.setAdapter(ca);
		}
		
		db.close();
		
	}
	
	class Locationasync extends AsyncTask<String[], Void, String>{
		
		@Override
		protected void onPreExecute() {
			
			LayoutInflater li=LayoutInflater.from(Calllist.this);
			View mydialog=li.inflate(R.layout.customdialog, null);
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(Calllist.this);
			dialog.setView(mydialog);
			dialog.setMessage("this is a custom dialog");
			
		 ad=dialog.create();
			ad.show();
			
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String[]... params) {
			String result=null;
			try {
				Thread.sleep(2000);
				//creating a custom dialog box
			
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			ad.dismiss();
			
			if(result!=null){
				
			}
			else{
				
			}
			super.onPostExecute(result);
		}
	
	}
	
	void backgroundwork(String[] locationrequest){
		Server.locationrequest(reciverip, locationrequest);
	}
}
