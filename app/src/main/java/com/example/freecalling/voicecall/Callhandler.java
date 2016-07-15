package com.example.freecalling.voicecall;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;

import com.example.freecalling.db.Db;

public class Callhandler {
	String caller,callerdeviceid,callerip,callingto,calltype;
	Context ctx;
	public String voiceCall(String calltype,String caller,String callerdeviceid,String callerip,String callingto){
		this.calltype=calltype;
		this.caller=caller;
		this.callerdeviceid=callerdeviceid;
		this.callerip=callerip;
		this.callingto=callingto;
		String status="sorry";
		//check for the no if it is in the block list if not then go ahead
			if(calltype.equals("voicecall")){
				
			}
			if(calltype.equals("videocall")){
				
			}
			if(calltype.equals("sms")){
		
			}
			if(calltype.equals("chat")){
				
			}
			if(calltype.equals("sharelocation")){
				
			}
			
			//else if the no in in the block list return status as you have been blocked 
		return status;
	}
	public String voicecall(String caller,String callerdeviceid,String callerip,String callingto,Context ctx){
		String status="notringing";
		String callerno=caller;
		String incallerdeviceid=callerdeviceid;
		String incallerip=callerip;
		String incallingto=callingto;
		Calendar cd=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentdate=sdf.format(new Date());
		this.ctx=ctx;
		Db link=new Db(ctx);
		
		/*if(link.cheakblock(callerno,incallerip,incallerdeviceid,currentdate).equals("blocked"))
		{
			
			System.out.println(link.logintery(callerno, "voicecall", "2", currentdate));
		}
		else{
			return "blocked";
		}*/
		
		return status;
	}

}
