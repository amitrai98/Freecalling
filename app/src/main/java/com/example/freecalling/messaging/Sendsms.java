package com.example.freecalling.messaging;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;
import com.example.freecalling.db.Db;
import com.example.freecalling.voicecall.Calllist;

public class Sendsms extends Activity {
	private Myreciver myreciver;
	TextView contact;
	EditText message;
	Context ctx;
	Button send, contactbtn;
	String no;
	String msgg;
	ListView lv;
	public static Sendsms sendsms;
	public ProgressDialog pd;
	static ArrayList<Messagebin> list = new ArrayList<Messagebin>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		sendsms=this;
		setContentView(R.layout.activity_sendsms);
		myreciver = new Myreciver(new Handler());
		registerReceiver(myreciver, new IntentFilter("updatelist"));
		Bundle bdl = getIntent().getExtras();
		no = bdl.getString("contactno");
		msgg = bdl.getString("message");
		lv = (ListView) findViewById(R.id._smslist);

		contact = (TextView) findViewById(R.id._contactbox);
		message = (EditText) findViewById(R.id._messagebox);

	
		new updateListTask().execute();
		send = (Button) findViewById(R.id._send);
		contactbtn = (Button) findViewById(R.id._contactbtn);

		send.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				messagesendingasync ms=new messagesendingasync();
				ms.execute();
			}
			
			
			
		});

		contactbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(Sendsms.this, Calllist.class);
				startActivity(i);
			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			String namee;

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				Messagebin bin = list.get(arg2);
				namee = bin.getMessage();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						Sendsms.this);
				builder.setTitle("select ");
				final ArrayAdapter<String> array = new ArrayAdapter<String>(
						Sendsms.this, android.R.layout.select_dialog_item);
				array.add("delete thread");
				array.add("forwarud message");
				builder.setNegativeButton("cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.setAdapter(array,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String name = array.getItem(which);
								if (name.equals("delete thread")) {
									dialog.dismiss();
									Db link = new Db(
											Sendsms.this);
									
									try {
										link.open();

										Toast.makeText(Sendsms.this,
												link.deleteonemessage(namee),
												Toast.LENGTH_SHORT).show();
										list.clear();
										//updatelist(Sendsms.this);
										new updateListTask().execute();
										
									} catch (Exception e) {
										e.printStackTrace();
									}finally{
										link.close();
									}

									
									

								} else if (name.equals("forwarud message")) {
									finish();
									Intent i = new Intent(Sendsms.this,
											Sendsms.class);
									i.putExtra("contactno", "");
									i.putExtra("message", list.get(arg2)
											.getMessage());
									startActivity(i);
								}
							}
						});
				AlertDialog alert = builder.create();
				alert.show();

				/*
				 * builder.setCancelable(true); builder.setPositiveButton("yes",
				 * new DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick(DialogInterface dialog, int
				 * which) { Databasehelper link=new
				 * Databasehelper(Sendsms.this); link.open();
				 * if(link.deletesms(name).equals("deleted")){
				 * Toast.makeText(Sendsms.this, "deleted",
				 * Toast.LENGTH_SHORT).show(); } else{
				 * Toast.makeText(Sendsms.this, "not found",
				 * Toast.LENGTH_SHORT).show(); } link.close();
				 * 
				 * 
				 * } }); builder.setNegativeButton("no", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick(DialogInterface dialog, int
				 * which) { dialog.cancel(); } }); AlertDialog
				 * alert=builder.create(); alert.show();
				 */

				return true;
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		setResult(Activity.RESULT_OK);
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		sendsms=null;
		unregisterReceiver(myreciver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sendsms, menu);
		return true;
	}

	class updateListTask extends AsyncTask<String, Void, ArrayList<Messagebin>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			message.setText("");
			message.setFocusable(true);
			list.clear();
			
		}

		@Override
		protected ArrayList<Messagebin> doInBackground(String... params) {
			// TODO Auto-generated method stub
			Db db = new Db(ctx);
			Cursor ccc = null;
			try {
				db.open();
				ccc = db.particularMessage(no);
				if (ccc.moveToFirst()) {
					while (!ccc.isAfterLast()) {
						String msg = ccc.getString(0);
						String date = ccc.getString(1);
						String messagetype = ccc.getString(2);
						Messagebin mbin = new Messagebin(msg, date, messagetype);
						list.add(mbin);
						ccc.moveToNext();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				//ccc.close();
				db.close();
			}
			
			
			return list;
		}

		@Override
		protected void onPostExecute(ArrayList<Messagebin> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			updatelist(result);
		}
	}

	public void updatelist(ArrayList<Messagebin> list1) {
		
		contact.setText(no);
		if (!TextUtils.isEmpty(contact.getText().toString().trim())) {

			if (list.size() > 0) {
				message.setFocusable(false);
				contact.setFocusable(false);
				message.setFocusableInTouchMode(true);
			} else {
				message.requestFocus();
			}
		} else if (TextUtils.isEmpty(contact.getText().toString().trim())
				&& !TextUtils.isEmpty(msgg))

		{
			message.setText(msgg);
			contact.requestFocus();
		}

		Messageadapter ma = new Messageadapter();
		ma.Messageadapters(Sendsms.this, list1);
		lv.setAdapter(ma);
		lv.setSelection(list.size() - 1);

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
					a aa = new a();
					aa.doSomthing();
				}
			});
		}

	}

	class a {
		public void doSomthing() {
			new updateListTask().execute();
			//updatelist(ctx);
		}
	}
	
	class messagesendingasync extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute() {
			pd=new ProgressDialog(Sendsms.this);
			pd.setTitle("sending message");
			pd.setMessage("Please wait while your message is being sent");
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				
				sendmessage();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			
			super.onPostExecute(result);
		}
		
	}
	
	
	void sendmessage(){

		String reciverno = contact.getText().toString().trim();
		String msg = message.getText().toString().trim();

		Messegecentralbase mm = new Messegecentralbase(Sendsms.this);
		Accuracycheak ac = new Accuracycheak();
		if (reciverno != null
				&& ac.noCheak(reciverno).equals("validno")) {

			if (!TextUtils.isEmpty(msg)) {

				try {
					Accuracycheak acc = new Accuracycheak();
					if (acc.dataConncetionCheak(Sendsms.this).equals(
							"connected")
							|| acc.wifiCheak(Sendsms.this).equals(
									"enabled")) {
						
						mm.messagesender(reciverno, msg);
					} else {

						mm.toaster("please connect to a wifi or use enable internet connection");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			else {
				mm.toaster("can not send an empty message");
			}

		} else {
			mm.toaster("please insert a valid contact nunmber");
		}
	
	}
}
