package com.example.freecalling.server;

import java.io.Serializable;
import java.net.Socket;

public class socketBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1l;
	
	
	private Socket socket;


	public Socket getSocket() {
		return socket;
	}


	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	
	
	
	
	

}
