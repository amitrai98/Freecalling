package com.example.freecalling.db;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.example.freecalling.Accuracycheak;

public class Db implements IE {

	public static final String NAME = "contactname";
	public static final String CONTACT_NO = "contactno";
	public static final String EMAIL = "email";
	public static final String CONTACT_LIST_TABLE = "contactlist";
	public static final String DB_NAME = "freecallingapp";
	public static final int DB_VERSION = 1;

	/**
	 *  REGISTRATION TABLE STRUCTURE.
	 */
	public static final String create_freecallingapp_table = "create table "+IE.REGISTRATION_TABLE+"("
			+ IE.USER_NAME + "text primary key,"
			+ IE.PASSWORD + "text not null,"
			+ IE.MY_NO + "text not null,"
			+ IE.MY_DEVICE_ID + "text not null,"
			+ IE.MY_NAME + "text not null,"
			+ IE.MY_DOB + "text not null,"
			+ IE.MY_EMAIL + "text not null,"
			+ IE.MY_GENDER + "text not null,"
			+ IE.MY_IP + "text not null,"
			+ IE.REGISTRATION_STATE  +  "text not null,"
			+ IE.REGISTRATION_DATE  +  "text,"
			+ IE.LAST_UPDATE + "text);";
	
	
	public static final String create_id_table = "create table id(" +
			"contactno text primary key," 
			+ "deviceid text,"
			+ "ip text,"
			+ "datee String);";
	public static final String create_contactlist_table = "create table contactlist(" +
			"contactno text primary key," +
			"contactname text," +
			"email text," +
			"image text," +
			"datee datetime);";
	public static final String create_log_table = "create table log(" +
			"rowid integer primary key autoincrement," +
			"contactno text not null," +
			"type text not null," +
			"duration text," +
			"datee datetime);";
	public static final String create_sms_table = "create table sms(" +
			"rowid integer primary key autoincrement," +
			"contactno text not null," +
			" message text not null," +
			"datee text," +
			"type text not null," +
			"status text);";
	public static final String create_gps_table = "create table gps(" +
			"latitude float," +
			"longitude float," +
			"datee String," +
			"contactno String," +
			"securityflag boolean);";
	public static final String create_block_table = "create table block(" +
			"contactno text primary key," +
			"dateofblock datetime);";

	Database database;
	Context ctx;
	static SQLiteDatabase db;

	public Db(Context ctx) {

		this.ctx = ctx;
		database = new Database(ctx);
	}

	private static class Database extends SQLiteOpenHelper {

		public Database(Context ctx) {
			super(ctx, DB_NAME, null, DB_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {

				db.execSQL(create_freecallingapp_table);
				db.execSQL(create_id_table);
				db.execSQL(create_contactlist_table);
				db.execSQL(create_log_table);
				db.execSQL(create_sms_table);
				db.execSQL(create_gps_table);
				db.execSQL(create_block_table);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("create_freecallingapp_table");
			onCreate(db);

		}
	}

	public Db open() {
		db = database.getWritableDatabase();
		return this;

	}

	public Db close() {
		db.close();
		return this;
	}

	public long logintery(String contactno, String calltype, String duration,
			String datetime) {
		Log.d("log create called", "" + calltype);
		ContentValues smslogpacket = new ContentValues();
		smslogpacket.put("contactno", contactno);
		smslogpacket.put("type", calltype);
		smslogpacket.put("duration", duration);
		smslogpacket.put("datee", datetime);
		return db.insert("log", null, smslogpacket);

	}

	public Cursor getcontactdetail(String contactno) {
		return db.rawQuery(
				"select contactname,email from contactlist where contactno='"
						+ contactno + "'", null);

	}

	public String findip(String contactno) {
		String status = "ip not available";
		Cursor c = db.rawQuery("select ip from id where contactno='"
				+ contactno + "'", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				status = c.getString(0);
				c.moveToNext();
			}
		}
		return status;
	}

	public Cursor logdata(String contactno) {
		return db.rawQuery(
				"select contactno,type,datee from log where contactno='"
						+ contactno + "'order by datee desc", null);
	}

	public Cursor mycontactlist() {
		return db.rawQuery("select contactno,ip from id", null);
	}

	public Cursor distinctlog() {
		return db.rawQuery(
				"select distinct contactno from log order by datee desc", null);
	}

