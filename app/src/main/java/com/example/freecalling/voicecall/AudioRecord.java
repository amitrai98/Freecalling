package com.example.freecalling.voicecall;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freecalling.R;
import com.example.freecalling.db.Db;

public class AudioRecord extends Activity {
	
	static public Button play,endcall;
	static TextView nameoffile,hintmessage;
	Chronometer duration;
	ImageView icon;
	public static boolean state=false;
	public static boolean recording=false;
	public static boolean sending=false;
	String filename;
	public static boolean playing=false;
	static Context ctx;
	Socket socket;
	ObjectOutputStream out;
	public static String oncallwith=null;
	public static AudioRecord audioRecord;
	String recivername,reciverno,reciverip,myno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_audio_record);
		Bundle bdl=getIntent().getExtras();
		recivername = bdl.getString("recivername");
		reciverno=bdl.getString("reciverno");
		reciverip=bdl.getString("reciverip");
		myno=bdl.getString("myno");
		oncallwith=reciverno;	
		audioRecord=this;
		
		play=(Button) findViewById(R.id._play);
		endcall=(Button) findViewById(R.id._send);
		duration=(Chronometer) findViewById(R.id._time);
		nameoffile=(TextView) findViewById(R.id._nameoffile);
		hintmessage=(TextView) findViewById(R.id._hintmessage);
		icon=(ImageView) findViewById(R.id._icon);
		
		ctx=this;
		final Audioa a=new Audioa(ctx,play);
		final Client c=new Client();
		
		endcall.setOnClickListener(new OnClickListener() {
			String mess;
			@Override
			public void onClick(View v) {

				Db db=new Db(getApplicationContext());
				try {
					db.open();
					myno=db.myno();
				//	String myip=db.myip();
				//	reciverip=db.ipaddress(reciverno);
					try {
						Socket sc=new Socket(reciverip, 4444);
						
						out= new ObjectOutputStream(sc.getOutputStream());
						
						String datapacket[]={"call end request",reciverno,myno,reciverip};
						
							
							out.writeObject(datapacket);
							sc.close();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
						
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					db.close();
				}
				finish();
				/*
				
				
				if(recording==false && playing==false && sending == false){
				
				Databasehelper db=new Databasehelper(getApplicationContext());
				try {
					db.open();
					myno=db.myno();
					String myip=db.myip();
					reciverip=db.ipaddress(reciverno);
					Socket sc1=new Socket(reciverip, 4444);
					ObjectOutputStream outt= new ObjectOutputStream(sc1.getOutputStream());
					String datapacket[]={"please recive my file",reciverno,myno,myip};
					outt.writeObject(datapacket);
					 ObjectInputStream in = new ObjectInputStream(sc1.getInputStream());
					 mess = (String) in.readObject();
			         System.out.println(mess);
					sc1.close();
						
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					db.close();
				}
				if(mess.equals("ok ready to recive your file please send")){
					try {
						new Backgroundtask().execute();
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}else if(mess.equals("call has been ended")){
					finish();
				}
				
			}else{
				toaster("please stop playing and recording to send file");
			}
			*/}
		});
		
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
		
				if(recording==false && state==false && playing==false && sending==false){
				play.setText("Stop");
					
					playing=true;
					try {
						Audioa.mediaplay(play);
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally{
						//playing=false;
					}
					
				}
				
				else if(playing==true){
					
					Audioa.stopPlaying();
					playing=false;
					play.setText("Play");
				
					
				}
				else{
					System.out.print(state+"    playing "+playing+"    recording "+recording);
					toaster("Can Not Play Recording Is In Progress");
				}
				
				
				
			
					
			}
		});
		
		icon.setOnClickListener(new OnClickListener() {
			String mess;
			
			@Override
			public void onClick(View v) {
				
				if(recording==false && playing ==false && sending==false){
					
				//	recording=true;
					
				/*	
					
					//////////////////////////////////////////////
					

					while(recording==true){
						Audioa.mediaplay(play);
						Audioa.startRecording();
						try {
							Thread.sleep(1000);
							Audioa.stopRecording();
							Databasehelper db=new Databasehelper(getApplicationContext());
							try {
								db.open();
								myno=db.myno();
								String myip=db.myip();
								reciverip=db.ipaddress(reciverno);
								Socket sc1=new Socket(reciverip, 4444);
								ObjectOutputStream outt= new ObjectOutputStream(sc1.getOutputStream());
								String datapacket[]={"please recive my file",reciverno,myno,myip};
								outt.writeObject(datapacket);
								 ObjectInputStream in = new ObjectInputStream(sc1.getInputStream());
								 mess = (String) in.readObject();
						         System.out.println(mess);
								sc1.close();
									
							} catch (Exception e) {
								e.printStackTrace();
							}finally{
								db.close();
							}
							if(mess.equals("ok ready to recive your file please send")){
								try {
									new Backgroundtask().execute();
								} catch (Exception e) {
									
									e.printStackTrace();
								}
							}else if(mess.equals("call has been ended")){
								finish();
							}
							
						
							
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					
					
					////////////////////////////////////////////	
					
					
					*/
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					
					try {
						duration.setBase(SystemClock.elapsedRealtime());
						recording=true;
						icon.setImageResource(R.drawable.mic2);
						duration.start();
						Audioa.startRecording();
					} catch (Exception e) {
						e.printStackTrace();
						recording=false;
					}finally{
						
					}
					
					
					
				}else{
					
					icon.setImageResource(R.drawable.mic1);
					duration.stop();
					stopRecording();
					recording=false;
					
					
					Db db=new Db(getApplicationContext());
					try {
						db.open();
						myno=db.myno();
						String myip=db.myip();
						reciverip=db.ipaddress(reciverno);
						Socket sc1=new Socket(reciverip, 4444);
						ObjectOutputStream outt= new ObjectOutputStream(sc1.getOutputStream());
						String datapacket[]={"please recive my file",reciverno,myno,myip};
						outt.writeObject(datapacket);
						 ObjectInputStream in = new ObjectInputStream(sc1.getInputStream());
						 mess = (String) in.readObject();
				         System.out.println(mess);
						sc1.close();
							
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						db.close();
					}
					if(mess!=null && mess.equals("ok ready to recive your file please send")){
						try {
							new Backgroundtask().execute();
						} catch (Exception e) {
							
							e.printStackTrace();
						}
					}else if(mess.equals("call has been ended")){
						finish();
					}
					
				
					
				}
				
			}

			private synchronized void startRecording() {
			
				
					SimpleDateFormat sdf=new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
					Calendar c=Calendar.getInstance();
					filename=sdf.format(c.getTime());
					nameoffile.setText(filename);
					hintmessage.setText("Tap Icon To Stop Recording");
					
					try {
						
						Audioa.startRecording();
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally{
						
					}
					
			}

			private synchronized void stopRecording() {
				if(recording==true){
					hintmessage.setText("Tap Icon To Start Recording");
					try {
						Audioa.stopRecording();
					} catch (Exception e) {
						e.printStackTrace();
					}
					finally{
						recording=false;
						state=false;
					}
					
					
					
				}else{
					toaster("No Recording Is In Progress Please Start Recording And Then Press To Stop");
				}
				
				
			}
		});
		
		
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Db db=new Db(getApplicationContext());
		try {
			db.open();
			myno=db.myno();
			String myip=db.myip();
			reciverip=db.ipaddress(reciverno);
			Socket sc1=new Socket(reciverip, 4444);
			ObjectOutputStream outt= new ObjectOutputStream(sc1.getOutputStream());
			String datapacket[]={"call end request",reciverno,myno,myip};
			outt.writeObject(datapacket);
			sc1.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			db.close();
		}
		finish();
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		oncallwith=null;
		audioRecord=null;
		
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
    	
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		oncallwith=null;
		audioRecord=null;
		finish();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.audio_record, menu);
		return true;
	}
	
	public void toaster(String message){
		Toast.makeText(AudioRecord.this, message, Toast.LENGTH_SHORT).show();
	}
	
	
	public class Client {

		/**
		 * @param args the command line arguments
		 * @throws IOException 
		 */
		public String sendmessage() throws IOException{
			String status=null;
		    Socket socket = null;
		    String host =reciverip;
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
	    	
		//URI myuri=f.toURI();
		

		    try {
				socket = new Socket(host, 5555);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    File file = new File(f.getPath());
		    // Get the size of the file
		    long length = file.length();
			System.out.println(length);
			
		    if (length > Integer.MAX_VALUE) {
		        System.out.println("File is too large.");
		    }
		    byte[] bytes = new byte[(int) length];
		    FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    BufferedInputStream bis = new BufferedInputStream(fis);
		    BufferedOutputStream out = null;
			try {
				out = new BufferedOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    int count;
		    try {
		    	 while ((count = bis.read(bytes)) > 0) {
				        out.write(bytes, 0, count);
				    }

				 status="sent";  
			} catch (Exception e) {
				e.printStackTrace();
				status="not sent";
			}
		   finally{ out.flush();
		    out.close();
		    fis.close();
		    bis.close();
		    socket.close();
		    }
		    
		    return status;
		}
		
	}
	
	public class Backgroundtask extends AsyncTask<Void, Void, String>{
		ProgressDialog p=new ProgressDialog(ctx);
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			//endcall.setEnabled(false);
			hintmessage.setText("sending your message");
			
			
		}
		
		
		@Override
		protected String doInBackground(Void... params) {
			String status=null;
			Client c=new Client();
			try {
				
				status=c.sendmessage();
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			return status;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			super.onPostExecute(result);
			System.out.println(result);
			if(result!=null && result.equals("sent")){
			//	endcall.setEnabled(true);
				sending=false;
				hintmessage.setText("messege sent");
			}
			else{
				
			}
		}
		
	}
	
	class abc{
		String sendfile()
		{
			String status=null;
			File f;
			ServerSocket serverSocket = null;
			 try {
			        serverSocket = new ServerSocket(5555);
			    } catch (IOException ex) {
			       status="Can't setup server on this port number. ";
			    }

			    Socket socket = null;
			    InputStream is = null;
			    FileOutputStream fos = null;
			    BufferedOutputStream bos = null;
			    int bufferSize = 0;
				
			    try {
			        socket = serverSocket.accept();
			    } catch (IOException ex) {
			        status="Can't accept client connection. ";
			    }

			    try {
			        is = socket.getInputStream();

			        bufferSize = socket.getReceiveBufferSize();
					
			        System.out.println("Buffer size: " + bufferSize);
			    } catch (IOException ex) {
			       status="Can't get socket input stream. ";
			    }

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
			    	
			    	try {
						f.createNewFile();
						System.out.println("file created");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        fos = new FileOutputStream(f);
			        bos = new BufferedOutputStream(fos);

			    } catch (FileNotFoundException ex) {
			    	ex.printStackTrace();
			        System.out.println("File not found. ");
			    }

			    byte[] bytes = new byte[bufferSize];

			    int count;
			    try {
					
				

			    while ((count = is.read(bytes)) > 0) {
			        bos.write(bytes, 0, count);
			        System.out.println("writting to file");
			    }
			    status="sucessfuly sent";
			    bos.flush();
			    bos.close();
			    is.close();
				
			    socket.close();
				
			    serverSocket.close();
			    } catch (Exception e) {
					e.printStackTrace();
				}
			    
			    return status;
		}
		
	}
	
}
