package com.example.freecalling.voicecall;


public class Contactviewbin {
	
	String contactname,contactno,image,email,ipadd;
	public Contactviewbin(String contactname,String contactno,String image,String email,String ipadd) {
		
		this.contactname=contactname;
		this.contactno=contactno;
		this.image=image;
		this.email=email;
		this.ipadd=ipadd;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIpadd() {
		return ipadd;
	}
	public void setIpadd(String ipadd) {
		this.ipadd = ipadd;
	}
	
	

}