	public String deletelog(String contactno) {
		String status = "unable to delete";
		// Cursor
		// c=db.rawQuery("DELETE FROM log where contactno='"+contactno+"'",
		// null);
		int c = db.delete("log", "contactno='" + contactno + "'", null);
		if (c > 0) {
			/*
			 * while(!c.isAfterLast()){
			 * 
			 * c.moveToLast(); c.moveToNext();
			 */
			status = "deleted sucessfully";

		}
		return status;
	}

	public String cheakAppStatus(String id) {
		String value = "nothing", status;
		Cursor c = db.rawQuery(
				"select regstate from freecallingapptable where deviceid='"
						+ id + "'", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				value = c.getString(0);
				c.moveToNext();
			}

			if (value.equals("registered")) {
				status = "registered";
			} else {
				status = "notregistered";
			}
		} else {

			status = "notregistered";
		}
		c.close();
		return status;
	}
	
	/**
	 * Fields required for registration
	 * @param username
	 * @param password
	 * @param contactno
	 * @param devid
	 * @param name
	 * @param dob
	 * @param email
	 * @param gender
	 * 
	 * additional fields required
	 * ip 
	 * regtext
	 * regdate
	 * lastupdate
	 * 
	 * @return  status of the registration
	 */

	public String registerApp(
			String username,
			String password,
			String contactno,
			String devid,
			String name,
			String dob,
			String email,
			String gender,
			String ip,
			String regdate
			) {
		open();
		String status = "notregistered";
		ContentValues data = new ContentValues();
		data.put(IE.USER_NAME, username);
		data.put(IE.PASSWORD, password);
		data.put(IE.MY_NO, contactno);
		data.put(IE.MY_DEVICE_ID, devid);
		data.put(IE.MY_NAME, name);
		data.put(IE.MY_DOB, dob);
		data.put(IE.MY_EMAIL, email);
		data.put(IE.MY_GENDER, gender);
		data.put(IE.MY_IP, ip);
		data.put(IE.REGISTRATION_STATE, "registered");
		data.put(IE.REGISTRATION_DATE, regdate);
		data.put(IE.LAST_UPDATE, regdate);
		long a = db.insert(IE.REGISTRATION_TABLE, null, data);
		if (a >= 1) {
			status = "registered";
		}
		close();
		return status;
	}

	public boolean updateLocation(float latitude, float longitude,
			String contactno) {
		Date currentdate = new Date();
		long a;
		boolean value = false;
		final SimpleDateFormat parser = new SimpleDateFormat(
				"dd-MM-yyyy HH:mm:ss");
		ContentValues data = new ContentValues();
		data.put("latitude", latitude);
		data.put("longitude", longitude);
		data.put("datee", parser.format(currentdate));
		data.put("contactno", contactno);
		Cursor c = db.rawQuery("select * from gps", null);
		if (c.moveToFirst()) {
			a = db.update("gps", data, null, null);
		} else {
			a = db.insert("gps", null, data);
		}

		if (a >= 1) {
			Log.e(">>>>>>>>", "location updated " + a);
			value = true;
		} else {
			Log.e(">>>>>>>>", "location not updated");
		}
		return value;
	}

	public String[] showLocation(String contactno) {
		String[] location = new String[4];

		Cursor c = db.rawQuery(
				"select latitude,longitude,datee from gps where contactno='"
						+ contactno + "'", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				float latitude = c.getFloat(0);
				float longitude = c.getFloat(1);
				String datee = c.getString(2);

				location[0] = "ok";
				location[1] = Float.toString(latitude);
				location[2] = Float.toString(longitude);
				location[3] = datee;

				c.moveToLast();
				c.moveToNext();

			}
		}

