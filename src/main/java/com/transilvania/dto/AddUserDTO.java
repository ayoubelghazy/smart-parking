package com.transilvania.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.transilvania.entities.Reservation;

public class AddUserDTO {
	private Integer id_normaluser;
	
	private String name;
	
	private String surname;
	
	private String mail;
	
	private String password;

	public Integer getId_normaluser() {
		return id_normaluser;
	}

	public void setId_normaluser(Integer id_normaluser) {
		this.id_normaluser = id_normaluser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

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
		return "NormalUserDTO [id_normaluser=" + id_normaluser + ", name=" + name + ", surname=" + surname + ", mail="
				+ mail + ", password=" + password + "]";
	}
	
}
