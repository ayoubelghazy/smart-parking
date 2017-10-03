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
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Table(name="parking_user")
@Entity
public class ParkingAdmin {
	
	@Id
	@Column(name="id_parkinguser")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_parkingadmin;
	
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="parkingAdmin")
    private List<Parking> parkings;
	
	@Column(name="name")
	private String name;
	
	@Column(name="surname")
	private String surname;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	private String img;
	
	

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<Parking> getParkings() {
		return parkings;
	}

	public void setParkings(List<Parking> parkings) {
		this.parkings = parkings;
	}

	public Integer getId_parkingadmin() {
		return id_parkingadmin;
	}

	public void setId_parkingadmin(Integer id_parkingadmin) {
		this.id_parkingadmin = id_parkingadmin;
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
		return "ParkingAdmin [id_parkingadmin=" + id_parkingadmin + ", name=" + name + ", surname=" + surname
				+ ", email=" + email + ", password=" + password + "]";
	}

}
