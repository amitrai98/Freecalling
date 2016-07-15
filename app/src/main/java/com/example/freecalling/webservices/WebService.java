package com.example.freecalling.webservices;

import android.app.Activity;

public class WebService {
	
	Activity activity;

	public WebService(Activity activity){
		this.activity=activity;
	}
	
//	class Backgroundtask extends AsyncTask<Void, Void, String>{
//
//		private static final String TAG_SUCCESS = "success";
//		ProgressDialog pb=new ProgressDialog(activity);
//		@Override
//		protected void onPreExecute() {
//			
//			super.onPreExecute();
//			
//			pb.setTitle("please wait");
//			pb.setMessage("please wait while we are communicating to our servers");
//			pb.show();
//		}
//		
//		
//		@Override
//		protected String doInBackground(Void... params) {
//			Databasehelper db=new Databasehelper(activity.getApplicationContext());
//			Accuracycheak ac=new Accuracycheak();
//			try {
//				db.open();
//				
//				//////////////////////////////sending it to server
//				
//				 List<NameValuePair> paramslist = new ArrayList<NameValuePair>();
////				 paramslist.add(new BasicNameValuePair("deviceid", devid));
////				 paramslist.add(new BasicNameValuePair("contactno", no));
////				 paramslist.add(new BasicNameValuePair("contactname", name));
////				 paramslist.add(new BasicNameValuePair("emailid", email));
////				 paramslist.add(new BasicNameValuePair("ipaddress", ac.ipAddress()));
////					String url_create_product="http://192.168.59.1/freecalling/registration.php";
//					Jsonparser jsonParser=new Jsonparser();
////					JSONObject json = jsonParser.makeHttpRequest(url_create_product,
////							"POST", paramslist);
////				 
//				 Log.e("resopnse ",""+json.getString("message").toString());
//				 String status=json.getString("message").toString();
//				 if(status.equals("successfully registered")){
//					 state=db.registerApp(devid, no, name, email, ac.ipAddress(), ac.date());
//						Log.d("do in background", ""+state);
//				 }
//		
//				 String TAG_SUCCESS = "success";
//				
//				 try {
//		                int success = json.getInt(TAG_SUCCESS);
//		 
//		                if (success == 1) {
//		                    
//		                   Log.e("response", ""+success);
//		 
//		                    
//		                    finish();
//		                } else {
//		                  
//		                }
//		            } catch (JSONException e) {
//		                e.printStackTrace();
//		            }
//				 
//			
//				
//				////////////////////////////
//				
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			finally{db.close();}
//			return state;
//		}
//		@Override
//		protected void onPostExecute(String result) {
//			if(result=="registered"){
//				pb.dismiss();
//				finish();
//				Intent i=new Intent(Registration.this,GridViewActivity.class);
//				startActivity(i);
//			
//			}
//			else{
//				pb.dismiss();
//				finish();
//			}
//			
//			super.onPostExecute(result);
//		}
//
//	}
}
