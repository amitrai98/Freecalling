package com.example.freecalling.webservices;

import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.Toast;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;
import com.example.freecalling.db.Db;

public class Jsonhandler extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jsonhandler);
		
		
	}
	public String smssenderjson(String contactno,String message)
	{
		String status="message not sent";
		JSONObject sendsms=new JSONObject();
		Accuracycheak ac=new Accuracycheak();
		Db db=new Db(Jsonhandler.this);
		
		try {
			db.open();
			if(!TextUtils.isEmpty(contactno)){
				if(!db.findip(contactno).equals("ip not available") && !TextUtils.isEmpty(db.findip(contactno))){
					if(!db.myno().equals("not known") && !TextUtils.isEmpty(db.myno())){
						if(!ac.ipAddress().equals("errorfetchingip") && !TextUtils.isEmpty(ac.ipAddress())){
							if(!ac.deviceId(Jsonhandler.this).equals("deviderror") && !TextUtils.isEmpty(ac.deviceId(Jsonhandler.this))){
								if(!TextUtils.isEmpty(ac.date())){
									
									sendsms.put("reciverno", contactno);
									sendsms.put("reciverip", db.findip(contactno));
									sendsms.put("senderno", db.myno());
									sendsms.put("senderip", ac.ipAddress());
									sendsms.put("senderdevid", ac.deviceId(Jsonhandler.this));
									sendsms.put("message",message);
									sendsms.put("date", ac.date());
								}
								else{
									Toast.makeText(Jsonhandler.this, "date is not valid", Toast.LENGTH_SHORT).show();
								}
							}
							else{
								Toast.makeText(Jsonhandler.this, "device id is not available", Toast.LENGTH_SHORT).show();
							}
						}
						else{
							Toast.makeText(Jsonhandler.this, "ip address is not available", Toast.LENGTH_SHORT).show();
						}
					}
					else{
						Toast.makeText(Jsonhandler.this, "ip address is not available", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					Toast.makeText(Jsonhandler.this, "ip address is not available", Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Toast.makeText(Jsonhandler.this, "contact no is empty", Toast.LENGTH_SHORT).show();
			}
			
			
			
			
			HttpClient client=new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(client.getParams(), 5000);
			HttpResponse response;
			
			HttpPost post=new HttpPost("http://localhost");
			StringEntity se=new StringEntity(sendsms.toString());
			se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(se);
			response=client.execute(post);
			
			if(response!=null){
				InputStream in=response.getEntity().getContent();
				System.out.println(in);
				
			}
			db.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return status;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.jsonhandler, menu);
		return true;
	}

}
