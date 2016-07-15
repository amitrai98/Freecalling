package com.example.freecalling;

import android.util.Log;


public class Location{
	float longitude;
	float latitude;
	String date;
	
Location(float latitude,float longitude,String date){
	latitude=this.latitude;
	longitude=this.longitude;
	date=this.date;
}

public float getLongitude() {
	return longitude;
}

public void setLongitude(float longitude) {
	this.longitude = longitude;
}

public float getLatitude() {
	return latitude;
}

public void setLatitude(float latitude) {
	this.latitude = latitude;
}

public String getDate() {
	return date;
}

public void setDate(String date) {
	this.date = date;
}


	

}
