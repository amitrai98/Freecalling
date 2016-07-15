package com.example.freecalling.db;


public interface IE {
	
	public static final String CONTACT_LIST_TABLE="contactlist";
	

	public static final String create_freecallingapp_table="create table freecallingapptable(deviceid text primary key,myno text not null,myname text not null,email text not null,ip text not null,regstate text not null,regdate text,lastupdate text);";
	public static final String create_id_table="create table id(contactno text primary key,deviceid text,ip text,datee String);";
	public static final String create_contactlist_table="create table contactlist(contactno text primary key,contactname text,email text,image text,datee datetime);";
	public static final String create_log_table="create table log(rowid integer primary key autoincrement,contactno text not null,type text not null,duration text,datee datetime);";
	public static final String create_sms_table="create table sms(rowid integer primary key autoincrement,contactno text not null, message text not null,datee text,type text not null,status text);";
	public static final String create_gps_table="create table gps(latitude float,longitude float,datee String,contactno String,securityflag boolean);";
	public static final String create_block_table="create table block(contactno text primary key,dateofblock datetime);";
	
	
	
	
	public static String DB_NAME = "freecalling";
	public static int DB_VERSION = 1;
	
	public static String REGISTRATION_TABLE = "freecalling_register";
	
	public static String USER_NAME = "username";
	public static String PASSWORD = "password";
	public static String CONTATCT_NO = "contatctno";
	public static String NAME = "name";
	public static String EMAIL = "email";
	
	/**DATA USED IN REGISTRATION TABLE **/
	public static String MY_DEVICE_ID = "deviceid";
	public static String MY_NO = "myno";
	public static String MY_NAME = "myname";
	public static String MY_EMAIL = "email";
	public static String MY_IP = "ip";
	public static String REGISTRATION_STATE = "regstate";
	public static String REGISTRATION_DATE = "regdate";
	public static String LAST_UPDATE = "lastupdate";
	public static String MY_DOB = "dob";
	public static String MY_GENDER = "gender";
	
	/**Table list**/
	

}
