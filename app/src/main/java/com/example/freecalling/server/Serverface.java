package com.example.freecalling.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Serverface extends Thread{
	ServerSocket serverSocket;
	Socket server;
	String serverip,rollno,subject,clientip,clientmac,pagename;
	int serverport;
	InputStream in;
	InputStreamReader ina;
	BufferedReader br;
	String statuss="not conneced";
	static int a=1;
	/*Thread t[] = new Thread[100];*/
public Serverface(int port) throws IOException
	{
   serverport=port;
	serverSocket = new ServerSocket(serverport);
	/*Thread t=new Thread();
	t.start();*/
	this.start();
	}

public void run()
{
	
	while(true)
	{
		try
		{
			System.out.println("server is getting started");
			server = serverSocket.accept();
			statuss=getdata();
			System.out.println(statuss);
			replyclient(statuss);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			break;
		}
	}
}

String getdata() throws IOException
{
	in=server.getInputStream();
	ina=new InputStreamReader(in);
	br=new BufferedReader(ina);
	
	String pagename=br.readLine();
	String page=pagename.toString();
	
	return page;
	
}
public void replyclient(String msg) throws IOException
{
	
	String msga=msg;
	try	
	{
		
		PrintWriter pw;
		OutputStream outToServer = server.getOutputStream();
		pw=new PrintWriter(outToServer,true);
		pw.println(msga);
		
	}
	catch(Exception kljj)
	{
					
	}
}

}
