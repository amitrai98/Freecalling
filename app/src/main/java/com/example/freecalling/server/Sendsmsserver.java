package com.example.freecalling.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.example.freecalling.messaging.Messegecentralbase;
import com.example.freecalling.messaging.Sendsms;

public class Sendsmsserver {
	private static Handler handler;
	String status="default status";
	ObjectOutputStream out;
	Context ctx;
	Socket sc;
	String[] messagepack;
	String mess ;
	String ipaddress;
	public Sendsmsserver(Context ctx){
		this.ctx=ctx;
	}
	@SuppressLint("HandlerLeak")
	public synchronized void msgsender(final String ip, final String[] messagepacket)
	
	{
		
			
		ipaddress=ip;
		messagepack=messagepacket;
		
		try {
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					dbupdateforsmssent backgroundtask=new dbupdateforsmssent();
					backgroundtask.execute();
				}
			}).start();
			
		} catch (Exception e) {
			e.printStackTrace();
			status="notsent";
		}
	}
		
		
	
	class dbupdateforsmssent extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			String result=null;
			try {
				sc=new Socket(ipaddress.toString().trim(), 4444);
				out = new ObjectOutputStream(sc.getOutputStream());
				
	            out.writeObject(messagepack);
	            ObjectInputStream in = new ObjectInputStream(sc.getInputStream());
	            mess = (String) in.readObject();
	           	System.out.println(mess);
				out.close();
				sc.close();
				if( mess.equals("recived your message")){
					result="message sent";
				}
				else{
					result="not sent";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				result="exception";
			}
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			Sendsms.sendsms.pd.dismiss();
			if(result!=null && result.equals("message sent")){
				Messegecentralbase ms=new Messegecentralbase(ctx);
				ms.smsdbupdate(messagepack[1], messagepack[3]);
				ms.toaster(result);
			}
			else if(result!=null && result.equals("not sent"))
			{
				AlertDialog.Builder ab=new AlertDialog.Builder(ctx);
				ab.setTitle("message not sent ");
				ab.setMessage("The no is not on network or the application on the no is not running ");
				ab.setNegativeButton("cancel", null);
				ab.setCancelable(true);
				ab.show();
			}
			else if(result!=null && result.equals("exception"))
			{
				AlertDialog.Builder ab=new AlertDialog.Builder(ctx);
				ab.setTitle("message not sent ");
				ab.setMessage("The no is not on network or the application on the no is not running ");
				ab.setNegativeButton("cancel", null);
				ab.setCancelable(true);
				ab.show();
			}
			super.onPostExecute(result);
		}
		
	}
}
