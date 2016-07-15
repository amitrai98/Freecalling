package com.example.freecalling.voicecall;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Button;

/***
 * a new class is being created to handle media related work
 * @author amit
 *
 */
 public class Audioa {
	MediaPlayer mplayer=null;
	static String logtag="adio record test";
	static MediaRecorder mrecorder;
	String mfilename=null;
	static Context ctx;
	Button play;
	static MediaPlayer mediaplayer;
	
	public static void onRecord()
	{
		
		//startRecording();
		
	}
	Audioa (Context ctx,Button play){
		this.ctx=ctx;
		this.play=play;
	}
	
	
	
	
	public static void stopPlaying(){
		mediaplayer.stop();
		mediaplayer.release();
		mediaplayer=new MediaPlayer();
		
		
	}
	
	public static void startRecording(){
	
		File f;
		Boolean isSDPresent=android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    	if(isSDPresent==true){
    		f=new File(Environment.getExternalStorageDirectory()+File.separator+"ab.mp3");
    		System.out.println("media is available");
    	}
    	else{
    		f=new File(ctx.getFilesDir()+File.separator+"ab.mp3");
    		System.out.println("will be writting on internal storage");
    	}
    	
    	try {
			f.createNewFile();
			System.out.println("file created");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		mrecorder=new MediaRecorder();
		mrecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mrecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mrecorder.setOutputFile(f.getPath());
		mrecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		try {
			mrecorder.prepare();
		} catch (Exception e) {
			Log.e(logtag, "prepare()  failed exception");
		}
		mrecorder.getMaxAmplitude();
		mrecorder.start();
	}
	
	public static void stopRecording(){
		mrecorder.stop();
		mrecorder.reset();
		mrecorder.release();
		mrecorder=null;
		AudioRecord.hintmessage.setText("Click send to transfer your message or click on icon to Record new");
		
	}
public static void mediaplay(final Button play){
		File f;
		try {
			Boolean isSDPresent=android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	    	if(isSDPresent==true){
	    		f=new File(Environment.getExternalStorageDirectory()+File.separator+"ab.mp3");
	    		System.out.println("media is available");
	    	}
	    	else{
	    		f=new File(ctx.getFilesDir()+File.separator+"ab.mp3");
	    		System.out.println("will be writting on internal storage");
	    	}
	    	
		//URI myuri=f.toURI();
		FileInputStream fin=new FileInputStream(f);
		
		System.out.println(f.getPath());
		mediaplayer=new MediaPlayer();
		mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		//"http://vpr.streamguys.net/vpr64.mp3"
			mediaplayer.setDataSource(fin.getFD());
			fin.close();
			mediaplayer.prepareAsync();
			mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mediaplayer.start();
					mediaplayer.setOnCompletionListener(new OnCompletionListener() {
						
						@Override
						public void onCompletion(MediaPlayer mp) {
							play.setText("Play");
							AudioRecord.playing=false;
							
						}
					});
					
				}
			});
		
			
		} catch (IllegalArgumentException e) {
			System.out.println("illegal argument");
			e.printStackTrace();
		} catch (SecurityException e) {
			System.out.println("security");
			e.printStackTrace();
		} catch (IllegalStateException e) {
			System.out.println("illegal state");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("io exception");
			e.printStackTrace();
		}
		
		
	}
	
	
	public void reciveFile(String ip){
		int bytesread;
		int current=0;
		try {
			@SuppressWarnings("resource")
			ServerSocket sc=new ServerSocket(5555);
			while(true){
				System.out.println("waitting for file to recive");
				Socket shock=sc.accept();
				System.out.println("accepted a connection from  "+shock.getInetAddress());
				byte[] mybytearray=new byte[1000000000];
				
				InputStream in=shock.getInputStream();
				FileOutputStream fos=new FileOutputStream("filename.mp3");
				BufferedOutputStream bos=new BufferedOutputStream(fos);
				bytesread=in.read(mybytearray,0,mybytearray.length);
				current=bytesread;
				do{bytesread=in.read(mybytearray,current,(mybytearray.length-current));
				if(bytesread >=0)current+=bytesread;
				}while(bytesread > -1);
				
				bos.write(mybytearray,0,current);
				bos.flush();
				long end=System.currentTimeMillis();
				bos.close();
				
				PrintWriter out=new PrintWriter(shock.getOutputStream(),true);
				out.println(99);
				out.close();
				shock.close();
				
			}
			
			
		} catch (Exception e) { 
			
			e.printStackTrace();
		} 
	}
	
	public void sendFile(){
		try {
			Socket sc1=new Socket("192.168.1.241",5555);
			
			File f=new File("sdcard/filename.mp3");
			System.out.println("Lenth"+f.length());
			byte[] buffer=new byte[(int)f.length()];
			FileInputStream fis=new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			bis.read(buffer,0,buffer.length);
			OutputStream os=sc1.getOutputStream();
			System.out.println("file is being sent");
			os.write(buffer,0,buffer.length);
			bis.close();
			os.flush();
			
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void videoreciver() throws UnknownHostException, IOException{
		ServerSocket ss=new ServerSocket(5555);
		while(true){
		Socket sc=ss.accept();
		new Thread(new Sockethread(sc)).start();
		}
		
	}
	
	public void videobroadcast()
	{
		
	}
	/*public void recivefile(){
		System.out.println("ready to recive");
		boolean acceptmore=true;
		ServerSocket serversocket = null;
		try {
			serversocket = new ServerSocket(4445);
			
			while (acceptmore) {
				
				Socket socket = serversocket.accept();
				
				try {
					File f=new File("/audio.mp3");
					InputStream is=socket.getInputStream();
					
					FileOutputStream fis=new FileOutputStream(new File("new file.mp3"));
					BufferedOutputStream bos=new BufferedOutputStream(fis);
					int buffersize=socket.getReceiveBufferSize();
					int count;
					byte[] bytes=new byte[buffersize];
					
					while((count=is.read(bytes))>0){
						bos.write(bytes,0,count);
					}
					bos.flush();
					bos.close();
					is.close();
					socket.close();
					
				}
						
					 catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
			

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
		
		public Socket socket;
		String message;
		String[] mess;

		public SocketThread(Socket socket) {
			this.socket = socket;
			

		}

		@Override
		public void run() {
			
	}
	
	}*/
	
	public class Sockethread implements Runnable {
	
		private Socket socket;
		String message;
		String[] mess;

		public Sockethread(Socket socket) {
			this.socket = socket;
			

		}

		@Override
		public void run() {
			
			System.out.println("reciving media");
			try {
				ParcelFileDescriptor pfd=ParcelFileDescriptor.fromSocket(socket);
				System.out.println(pfd);
				mediaplayer.setDataSource(pfd.getFileDescriptor());
				mediaplayer.prepareAsync();
				mediaplayer.prepareAsync();
				mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
					
					@Override
					public void onPrepared(MediaPlayer mp) {
						// TODO Auto-generated method stub
						mediaplayer.start();
					}
				});
				
				
			} catch (Exception e) {
				
			}
			
			
		}
	
	}
	
	
 }
	

