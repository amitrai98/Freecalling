package com.example.freecalling.voicecall;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.R;
import com.example.freecalling.db.Db;
import com.example.freecalling.messaging.Sendsms;

public class AddNewContact extends Activity {

	String namee,emailid,ipadd;
	TextView firstname,lastname,contactno,emailaddress,ipaddress;
	Button save,cancel,call,sendmessage;
	ImageView img;
	String status="notcreated",cname;
	String recivername,reciverno,reciverip,myno;
	Bitmap photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_contact);
		
		Bundle bd=getIntent().getExtras();
		String no=bd.getString("contactno");
		String name=bd.getString("name");
		//String image=bd.getString("image");
		String email=bd.getString("email");
		String ip=bd.getString("ip");
		
		firstname=(TextView) findViewById(R.id._firstname);
		
		contactno=(TextView) findViewById(R.id._contactno);
		emailaddress=(TextView) findViewById(R.id._email);
		ipaddress=(TextView) findViewById(R.id._ipaddress);
		img=(ImageView)findViewById(R.id._defaultcontactimage);
		call=(Button) findViewById(R.id._call);
		sendmessage=(Button) findViewById(R.id._sendmessage);
		
		sendmessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String cno=contactno.getText().toString().trim();
				if(cno!=null){
			/*	Databasehelper link=new Databasehelper(AddNewContact.this);
				try {
					link.open();
					cno=link.getName(cno);
					link.close();
				} catch (Exception e) {
					// TODO: handle exception
				}finally{link.close();}*/
				System.out.println(cno);
				if(cno!=null && cno!=" "){
					finish();
					Intent i=new Intent(AddNewContact.this,Sendsms.class);
					i.putExtra("contactno",cno);
					i.putExtra("message", "");
					startActivity(i);
				}
				else{
					Toast.makeText(AddNewContact.this, "This is not a valid no.", Toast.LENGTH_SHORT).show();
				}
				}else{
					Toast.makeText(AddNewContact.this, "This is not a valid no.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		/*call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				System.out.println("call was pressed");
				Databasehelper link=new Databasehelper(getApplicationContext());
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
						if (!TextUtils.isEmpty(recivername) && !TextUtils.isEmpty(reciverno) && !TextUtils.isEmpty(reciverip) && !TextUtils.isEmpty(myno)) {
							{
						
						finish();
						Intent i=new Intent(AddNewContact.this,Call.class);
						i.putExtra("recivername", recivername);
						i.putExtra("reciverno", reciverno);
						i.putExtra("reciverip", reciverip);
						i.putExtra("myno", myno);
						
						startActivity(i);	
						}
					}
					
					else{
						Toast.makeText(AddNewContact.this, "You are not connected to a Wifi network please connect and then try..", Toast.LENGTH_SHORT).show();
					}
					}
					else{
						Toast.makeText(AddNewContact.this, "please first cheack the contact no ipaddress and contact no of the reciver..", Toast.LENGTH_SHORT).show();
					}
					
			
			}
		});*/
		
		contactno.setText(no);
		
		Db link=new Db(AddNewContact.this);
		link.open();
		Cursor cc=link.getcontactdetail(no);
		if(cc.moveToFirst())
		{
			while(!cc.isAfterLast()){
				namee=cc.getString(0);
				emailid=cc.getString(1);
				ipadd=link.findip(no);
				cc.moveToLast();
				cc.moveToNext();
			}
			cc.close();
		}
		
		
		if(TextUtils.isEmpty(name)){
			firstname.setText(namee);
		}
		else {
			firstname.setText(name);
		}
		if(TextUtils.isEmpty(email)){
			emailaddress.setText(emailid);
		}
		else{
			emailaddress.setText(email);
		}
		if(TextUtils.isEmpty(ip)){
			ipaddress.setText(ipadd);
		}
		else{
			ipaddress.setText(ip);
		}
		
		link.close();
		
		
		img=(ImageView) findViewById(R.id._contactimage);
		save=(Button) findViewById(R.id._save);
		cancel=(Button) findViewById(R.id._cancel);
		save.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name=firstname.getText().toString().trim() ;
				String cno=contactno.getText().toString().trim();
				String email=emailaddress.getText().toString().trim();
				String ip=ipaddress.getText().toString().trim();
				if(ip==null || ip.equals("")){
					Db db=new Db(getApplicationContext());
					try {
						db.open();
						ip=db.ipaddress(cno);
						if(ip.equals("noip"))
						{
							ip="";
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally {db.close();}
				}
				Accuracycheak ac=new Accuracycheak();
				if(ac.isNameValid(name)==true ){
					if(ac.noCheak(cno).equals("validno") ){
						if(ac.emailCheak(email).equals("validemail")|| TextUtils.isEmpty(email)){
							if(ac.cheakip(ip).equals("validip") || TextUtils.isEmpty(ip)){
								
								
								
								Db db=new Db(AddNewContact.this);
								
								
								if(photo==null)
								{
									db.open();
									status=db.addContact(cno, name, email, null,ip);
									db.close();
								}
								else{
									db.open();
									status=db.addContact(cno, name, email, ac.BitMapToString(photo),ip);
									db.close();
								}
								
								if(status.equals("updated") || status.equals("contactcreated")){
									db.open();
									db.cheakid(cno, ip, ac.deviceId(AddNewContact.this));
									db.close();
									Toast.makeText(AddNewContact.this, "contact created", Toast.LENGTH_SHORT).show();
								}
								else{Toast.makeText(AddNewContact.this, "contact was not created", Toast.LENGTH_SHORT).show();}
								
								
								finish();
								Intent i=new Intent(AddNewContact.this,Calllist.class);
								startActivity(i);
								
								}else{Toast.makeText(AddNewContact.this, "ip address is not valid", Toast.LENGTH_SHORT).show();}
							
						}else{Toast.makeText(AddNewContact.this, "email address is not valid", Toast.LENGTH_SHORT).show();}
						
					}else{Toast.makeText(AddNewContact.this, "number is not valid", Toast.LENGTH_SHORT).show();}
					
				}else{Toast.makeText(AddNewContact.this, "name is not valid", Toast.LENGTH_SHORT).show();}
			}
			
		});
		
		img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);//
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),100);
				
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						finish();
						
					}
				});
		link.close();
	}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	
	   if(requestCode == 100)
	    {
	        if (resultCode == RESULT_OK) {
	            Uri selectedImageUri = data.getData();
	            String selectedImagePath = getPath(selectedImageUri);
	            System.out.println(getPath(selectedImageUri));
	            Accuracycheak ac=new Accuracycheak();
	            photo = ac.getPreview(selectedImagePath);
	            if(photo==null)
	            {
	            	 img.setImageResource(R.drawable.defaultcontactimage);
	            }
	            else{
	            img.setImageBitmap(photo);
	            }
	            
	        }
	    }
}
public String getPath(Uri uri)
{
    String[] projection = { MediaStore.Images.Media.DATA };
    Cursor cursor = managedQuery(uri, projection, null, null, null);
    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    cursor.moveToFirst();
   
    return cursor.getString(column_index);
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_contact, menu);
		return true;
	}
	
	

}
