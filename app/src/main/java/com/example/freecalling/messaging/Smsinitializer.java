package com.example.freecalling.messaging;


public class Smsinitializer {
	String image, name, status, type,contactno;
	String date;
	public Smsinitializer(String contactno,String image,String name,String status,String type,String date)
	{
		this.contactno=contactno;
		this.image=image;
		this.name=name;
		this.status=status;
		this.type=type;
		this.date=date;
	}
	@Override
	public String toString() {
		return "Smsinitializer [image=" + image + ", name=" + name
				+ ", status=" + status + ", type=" + type + ", date=" + date
				+ "]";
	}
	public String getDate()
	{
		return date;
	}
	public String getImage()
	{
		return image;
	}
	public String getName()
	{
		return name;
	}
	public String getStatus()
	{
		return status;
	}
	
	public String getType()
	{
		return type;
	}
	public String getNumber()
	{
		return contactno;
	}
	
}
