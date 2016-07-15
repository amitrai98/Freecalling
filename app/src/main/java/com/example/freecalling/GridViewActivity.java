package com.example.freecalling;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.example.freecalling.db.Db;
import com.example.freecalling.messaging.Smslist;
import com.example.freecalling.server.Server;
import com.example.freecalling.voicecall.Calllist;
import com.example.freecalling.voicecall.Loglist;

public class GridViewActivity extends Activity {

	GridView gridview;
	public static GridViewActivity gridViewActivity;
	static final String[] mainmenu={"status","contacts","sms","log","location","aboutus","help","settings","exit"};
	AlertDialog.Builder builder;
	DialogInterface dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		gridViewActivity=this;
		
		  Intent service=new Intent(GridViewActivity.this,Server.class);
		  this.startService(service);
			
	
		
		gridview=(GridView)findViewById(R.id._gridview1);

		gridview.setAdapter(new Gridadapter(GridViewActivity.this,mainmenu));
		
		
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				TextView itemnam=(TextView)v.findViewById(R.id._contacts_text);
				String itemname=itemnam.getText().toString();
				if(itemname.equals("exit"))
				{
					 builder=new AlertDialog.Builder(GridViewActivity.this);
					builder.setTitle("are you sure you want to exit");
					builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							GridViewActivity.this.finish();
						}
					});
					builder.show();
					
					
					
				}
				if(itemname.equals("status")){
					Intent i=new Intent(GridViewActivity.this,Mystatus.class);
					startActivity(i);
				}
				else if(itemname.equals("sms"))
				{
					Intent i=new Intent(GridViewActivity.this,Smslist.class);
					startActivity(i);
				}
				else if(itemname.equals("contacts")){
					Intent i=new Intent(GridViewActivity.this,Calllist.class);
					startActivity(i);
				}
				else if(itemname.equals("log")){
					Intent i=new Intent(GridViewActivity.this,Loglist.class);
					startActivity(i);
				}
				else if(itemname.equals("location")){
					
					List<String>location=new ArrayList<String>();
					location=getcurrentlocation();
					if(location.get(0).equals("internet not connected")){
						
						new AlertDialog.Builder(GridViewActivity.this)
				        .setMessage("You do not have data connection please enable to show your location")
				        .setCancelable(false)
				        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int id) {

				            	Intent intent=new Intent();
				                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.WirelessSettings"));
				                startActivity(intent);
				            }
				        })
				        .setNegativeButton("No", null)
				        .show();
					}
					else if(location.get(0).equals("gps not enabled")){
						
						new AlertDialog.Builder(GridViewActivity.this)
				        .setMessage("gps is not enabled please enable to see your location")
				        .setCancelable(false)
				        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int id) {
				            	
				            	Intent callGPSSettingIntent = new Intent(
				                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				                startActivity(callGPSSettingIntent);
				                
				            }
				        })
				        .setNegativeButton("No", null)
				        .show();
					}
					else{
						showlocation(location.get(0),location.get(1));
					}
					
				}
				
				else if(itemname.equals("aboutus")){
					Intent i=new Intent(GridViewActivity.this,Mystatus.class);
					startActivity(i);
				}
				else if(itemname.equals("help")){
					Intent i=new Intent(GridViewActivity.this,Mystatus.class);
					startActivity(i);
				}
				else if(itemname.equals("settings")){
					
				}
				
				else if(itemname.equals("settings")){
					
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
        .setMessage("Are you sure you want to exit?")
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                 GridViewActivity.this.finish();
                
            }
        })
        .setNegativeButton("No", null)
        .show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid_view, menu);
		return true;
	}
	
	public void showlocation(String lati,String longi){
		
		float latitude=Float.parseFloat(lati);
		float longitude=Float.parseFloat(longi);

		Accuracycheak ac=new Accuracycheak();
		String connection =ac.dataConncetionCheak(getApplicationContext());
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	   if(connection!="notconnected"){
			
			if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				
		
		if(lati!=null && longi!=null){
			Db db=new Db(getApplicationContext());
			try {
				db.open();
				String contactno=db.myno();
				db.updateLocation(latitude, longitude,contactno);
				String s[]=db.showLocation(db.myno());
				Log.e(">>>>>>>>>>>", s[0]);
				Log.e(">>>>>>>>>>>", s[1]);
				Log.e(">>>>>>>>>>>", s[2]);
				Log.e(">>>>>>>>>>>", s[3]);
				//Toast.makeText(getApplicationContext(), s[2], Toast.LENGTH_LONG).show();
				
			} catch (Exception e) {
				Log.e(">>>>>>>>", "error while updating location database");
				e.printStackTrace();
			}
			finally{
				db.close();
			}
			Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
					Uri.parse("geo:0,0?q="+latitude+","+longitude+" "));
					startActivity(intent);
		} 
		
			}
			else
			{
				new AlertDialog.Builder(GridViewActivity.this)
				.setMessage("Enable GPS to show location")
				.setCancelable(false)
				.setPositiveButton("yes", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent1 = new Intent();
					    intent1.setClassName("com.android.settings",
					                                "com.android.settings.SecuritySettings");
					                GridViewActivity.this.startActivity(intent1);
						
					}
				})
				.setNegativeButton("No", null)
				.show();
			}
			
		}
		else
		{
			new AlertDialog.Builder(GridViewActivity.this)
			.setMessage("Please Enable wifi or data connection to show location")
			.setCancelable(false)
			.setPositiveButton("yes", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
					
				}
			})
			.setNegativeButton("No", null)
			.show();
		}
		
		
						
	}
	
	
	public List<String> getcurrentlocation(){
		List<String>location=new ArrayList<String>();
		
		Accuracycheak ac=new Accuracycheak();
		String connection =ac.dataConncetionCheak(getApplicationContext());
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	   if(connection!="notconnected"){
			
			if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				
				LocationManager myLocationManager;
				LocationListener myLocationListener;
				Float latitude = null,longitude = null;;
				
				 myLocationManager = (LocationManager)getSystemService    
						  (Context.LOCATION_SERVICE);
						               myLocationListener = new LocationListener(){

										@Override
										public void onLocationChanged(
												Location location) {
											Log.d("on provider enablede", "exicuted");
											
										}

										@Override
										public void onProviderDisabled(
												String provider) {
											Log.d("onProviderDisabled", "exicuted");
											
										}

										@Override
										public void onProviderEnabled(
												String provider) {
											Log.d("on provider enablede", "exicuted");
											
										}

										@Override
										public void onStatusChanged(
												String provider,
												int status, Bundle extras) {
											Log.d("onStatusChanged", "exicuted");
											
										}
									};

						          myLocationManager.requestLocationUpdates
						  (LocationManager.GPS_PROVIDER, 1, 1, myLocationListener);

						  //HERE UNCOMMENT
						  
						    try {
						    	  latitude=(float) myLocationManager.getLastKnownLocation
										  (LocationManager.GPS_PROVIDER).getLatitude();
										   longitude=(float) myLocationManager.getLastKnownLocation
										         (LocationManager.GPS_PROVIDER).getLongitude();
										   location.add(latitude.toString());
										   location.add(longitude.toString());
										   
							} catch (Exception e) {
								
								Log.e("error in fetchin latitude and longitude",">>>>>>");
								e.printStackTrace();
							}      
						        
			}
			else{
				location.add("gps not enabled");
			}
	   }
	   else{
		   location.add( "internet not connected");
	   }
		return location;
	}

}
