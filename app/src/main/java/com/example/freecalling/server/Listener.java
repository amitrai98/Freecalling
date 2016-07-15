package com.example.freecalling.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;

import com.example.freecalling.R;

public class Listener extends Activity {
	private Socket socket;
	private static final int serverport=5000;
	private static String serverip;

	private String message;
	public void Listener(String message,String ip){
		this.message=message;
		this.serverip=ip;
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listener);
		
		new Thread(new Clientthread()).start();
		try {
			PrintWriter out=new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			out.println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class Clientthread implements Runnable{

		@Override
		public void run() {
			try {
				InetAddress serAddress=InetAddress.getByName(serverip);
				socket=new Socket(serAddress,serverport);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}catch (IOException ea) {
				ea.printStackTrace();
			}
		}
		
	}
	
}
