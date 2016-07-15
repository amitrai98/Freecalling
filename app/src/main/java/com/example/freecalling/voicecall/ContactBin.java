package com.example.freecalling.voicecall;

public class ContactBin {
	String name,image,number,email,ip;
	public ContactBin(String name,String number,String image,String email,String ip){
		this.name=name;
		this.image=image;
		this.number=number;
		this.email=email;
		this.ip=ip;
	}
	
	public String getName()
	{
		return name;
	}
	public String getImage()
	{
		return image;
	}
	public String getNumber()
	{
		return number;
	}
	public String getEmail()
	{
		return email;
	}
	public String getIp()
	{
		return ip;
	}
	@Override
	public String toString() {
		return "ContactBin [name=" + name + ", image=" + image + ", number="
				+ number + ", email=" + email + ", ip=" + ip + "]";
	}
	
}

