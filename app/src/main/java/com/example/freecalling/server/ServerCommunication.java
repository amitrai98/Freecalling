package com.example.freecalling.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCommunication {/*
	private static boolean acceptmore = true;

public ServerCommunication() {
	ServerSocket serversocket = null;
	try {
		serversocket = new ServerSocket(4444);
		
		while (acceptmore) {
			System.out.println("ready to accept");
			Socket socket = serversocket.accept();
			System.out.println("recived a connection");
		

			new Thread(new SocketThread(socket)).start();
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			serversocket.close();
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	}
}


public class SocketThread implements Runnable {
	private Socket socket;
	String message;
	String[] mess;

	public SocketThread(Socket socket) {
		this.socket = socket;
		

	}

	@Override
	public void run() {
		
		//System.out.println(socket.getRemoteSocketAddress().toString()+"  connected to server");
		try {
			ObjectInputStream msg=new ObjectInputStream(socket.getInputStream());
			try {
				mess=(String[])msg.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Server sc=new Server();
			sc.messagereciver(mess);
		} catch (IOException e) {
			System.out.println("unable to fetch message");
		}
		
		
	}
}
*/}
