package com.example.freecalling.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.example.freecalling.Accuracycheak;
import com.example.freecalling.GridViewActivity;
import com.example.freecalling.R;
import com.example.freecalling.Staticactivity;
import com.example.freecalling.db.Db;
import com.example.freecalling.messaging.Smslist;
import com.example.freecalling.voicecall.AudioRecord;
import com.example.freecalling.voicecall.Audioa;
import com.example.freecalling.voicecall.Call;
import com.example.freecalling.voicecall.Someonecalling;





public class Server extends Service {
	Myreciver myreciver;
	Mycallcontroler mycallcontroler;
	String serverstatus = "not running";
	socketBean bean;
	String myno;
	public static Socket socket;
	public static ObjectOutputStream out;
	static String msgg[];
	static Context ctx;
	static String locationmessage[];
	
	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		myreciver = new Myreciver(new Handler());
		registerReceiver(myreciver, new IntentFilter("call request"));
		
		/*mycallcontroler = new Mycallcontroler(new Handler());
		registerReceiver(myreciver, new IntentFilter("call end request"));
*/

		System.out.println(Staticactivity.isMyServiceRunning(
				getApplicationContext(), "com.example.freecalling.Server"));
		if (Staticactivity.isServerRunning() == false) {
			serverstatus = "running";
			/*System.out.println("service started");
			Toast.makeText(this, "service started", Toast.LENGTH_SHORT).show();
*/
			Log.d("service started", "freecalling");
			try {
				new Thread(new Runnable() {

					@Override
					public void run() {
						new ServerCommunication();
					}
				}).start();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}

		}
		return START_STICKY;
	}

	private synchronized void messagereciver(String message[]) {

		Db db1 = new Db(getApplicationContext());
		db1.open();

		if (message[1].equals(db1.myno())) {

			db1.recivesms(message[2], message[4], message[1], message[3]);

		}

		else {
			message = null;
		}

		message = null;
		db1.close();
	}

	public void ipupdaterecived(String[] message) {
		Db db2 = new Db(getApplicationContext());

		try {

			db2.open();

			if (message[1].equals(db2.myno())) {

				db2.cheakid(message[2], message[3], message[4]);
				

			}

			else {
				message = null;
			}

			message = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db2.close();
		}

	}

	public class ServerCommunication {

		private boolean acceptmore = true;

		public ServerCommunication() {

			Db db = new Db(getApplicationContext());
			try {
				db.open();
				myno = db.myno();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
			ctx=getApplicationContext();
			ServerSocket serversocket = null;
			try {
				serversocket = new ServerSocket(4444);
				
				while (acceptmore) {
					String mess[] = null;
					System.out
							.println("listening for connection  hahahahahahah");
					 socket = serversocket.accept();

					ObjectInputStream msg = null;
					try {
						msg = new ObjectInputStream(socket.getInputStream());
					} catch (StreamCorruptedException e1) {

						e1.printStackTrace();
					} catch (IOException e1) {

						e1.printStackTrace();
					}

					try {
						mess = (String[]) msg.readObject();
						msgg=mess;

						out = new ObjectOutputStream(
								socket.getOutputStream());

						// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
						// + mess[0] + "  " + mess[1] + "  " + mess[2]
						// + mess[3] + "  " + mess[4] + "  " + myno);

						if (mess[0].equals("call request")
								&& mess[1].equals(myno)) {
							
							if(Someonecalling.oncallwith==null){
								/*Server.this
								.sendBroadcast(new Intent("call request"));*/
								Db link=new Db(getApplicationContext());
								Accuracycheak ac=new Accuracycheak();
								try {
									
									link.open();
									String blockstate=link.cheakblock(msgg[2], msgg[3], msgg[4], ac.date());
									Log.e("loglist", blockstate);
									
									if(blockstate.equals("notblocked")){
										Intent i = new Intent(getApplicationContext(),
												Someonecalling.class);
										i.putExtra("callerno", msgg[2]);
										i.putExtra("callerip", msgg[3]);
										i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										startActivity(i);
									}
									else{
										out.writeObject("blocked");
									}
									
								} catch (Exception e) {
									e.printStackTrace();
								}finally{link.close();}
								
								
							
										
							}else
							{
								out.writeObject("busy");
								}

							

						} else if (mess[0].equals("sms")
								&& mess[1].trim().equals(myno.trim())) {
							

							
							Db link=new Db(getApplicationContext());
							Accuracycheak ac=new Accuracycheak();
							try {
								
								link.open();
								String blockstate=link.cheakblock(mess[2], mess[4], mess[5], ac.date());
								
								Log.e("server", blockstate);
								
								if(blockstate.equals("notblocked")){
									messagereciver(mess);
									out.writeObject("recived your message");
									if (Smslist.smslist!=null) {

										/*sendBroadcast(new Intent("updatelist"));
										sendBroadcast(new Intent("updatesmslist"));*/
										
										
									} else {
										sendBroadcast(new Intent("updatesmslist"));
										
										db.open();
										createnotification(db.getName(mess[2]));
										db.close();
									}
									
								}
								else{
									out.writeObject("blocked");
								}
								
							} catch (Exception e) {
								e.printStackTrace();
							}finally{link.close();}
							
						}
//						signature of location packet "show your location",reciverno,myno,myip,mydevid
						 else if (mess[0].equals("show your location")
									&& mess[1].trim().equals(myno.trim())) {
							 
							 Accuracycheak ac=new Accuracycheak();
							 Db link=new Db(getApplicationContext());
							 try {
								link.open();
								String blockstate=link.cheakblock(mess[2], mess[3], mess[4], ac.date());
								String security=link.locationSecurity();
								Log.e("server ",""+security);
								if(blockstate!=null  && blockstate.equals("notblocked")){
									
									if( security==null && security!="on"){
										List<String>location=new ArrayList<String>();
										location=GridViewActivity.gridViewActivity.getcurrentlocation();
										Log.e(""+location.get(0), "");
										if(location.get(0)!=null && location.get(0)!=null && location.get(0)!="gps not enabled" && location.get(0)!="internet not connected"){
											//location reply =current location,reciverno,myno,latitude,longitude,date,ipaddress,deviceid
											String locationupdate[]={"current location",mess[2],myno,location.get(0),location.get(1),ac.date(),ac.ipAddress(),ac.deviceId(getApplicationContext())};
											out.writeObject(locationupdate);
										}
										else if(location.get(0)==null || location.get(1)==null){
											Db db2=new Db(getApplicationContext());
											try {
												db2.open();
												String[] locationdata=db2.showLocation(mess[2]);
												if(locationdata[0]!=null && locationdata[0].equals("ok")){
													String locationupdate[]={"last location",mess[2],myno,locationdata[1],locationdata[2],locationdata[3],ac.ipAddress(),ac.deviceId(getApplicationContext())};
												}
												else{
													out.writeObject("error fetching location data");
												}
											} catch (Exception e) {
												e.printStackTrace();
											}finally {db2.close();}
										}
										/*out.writeObject("error fetching location");*/
									}
									else{
										out.writeObject("user restriction");
									}
									
								}else{
									out.writeObject("blocked");
								}
								
								
							} catch (Exception e) {
								Log.e(">>>>>>>>>>", "error");
								e.printStackTrace();
							}
							 finally{
								 link.close();
							 }

							}
						
						
						
						
						
						
						else if (mess[0].equals("call end request")
								&& mess[1].trim().equals(myno.trim())) {
							
							if((Someonecalling.oncallwith!=null && Someonecalling.oncallwith.equals(mess[2]))){
								
								if(Someonecalling.vb!=null)
								{
									Someonecalling.vb.cancel();
								}
								
								Someonecalling.someonecalling.finish();
									
								}else if(AudioRecord.oncallwith!=null && AudioRecord.oncallwith.equals(mess[2])){
									AudioRecord.audioRecord.finish();
								}
								else if(Call.call!=null){
									Call.call.finish();
								}
							}
							
						
						else if (mess[0].equals("please recive my file")
								&& mess[1].trim().equals(myno.trim()) && mess[2].equals(AudioRecord.oncallwith)) {
							
							if(AudioRecord.audioRecord!=null){
								new Thread(new Runnable() {
									
									@Override
									public void run() {
										abc aa=new abc();
										String state=aa.abcd();
										if(state.equals("file recivied")){

											
											
											if(AudioRecord.recording==false && AudioRecord.state==false && AudioRecord.playing==false && AudioRecord.sending==false){
											//	AudioRecord.play.setText("Stop");
												
												AudioRecord.playing=true;
												try {
													Audioa.mediaplay(AudioRecord.play);
												} catch (Exception e) {
													e.printStackTrace();
												}
												finally{
													//playing=false;
												}
												
											}
											
											else if(AudioRecord.playing==true){
												
												Audioa.stopPlaying();
												AudioRecord.playing=false;
												//AudioRecord.play.setText("Play");
												
												AudioRecord.playing=true;
												try {
													Audioa.mediaplay(AudioRecord.play);
												} catch (Exception e) {
													e.printStackTrace();
												}
												finally{
													//playing=false;
												}
											
												
											}
											else{
												
											}
												
										
										}
									}
								}).start();
							}
							
							else{
								out.writeObject("call has been ended");
							}
							
							/*
							if(Someonecalling.someonecalling!=null || AudioRecord.audioRecord!=null){
								if(Someonecalling.oncallwith.equals(mess[2])){
									
									if(Someonecalling.ctx!=null){
										Someonecalling.someonecalling.finish();
									}
									else if(AudioRecord.ctx!=null){
										AudioRecord.audioRecord.finish();
									}
									
								}
							}*/
							
						} 
						else {
							out.writeObject("not myno");
						}
					} catch (OptionalDataException e) {

						e.printStackTrace();
					} catch (ClassNotFoundException e) {

						e.printStackTrace();
					} catch (IOException e) {

						e.printStackTrace();
					}

					new Thread(new SocketThread(mess)).start();

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
		}

		ArrayList<Socket> list = new ArrayList<Socket>();

		public class SocketThread implements Runnable {
			String[] message;

			public SocketThread(String[] message) {
				this.message = message;

			}

			@Override
			public synchronized void run() {
				syncmethod(message);

			}

		}

		public synchronized void syncmethod(String[] msg) {
			
			Db db = new Db(
					getApplicationContext());
			try{
			if (msg[0].equals("sms")) {/*
				
				Databasehelper link=new Databasehelper(getApplicationContext());
				Accuracycheak ac=new Accuracycheak();
				try {
					
					link.open();
					String blockstate=link.cheakblock(msg[2], msg[4], msg[5], ac.date());
					
					Log.e("server", blockstate);
					
					if(blockstate.equals("notblocked")){
						messagereciver(msg);
					}
					else{
						out.writeObject("blocked");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally{link.close();}
				
				
				if (Smslist.smslist!=null) {

					sendBroadcast(new Intent("updatelist"));
					sendBroadcast(new Intent("updatesmslist"));
					
					
				} else {
					sendBroadcast(new Intent("updatesmslist"));
					
					db.open();
					createnotification(db.getName(msg[2]));
					db.close();
				}
			*/} else if (msg[0].equals("ipbroadcast")) {

				ipupdaterecived(msg);
			} else if (msg[0].equals("pingrequest")) {
				
				
			
			}

		}catch(Exception e){e.printStackTrace();}finally{db.close();}
		}
	}

	
	///////////////////////////////////////////////////////////////////////////
//	signature of location packet "show your location",reciverno,myno,myip,mydevid
	
	
		public static String[] locationrequest(String reciverip,String datapacket[]) {
			String[] locationreply = null;
			String[] mess = null;
			try {
			
			if(reciverip!="noip" && reciverip!=null){
			
			Socket sc=new Socket(reciverip, 4444);
			
			out= new ObjectOutputStream(sc.getOutputStream());
			
			
				out.writeObject(datapacket);
				
				
				ObjectInputStream msg = null;
				try {
					msg = new ObjectInputStream(sc.getInputStream());
				} catch (StreamCorruptedException e1) {

					e1.printStackTrace();
				} catch (IOException e1) {

					e1.printStackTrace();
				}

				try {
					mess = (String[]) msg.readObject();
					msgg=mess;
					}
					catch(Exception e){
						e.printStackTrace();
					}

				
				
				out.close();
				sc.close();
				
				Db db=new Db(ctx);
				Accuracycheak ac=new Accuracycheak();
				try {
					db.open();
					
					//location reply =current location,reciverno,myno,latitude,longitude,date,ipaddress,deviceid
					if(msgg[0].equals("current location")){
						if((db.cheakblock(msgg[2], msgg[6], msgg[7], ac.date())!="blocked")){
							GridViewActivity.gridViewActivity.showlocation(msgg[3], msgg[4]);
						}
						
						
						Log.d(">>>", "update recived");
					}
					else if(mess[0].equals("last location")){
						
						if((db.cheakblock(msgg[2], msgg[6], msgg[7], ac.date())!="blocked")){
							GridViewActivity.gridViewActivity.showlocation(mess[3], mess[4]);
						}
						
						Log.d(""+msgg[0], "update recived");
					}
					else if(msgg[0].equals("error fetching location data")){
						
						Log.d(""+msgg[0], "update recived");
					}
					else if(msgg[0].equals("user restriction")){
						
						Log.d(""+mess[0], "update recived");
					}
					else if(msgg[0].equals("blocked")){
						
						Log.d(""+mess[0], "update recived");
					}
				}
					
				 catch (Exception e) {
					// TODO: handle exception
				}
				
			
			} 
		
			}
			catch (Exception e) {
			e.printStackTrace();
			
		}
			
			
			return locationreply;
	}
		
	
		
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public class Myreciver extends BroadcastReceiver {

		private final Handler handler;

		public Myreciver(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void onReceive(final Context context, final Intent intent) {
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					
					Intent i = new Intent(getApplicationContext(),
							Someonecalling.class);
					i.putExtra("callerno", msgg[2]);
					i.putExtra("callerip", msgg[3]);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);
				}
			});
		}

	}

	public class Mycallcontroler extends BroadcastReceiver {

		private final Handler handler;

		public Mycallcontroler(Handler handler) {
			this.handler = handler;
		}

		@Override
		public void onReceive(final Context context, final Intent intent) {
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					
					
				}
			});
		}

	}
	@SuppressWarnings("deprecation")
	public void createnotification(String notificationmessage) {
		Notification notification = new Notification(
				R.drawable.smsnotification, notificationmessage,
				System.currentTimeMillis());
		Intent i = new Intent(this, Smslist.class);

		PendingIntent currentintent = PendingIntent.getActivity(this, 0, i, 0);

		notification.setLatestEventInfo(this, "New Message",
				notificationmessage, currentintent);
		// Uri
		// sound=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		notification.sound = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		// notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.defaults |= Notification.DEFAULT_ALL;

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

		boolean isScreenOn = pm.isScreenOn();

		if (isScreenOn == false) {

			WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
					| PowerManager.ACQUIRE_CAUSES_WAKEUP
					| PowerManager.ON_AFTER_RELEASE, "MyLock");

			wl.acquire(5000);
			WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
					"MyCpuLock");

			wl_cpu.acquire(5000);
		}

		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// notification.FLAG_AUTO_CANCEL=0;
		NotificationManager nmm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nmm.notify(0, notification);
	}

	public class Socketcontrol {

		public Socketcontrol(Socket socket) {

		}
	}
	
	class abc{
		String abcd()
		{
			String status=null;
			File f;
			ServerSocket serverSocket = null;
			 try {
			        serverSocket = new ServerSocket(5555);
			    } catch (IOException ex) {
			    	ex.printStackTrace();
			       status="Can't setup server on this port number. ";
			    }

			    Socket socket = null;
			    InputStream is = null;
			    FileOutputStream fos = null;
			    BufferedOutputStream bos = null;
			    int bufferSize = 0;
			    try {
					out.writeObject("ok ready to recive your file please send");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    try {
			        socket = serverSocket.accept();
			    } catch (IOException ex) {
			    	ex.printStackTrace();
			        status="Can't accept client connection. ";
			    }

			    try {
			        is = socket.getInputStream();

			        bufferSize = socket.getReceiveBufferSize();
					
			        System.out.println("Buffer size: " + bufferSize);
			    } catch (IOException ex) {
			    	ex.printStackTrace();
			       status="Can't get socket input stream. ";
			    }

			    try {
			    	Boolean isSDPresent=android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
			    	if(isSDPresent==true){
			    		f=new File(Environment.getExternalStorageDirectory()+File.separator+"ab.mp3");
			    	
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
			    status="file recivied";
			    System.out.println(status);
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


