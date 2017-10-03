package com.transilvania.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Table(name="normal_user")
@Entity
public class User {
	
	@Id
	@Column(name="id_normaluser")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_normaluser;
	
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="user")
    private List<Reservation> reservations;
	
	@Column(name="name")
	private String name;
	
	@Column(name="surname")
	private String surname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id_normaluser=" + id_normaluser + ", name=" + name + ", surname=" + surname + ", email=" + email
				+ ", password=" + password + "]";
	}

	
	
	

}