		return location;
	}

	public String locationSecurity() {
		String value = null;
		Cursor c = db.rawQuery("select securityflag from gps", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				value = c.getString(0);
				c.moveToLast();
				c.moveToNext();

			}
		}
		return value;

	}

	public String recivesms(String senderno, String ip, String devid, String msg) {
		String statuss;

		final SimpleDateFormat parser = new SimpleDateFormat(
				"dd-MM-yyyy HH:mm:ss");
		Date currentdate = new Date();

		Long l;
		String status = null;

		String state = cheakblock(senderno, ip, devid,
				parser.format(currentdate));
		if (state.equals("blocked")) {
			status = "blocked";
			return status;
		} else {
			ContentValues msgpacket = new ContentValues();
			msgpacket.put("contactno", senderno);
			msgpacket.put("message", msg);
			msgpacket.put("datee", parser.format(currentdate));
			msgpacket.put("type", "smsrecived");
			msgpacket.put("status", "recived");
			l = db.insert("sms", null, msgpacket);

			statuss = "smsrecieved";

			if (statuss == "smsrecieved") {
				/*
				 * ContentValues smslogpacket=new ContentValues();
				 * smslogpacket.put("contactno", senderno);
				 * smslogpacket.put("type", "smsrecived");
				 * smslogpacket.put("datee", parser.format(currentdate));
				 * l=db.insert("log", null, smslogpacket);
				 */
				Long l1 = logintery(senderno, "smsrecived", "",
						parser.format(currentdate));

				if (l1 >= 1) {
					status = "logcreated";

				}

				else {
					status = "lognotcreated";
				}
			}
		}
		return status;
		/*
		 * return
		 * db.rawQuery("insert into sms values('"+cno+"','"+message+"','"+
		 * date+"','"+type+"')",null);
		 */
	}

	public String sendSms(String reciverno, String ip, String devid,
			String msg, String typee, String myno, String myip) {
		String type = typee;
		String status = "notsent";
		// System.out.println(this.myno());
		/*
		 * String smspacket[]={"sms",reciverno,myno,msg,myip}; Sendsmsserver
		 * ss=new Sendsmsserver(); ss.msgsender(ip, smspacket);
		 * if(status.equals("sent")){
		 */

		final SimpleDateFormat parser = new SimpleDateFormat(
				"dd-MM-yyyy HH:mm:ss");
		Date currentdate = new Date();
		ContentValues msgpacket = new ContentValues();
		msgpacket.put("contactno", reciverno);
		msgpacket.put("message", msg);
		msgpacket.put("datee", parser.format(currentdate));
		msgpacket.put("type", typee);
		msgpacket.put("status", "pending");
		Long l = db.insert("sms", null, msgpacket);
		status = "smssent";
		if (status == "smssent") {
			Long l1 = logintery(reciverno, type, "", parser.format(currentdate));
			System.out.println(l1);
			if (l1 >= 1) {
				status = "sent";
			}

		}
		return status;
	}

	public String myipudate(String ip) {
		String status = "not updated";
		Accuracycheak ac = new Accuracycheak();
		ContentValues data = new ContentValues();
		data.put("ip", ip);
		long l = db.update("freecallingapptable", data,
				"myno='" + myno() + "'", null);
		if (l > 0) {
			status = "updated";
		}
		return status;
	}

	public String deletemessage(String contactno) {
		String status = "not deleted";
		String contactnumber = "no";
		int a;
		Cursor c = db.rawQuery(
				"select contactno form contactlist where contactno='"
						+ contactno + "'", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				contactnumber = c.getString(0);
				c.moveToLast();
				c.moveToNext();
			}
			a = db.delete("sms", "contactno='" + contactnumber + "'", null);
		} else {

			a = db.delete("sms", "contactno='" + contactno + "'", null);
		}

		if (a > 0) {
			status = "deleted";
		}
		return status;
	}

	public String deleteonemessage(String message) {
		String status = "notdeleted";
		int a = db.delete("sms", "message='" + message + "'", null);
		if (a > 0) {
			status = "deleted";
		}
		return status;
	}

	/*
	 * public String updateip(String cno,String devid,String ip,Date datee) {
	 * String statuss=null; String status=null;
	 * 
	 * Cursor c=db.rawQuery("select datee from id where contactno='"+cno+"'",
	 * null); try{
	 * 
	 * 
	 * if (c.moveToFirst()) { do{ statuss=c.getString(1);
	 * 
	 * }while(c.moveToNext()); c.close(); if(statuss=="nullnull" ) { status=
	 * "updated"; final SimpleDateFormat parser = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); ContentValues idinsert=new
	 * ContentValues(); idinsert.put("", cno); idinsert.put("", devid);
	 * idinsert.put("", ip); idinsert.put("", parser.format(datee));
	 * db.insert("id", null, idinsert);
	 * 
	 * } else { status= "allreadyupdated"; }
	 * 
	 * }
	 * 
	 * } catch(SQLException e) { e.printStackTrace();
	 * 
	 * } return status; }
	 */
	public String cheakblock(String no, String ip, String deviceid,
			String currentdate) {
		String status = null;

		try {
			Cursor c = db.rawQuery(
					"select contactno from block where contactno='" + no + "'",
					null);
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					String data = c.getString(0);

					c.moveToNext();

					cheakid(no, ip, deviceid);
					status = "blocked";
				}
			} else {
				System.out.println(cheakid(no, ip, deviceid));
				status = "notblocked";
			}

			c.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}

	public String cheakblockbyno(String no) {
		String status = null;
		String statuss = null;

		try {
			Cursor c = db.rawQuery(
					"select contactno from block where contactno='" + no + "'",
					null);
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					String data = c.getString(0);

					c.moveToNext();

					status = "blocked";
				}
			} else {

				status = "notblocked";
			}

			c.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

		return status;
	}

	public String myno() {
		String status = "not known";
		Cursor c = db.rawQuery("select myno from freecallingapptable", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				status = c.getString(0);
				c.moveToLast();
				c.moveToNext();
			}
		}
		return status;
	}

	public String myip() {
		String status = "not known";
		Cursor c = db.rawQuery("select ip from freecallingapptable", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				status = c.getString(0);
				c.moveToLast();
				c.moveToNext();
			}
		}
		return status;
	}

	public String myname() {
		String status = "not known";
		Cursor c = db.rawQuery("select myname from freecallingapptable", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				status = c.getString(0);
				c.moveToLast();
				c.moveToNext();
			}
		}
		return status;
	}

	public String blocker(String contactno) {
		Accuracycheak ac = new Accuracycheak();
		String currentdate = ac.date();
		String status = cheakblock(contactno, "", "", currentdate);
		if (status.equals("blocked")) {
			status = "allreadyblocked";
		} else {

			ContentValues blockinsert = new ContentValues();
			blockinsert.put("contactno", contactno);
			blockinsert.put("dateofblock", currentdate);
			Long l = db.insert("block", null, blockinsert);
			if (l > 0) {
				status = "blocked";
			} else {
				status = "errorwhileblocking";
			}
		}
		return status;
	}

	public String addContact(String contactno, String contactname,
			String email, String imageurl, String ip) {
		String status = null;
		final SimpleDateFormat parser = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();

		Cursor c = db.rawQuery(
				"select contactname from contactlist where contactno='"
						+ contactno + "'", null);
		if (c.getCount() > 0) {
			c.moveToFirst();
			while (!c.isAfterLast()) {
				// contactname=c.getString(0);
				c.moveToNext();

			}

			ContentValues newcontact = new ContentValues();
			newcontact.put("contactno", contactno);
			newcontact.put("contactname", contactname);
			newcontact.put("email", email);
			newcontact.put("image", imageurl);
			newcontact.put("datee", parser.format(date));
			int l = db.update("contactlist", newcontact, "contactno='"
					+ contactno + "'", null);

			/*
			 * if(ip!=null && !TextUtils.isEmpty(contactname) &&
			 * contactname!=" " && contactname.length()>1){ ContentValues
			 * newcontac = new ContentValues(); newcontac.put("ip",ip); long
			 * la=db.update("id", newcontac, "contactno='"+contactno+"'", null);
			 * if(la>0){}else{db.insert("id", null, newcontac);}
			 * 
			 * }
			 */

			if (l >= 1) {
				status = "updated";
			} else {
				status = "notupdated";
			}

		}

		else

		{

			ContentValues newcontact = new ContentValues();
			newcontact.put("contactno", contactno);
			newcontact.put("contactname", contactname);
			newcontact.put("email", email);
			newcontact.put("image", imageurl);
			newcontact.put("datee", parser.format(date));
			Long l = db.insert("contactlist", null, newcontact);

			/*
			 * if(ip!=null && !TextUtils.isEmpty(contactname) &&
			 * contactname!=" " && contactname.length()>1){ ContentValues ipadd
			 * = new ContentValues(); ipadd.put("ip",ip); db.insert("id", null,
			 * ipadd);
			 * 
			 * }
			 */
			if (l >= 1) {
				status = "contactcreated";
			} else
				status = "contactnotcreated";
		}

		c.close();
		return status;
	}

	public String deleteContact(String contactno) {
		String status = "notdeleted";
		int i = db.delete("contactlist", "contactno='" + contactno + "'", null);
		if (i >= 1) {
			status = "deleted";
		}
		return status;
	}

	public String deleteAllContact() {
		String status = "notdeleted";
		int i = db.delete("contactlist", null, null);
		if (i >= 1) {
			status = "delete";
		}
		return status;
	}

	public Cursor distinctmsglog() {
		return db.rawQuery(
				"select distinct contactno from sms order by datee desc", null);
	}

	public Cursor inbox(String contactno) {
		return db.rawQuery(
				"select contactno,status,type,datee from sms where contactno='"
						+ contactno + "' order by datee desc", null);
	}

	public Cursor particularMessage(String no) {
		return db.rawQuery(
				"select message,datee,type from sms where contactno ='" + no
						+ "' order by datee asc", null);
	}

	public Cursor getContactNo() {
		return db
				.rawQuery(
						"SELECT contactno from contactlist group by contactname;",
						null);
	}

	public String getName(String no) {
		String name = no;

		Cursor c = db.rawQuery(
				"select contactname from contactlist where contactno ='" + no
						+ "'", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				name = c.getString(0);
				if (TextUtils.isEmpty(name) || name == " ") {
					name = no;
				}
				c.moveToNext();
			}

		}

		c.close();
		return name;
	}

	public String getNo(String name) {
		String no = null;

		Cursor c = db.rawQuery(
				"select contactno from contactlist where contactname ='"
						+ name.trim() + "'", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				no = c.getString(0);
				if (TextUtils.isEmpty(no) || no == " ") {

				} else {
					no = null;
				}
				c.moveToLast();
				c.moveToNext();
			}

		}

		c.close();
		return no;
	}

	public Cursor contactdata(String contactno) {
		return db.rawQuery(
				"select contactname,image,email from contactlist where contactno='"
						+ contactno + "'order by contactname ", null);
	}

	public String ipaddress(String contactno) {
		String status = "noip";

		Cursor c = db.rawQuery("select ip from id where contactno='"
				+ contactno + "'", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				status = c.getString(0);
				c.moveToLast();
				c.moveToNext();
			}
		}
		return status;
	}

	public String image(String contactno) {
		String status = null;

		Cursor c = db.rawQuery(
				"select image from contactlist where contactno='" + contactno
						+ "'", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				status = c.getString(0);
				c.moveToLast();
				c.moveToNext();
			}

		}
		c.close();
		return status;
	}

	public String deletesms(String contactno) {
		String cno = contactno;
		String status = "null";
		try {
			int l = db.delete("sms", "contactno='" + cno + "'", null);
			if (l >= 1) {

				status = l + " messages deleted";

			} else {
				status = "notdeleted";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	public String unblocker(String contactno, String currentdate) {

		String status = cheakblock(contactno, "", "", currentdate);
		if (status.equals("blocked")) {

			Cursor c = db.rawQuery("delete from block where contactno='"
					+ contactno + "'", null);
			if (c.moveToFirst()) {
				while (!c.isAfterLast()) {
					String data = c.getString(0);

					c.moveToNext();

					status = "unblockedsucessfully";
				}
			} else {

				status = "errorwhileunblock";
			}

		} else {
			status = "notblocked";
		}
		return status;
	}

	public String cheakid(String no, String ip, String devid) {// checks if
																// contact
																// exists
		String status = "error";
		Accuracycheak ac = new Accuracycheak();
		Cursor c = db.rawQuery("select contactno from id where contactno='"
				+ no + "'", null);
		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				String data = c.getString(0);

				c.moveToNext();

				ContentValues idinsert = new ContentValues();

				idinsert.put("deviceid", devid);
				idinsert.put("ip", ip);
				idinsert.put("datee", ac.date());
				db.update("id", idinsert, "contactno='" + no + "'", null);
				status = "ipupdated";
			}
		} else {

			ContentValues idinsert = new ContentValues();
			idinsert.put("contactno", no);
			idinsert.put("deviceid", devid);
			idinsert.put("ip", ip);
			idinsert.put("datee", ac.date());
			db.insert("id", null, idinsert);
			status = "idcreated";
		}
		c.close();
		return status;
	}

	/*
	 * public void requestLocation(){ Databasehelper link=new
	 * Databasehelper(ctx); try { link.open(); Location la=link.showLocation();
	 * if(la!=null){ float latitude=la.getLatitude(); float
	 * longitude=la.getLongitude(); String date=la.getDate(); Log.e("latitude",
	 * ""+latitude); Log.e("longitude", ""+longitude); Log.e("date", ""+date); }
	 * else{ Log.e(">>>>>>>>>>", "location is null"); }
	 * 
	 * } catch (Exception e) { Log.e(">>>>>>>>>>", "error"); } finally{
	 * link.close(); } }
	 */

}
