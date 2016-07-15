package com.example.freecalling.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.example.freecalling.Accuracycheak;

public class Invokeserver {
	int serverport=6000;
	PrintWriter sendmessage;
	
	Invokeserver(){
		try {
			
			Accuracycheak ac=new Accuracycheak();
			System.out.println(ac.ipAddress());
			ServerSocket sc=new ServerSocket(serverport);
			Socket client =sc.accept();
			
			//send message to client
			try {
				sendmessage = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
				
				BufferedReader response=new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				while (true) {
                    String message = response.readLine();
 
                    if (message != null) {
                        //call the method messageReceived from ServerBoard class
                      System.out.println("message recived");
                    }
                }
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			finally{
				client.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


public interface OnMessageReceived {
    public void messageReceived(String message);
}
}
