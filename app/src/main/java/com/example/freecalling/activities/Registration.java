package com.example.freecalling.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freecalling.GridViewActivity;
import com.example.freecalling.R;
import com.example.freecalling.db.Db;
import com.example.freecalling.util.CommonMethods;

public class Registration extends Activity {
	
	private Button btnsave, btncancel;
	
	private TextView txtheader;
	
	private EditText edtuname,  edtpass, edtcnfpass, edtfname,edtlastname,edtemail,edtdob,edtcno;
	
	private RadioGroup radiogender; 
	
	private String ip, deviceid,datee ;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profileinfo);
		init();
		setDimention();
		
	}

	/**
	 * Initializing view elements
	 * 
	 */
	public void init() {
		txtheader = (TextView) findViewById(R.id.txtheader);
		
		edtuname = (EditText) findViewById(R.id.edtusername);
		edtpass = (EditText) findViewById(R.id.edtpass);
		edtcnfpass = (EditText) findViewById(R.id.edtcnfpass);
		edtfname = (EditText) findViewById(R.id.edtfirstname);
		edtlastname = (EditText) findViewById(R.id.edtlastname);
		edtemail = (EditText) findViewById(R.id.edtemail);
		edtdob = (EditText) findViewById(R.id.edtdob);
		edtcno = (EditText) findViewById(R.id.edtmobileno);
		
		radiogender = (RadioGroup) findViewById(R.id.layoutgeder);
		
		btnsave = (Button) findViewById(R.id.btnsave);
		btncancel = (Button) findViewById(R.id.btncancel);
		
		btnsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				saveData();
				registerUser();
//				startActivity(new Intent(Registration.this,GridViewActivity.class));
			}
		});

		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/**
	 * setting the dimension of view elements at runtime.
	 */
	public void setDimention() 
	{
//		DisplayMetrics screen = CommonTasks.getScreen(ProfileInfo.this);
//		int ht =  screen.heightPixels;
//		int wd = screen.widthPixels;
		
	}
	
	
	private boolean registerUser(){
		String status = "notregistered";
		if(edtuname.getText()!=null && edtuname.getText().toString().trim().length()>0){
			if(edtpass.getText()!=null && edtpass.getText().toString().trim().length()>0){
				if(edtcnfpass.getText()!=null && edtcnfpass.getText().toString().trim().length()>0){
					if(edtpass.getText().toString().trim().equals(edtcnfpass.getText().toString().trim())){
						if(edtfname.getText()!=null && edtfname.getText().toString().trim().length()>0){
							if(edtemail.getText()!=null && android.util.Patterns.EMAIL_ADDRESS.matcher(edtemail.getText().toString().trim()).matches()){
								if(edtcno.getText()!=null && edtcno.getText().toString().trim().length()==10){
									
									ip = CommonMethods.getIpAddress();
									if(ip == "errorfetchingip_"){
										Toast.makeText(Registration.this, "Unable to fetch ip address plese connect to wifi.", Toast.LENGTH_LONG).show();
									}else{
										deviceid = CommonMethods.getDeviceId(this);
										if(deviceid == "deviderror"){
											Toast.makeText(Registration.this, "Unable to fetch Device id.", Toast.LENGTH_LONG).show();
										}else{
											datee = CommonMethods.getDate();
											if(datee=="errordate"){
												Toast.makeText(Registration.this, "Please set date and time of the device first.", Toast.LENGTH_LONG).show();
											}else{
												int checked_id = radiogender.getCheckedRadioButtonId();
												String gender = "male";
												if(checked_id == R.id.radiomale)
													gender = "male";
												else
													gender = "female";
												
												Db db = new Db(Registration.this);
												status =db.registerApp(
														edtuname.getText().toString().trim(),
														edtpass.getText().toString().trim(), 
														edtcno.getText().toString().trim(),
														deviceid,
														edtfname.getText().toString().trim()+" "+edtlastname.getText().toString().trim(), 
														edtdob.getText().toString().trim(),
														edtemail.getText().toString().trim(),
														gender,
														ip,
														datee);
												
												if(status == "")
													Toast.makeText(this, "registered", 10).show();
												else
													Log.e("error while saving","");
												
											}
										}
										
									}
									
								}else{
									Toast.makeText(Registration.this, "Please enter a valid 10 digit mobile no.", Toast.LENGTH_LONG).show();
								}
							}else{
								Toast.makeText(Registration.this, "Please enter a valid email address.", Toast.LENGTH_LONG).show();
							}
						}else{
							Toast.makeText(Registration.this, "Name cannot be left blank.", Toast.LENGTH_LONG).show();
						}
					}else{
						Toast.makeText(Registration.this, "Password and Conform password do not match.", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(Registration.this, "Conform Password can not be left blank.", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(Registration.this, "Password can not be left blank.", Toast.LENGTH_LONG).show();
			}
		}else{
			Toast.makeText(Registration.this, "Please select a valid username.", Toast.LENGTH_LONG).show();
		}
		
		if(status == "registered")
			return true;
		else
			return false;
		
	}


}
