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
import javax.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Table(name="category")
@Entity
public class Category {
		
	@Id
	@Column(name="id_category")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_category;
	
    @ManyToOne(fetch=FetchType.LAZY )
    @JoinColumn(name="id_parking",referencedColumnName = "id_parking")
    private Parking park;
    
    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="category")
    private List<Reservation> reservations;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="total_places")
	private Integer total_places;
	
	@Column(name="covered")
	private Boolean covered;
	
	@Column(name="max_height")
	private Double max_height;
	
	@Column(name="busy_places")
	private Integer busy_places;

	@Column(name="name")
	private String name;
	
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Reservation> getReservations() {
		return reservations;
	}


	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}


	public Integer getId_category() {
		return id_category;
	}


	public void setId_category(Integer id_category) {
		this.id_category = id_category;
	}


	public Parking getPark() {
		return park;
	}


	public void setPark(Parking park) {
		this.park = park;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Integer getTotal_places() {
		return total_places;
	}


	public void setTotal_places(Integer total_places) {
		this.total_places = total_places;
	}


	public Boolean getCovered() {
		return covered;
	}


	public void setCovered(Boolean covered) {
		this.covered = covered;
	}


	public Double getMax_height() {
		return max_height;
	}


	public void setMax_height(Double max_height) {
		this.max_height = max_height;
	}


	public Integer getBusy_places() {
		return busy_places;
	}


	public void setBusy_places(Integer busy_places) {
		this.busy_places = busy_places;
	}


	@Override
	public String toString() {
		return "Category [id_category=" + id_category + ", price=" + price + ", total_places=" + total_places
				+ ", covered=" + covered + ", max_height=" + max_height + ", busy_places=" + busy_places + ", name="
				+ name + "]";
	}

}
