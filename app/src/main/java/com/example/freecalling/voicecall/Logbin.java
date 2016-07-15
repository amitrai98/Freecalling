package com.example.freecalling.voicecall;


public class Logbin {
	String contactno,contactname,typeofcall,date,image;
	public Logbin(String contactno,String contactname,String typeofcall,String date,String image)
	{
		this.contactno=contactno;this.contactname=contactname;this.typeofcall=typeofcall;this.date=date;this.image=image;
	}
	public String getContactno()
	{
		return contactno;
	}
	public String getContactname()
	{
		return contactname;
	}
	@Override
	public String toString() {
		return "Logbin [contactno=" + contactno + ", contactname="
				+ contactname + ", typeofcall=" + typeofcall + ", date=" + date
				+ ", image=" + image + "]";
	}
	public String getTypeofcall()
	{
		return typeofcall;
	}
	public String getDate()
	{
		return date;
	}
	public String getImage()
	{
		return image;
	}
	

}
