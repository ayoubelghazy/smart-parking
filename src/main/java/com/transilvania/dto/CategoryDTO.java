package com.transilvania.dto;

import com.transilvania.entities.Parking;

public class CategoryDTO {
	private Integer id_category;

	private Parking park;

	private Double price;

	private Boolean covered;

	private Integer total_places;
	
	private Double max_height;

	private Integer busy_places;

	private Integer reservations;
	
	private String name;
	
	private Integer free_places;
	
	
	
	
	
	public Integer getFree_places() {
		return free_places;
	}

	public void setFree_places(Integer free_places) {
		this.free_places = free_places;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getReservations() {
		return reservations;
	}

	public void setReservations(Integer reservations) {
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

	public Boolean getCovered() {
		return covered;
	}

	public void setCovered(Boolean covered) {
		this.covered = covered;
	}

	public Integer getTotal_places() {
		return total_places;
	}

	public void setTotal_places(Integer total_places) {
		this.total_places = total_places;
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
		return "CategoryDTO [id_category=" + id_category + ", park=" + park + ", price=" + price + ", covered="
				+ covered + ", total_places=" + total_places + ", max_height=" + max_height + ", busy_places="
				+ busy_places + ", reservations=" + reservations + ", name=" + name + ", free_places=" + free_places
				+ "]";
	}


	
	
}
