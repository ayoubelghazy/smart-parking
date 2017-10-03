package com.transilvania.dto;

public class ParkingAdminDTO {

	private Integer id;
	private String name;
	private String email;
	private String surname;
	private String password;
	private String img;
	
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "ParkingAdminDTO [id=" + id + ", name=" + name + ", email=" + email + ", surname=" + surname
				+ ", password=" + password + "]";
	}
	
	
}
