package com.transilvania.dto;

public class LoginDTO {
	
	private String mail;
	private String password;
	
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginDTO [mail=" + mail + ", password=" + password + "]";
	}
	
	

}
