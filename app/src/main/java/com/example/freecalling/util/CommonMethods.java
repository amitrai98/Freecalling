package com.example.freecalling.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.TextView;

public class CommonMethods {
	Activity activity;
	String page_name,mobno,email_add,name;
	String namecheaker;
	String nocheaker;
	
	public CommonMethods(Activity activiy){
		this.activity=activiy;
	}

	public boolean isNetworkAvailable(){
		ConnectivityManager conn=(ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo=conn.getActiveNetworkInfo();
		return activeNetworkInfo !=null && activeNetworkInfo.isConnected();
		
	}
	
	public static boolean checkValidity(TextView v,int length){
		if(v.getText()!=null ){
			if(length!=0){
				if( v.getText().toString().length()>=length){
					v.setBackgroundColor(color.white);
					return true;
				}
				else {	
					v.setBackgroundColor(color.black);
					return false;
				}
			}
			else{
				v.setBackgroundColor(color.white);
				return true;
			}	
		}
		v.setBackgroundColor(color.black);
		return false;
	}
	
	

	
		public String noCheak(String mobno)
		{
			String status="invalid";
			boolean noo=PhoneNumberUtils.isGlobalPhoneNumber(mobno);
			if (noo==true && mobno.length()>=10)
			{
				status="validno";
			}
			return status;
		}
		
		
		public static String getDate(){
			String status="errordate";
					Date d=new Date();
					SimpleDateFormat parser=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					status=parser.format(d);
			return status;
		}
		
		
		public String emailCheak(String email)
		{
			String status="invalidemail";
			this.email_add=email;
			boolean b=android.util.Patterns.EMAIL_ADDRESS.matcher(email_add).matches();
			if(b==true)
			{
				status="validemail";
			}
			return status;
		}
		public static String getDeviceId(Context ctx)
		{
			String status="deviderror";
			String devid=Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);
			if(!TextUtils.isEmpty(devid)){
				status=devid;
			}
			return status;
		}
		
		
		
		public String cheakip(String ipaddress)
		{
			String status="invalidip";
			
			boolean b=android.util.Patterns.IP_ADDRESS.matcher(ipaddress).matches();
			if(b==true)
			{
				status="validip";
			}
			return status;
		}
		
		
		/*public String myno(Context ctx)
		{
			Databasehelper db=new Databasehelper(ctx);
			db.open();
			String mycno=db.myno();
			db.close();
		}*/
		/*public String ipAddress() {
			String status="errorfetichingip";
		    try {
		        InetAddress ownip=InetAddress.getLocalHost();
		        status=ownip.getHostAddress();
		        
		    } catch (Exception ex) {
		        ex.getMessage();
		    }
		    return status;
		}*/
		
		public static String getIpAddress(){
			String status="errorfetchingip";
			try{
				for(Enumeration en =NetworkInterface.getNetworkInterfaces();en.hasMoreElements();){
					NetworkInterface intf=(NetworkInterface) en.nextElement();
					for(Enumeration enumipadd=intf.getInetAddresses();enumipadd.hasMoreElements();){
						InetAddress inetadd=(InetAddress) enumipadd.nextElement();
						if(!inetadd.isLoopbackAddress()) {
							status=inetadd.getHostAddress().toString();
							
						}
					}
				}
			}
			catch(Exception e)
			{
				return status;
			}
			return status;
		}
		
		
		
		
		
		
		
		public String wifiCheak(Context ctx){
			Context cntx = null;
			String status="notenabled";
			try{
				WifiManager wifi=(WifiManager)cntx.getSystemService(Context.WIFI_SERVICE);
				 if(wifi.isWifiEnabled())
				 {
					status="enabled"; 
				 }
				 
			}catch(Exception e)
			{
				status="notenabled";
			}
			return status;
		}
		
		
		
		public String dataConncetionCheak(Context ctx){
			String status="notconnected";
			ConnectivityManager cm=(ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo[] netinfo=cm.getAllNetworkInfo();
			for(NetworkInfo ni : netinfo){
				if(ni.getTypeName().equalsIgnoreCase("WIFI") && ni.isConnected()){
					status="connected";
				}
				else if(ni.getTypeName().equalsIgnoreCase("MOBILE") && ni.isConnected()){
					status="mobile connected";
				}
			}
			
			return status;
		}
		public boolean isNameValid(String name)
		{
			System.out.println(name.length());
			if(!TextUtils.isEmpty(name) && name!=" " && name.length()>1){
				return true;
			}
			
				return false;
		}
		
		public String BitMapToString(Bitmap bitmap){
		    ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		    bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
		    byte [] arr=baos.toByteArray();
		    String result=Base64.encodeToString(arr, Base64.DEFAULT);
		    return result;
		}
		
		public Bitmap StringToBitMap(String image){
		       try{
		           byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
		           Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
		           return bitmap;
		         }catch(Exception e){
		           e.getMessage();
		          return null;
		         }
		 }
		
		public Bitmap getPreview(String fileName)
		{
		    File image = new File(fileName);

		    BitmapFactory.Options bounds = new BitmapFactory.Options();
		    bounds.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(image.getPath(), bounds);

		    if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) 
		    {
		        return null;
		    }
		    int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight : bounds.outWidth;
		    BitmapFactory.Options opts = new BitmapFactory.Options();
		    opts.inSampleSize = originalSize / 64;
		    return BitmapFactory.decodeFile(image.getPath(), opts);
		}
		

}
