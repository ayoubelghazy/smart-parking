package com.transilvania.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Table(name="parking")
@Entity
public class Parking {
	
	@Id
	@Column(name="id_parking")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_parking;
	
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="park")
    private List<Category> categories;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_parkinguser",referencedColumnName = "id_parkinguser")
    private ParkingAdmin parkingAdmin;
    
    @Column(name="arrive_time")
    private Integer arrive_time;
    
    @Column(name="credit_card")
    private Boolean credit_card;
    
	@Column(name="image")
	private String image;
	
	@Column(name="name")
	private String name;
	
	@Column(name="lat")
	private Double lat;
	
	@Column(name="lng")
	private Double lon;
	
	@Column(name="description")
	private String description;

	
	public Integer getArrive_time() {
		return arrive_time;
	}

	public void setArrive_time(Integer arrive_time) {
		this.arrive_time = arrive_time;
	}

	public Boolean getCredit_card() {
		return credit_card;
	}

	public void setCredit_card(Boolean credit_card) {
		this.credit_card = credit_card;
	}

	public ParkingAdmin getParkingAdmin() {
		return parkingAdmin;
	}

	public void setParkingAdmin(ParkingAdmin parkingAdmin) {
		this.parkingAdmin = parkingAdmin;
	}

	public Integer getId_parking() {
		return id_parking;
	}

	public void setId_parking(Integer id_parking) {
		this.id_parking = id_parking;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Parking [id_parking=" + id_parking + ", categories=" + categories + ", parkingAdmin=" + parkingAdmin
				+ ", arrive_time=" + arrive_time + ", credit_card=" + credit_card + ", image=" + image + ", name="
				+ name + ", lat=" + lat + ", lon=" + lon + ", description=" + description + "]";
	}

}
