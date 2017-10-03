package com.transilvania.dto;

import java.util.List;



public class ParkingDTO {
	

	private Integer id_parking;

    private List<CategoryDTO> categories; 
	
	private String name;
	
	private Double lat;
	
	private Double lng;
	
	private String description;
	
	private String image;
	
	private Integer arriveTime;
	
	private Boolean creditCard;

	private Integer totalPlaces;
	
	private Integer busyPlaces;
	
	private Integer totalReservations;
	
	
	
	public Integer getTotalReservations() {
		return totalReservations;
	}

	public void setTotalReservations(Integer totalReservaions) {
		this.totalReservations = totalReservaions;
	}

	public Integer getBusyPlaces() {
		return busyPlaces;
	}

	public void setBusyPlaces(Integer busyPlaces) {
		this.busyPlaces = busyPlaces;
	}

	public Integer getTotalPlaces() {
		return totalPlaces;
	}

	public void setTotalPlaces(Integer totalPlaces) {
		this.totalPlaces = totalPlaces;
	}

	public Integer getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Integer arriveTime) {
		this.arriveTime = arriveTime;
	}

	public Boolean getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(Boolean creditCard) {
		this.creditCard = creditCard;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getId_parking() {
		return id_parking;
	}

	public void setId_parking(Integer id_parking) {
		this.id_parking = id_parking;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
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



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
	
	
	@Override
	public String toString() {
		return "ParkingDTO [id_parking=" + id_parking + ", categories=" + categories + ", name=" + name + ", lat=" + lat
				+ ", lng=" + lng + ", description=" + description + ", image=" + image + ", arriveTime=" + arriveTime
				+ ", creditCard=" + creditCard + ", totalPlaces=" + totalPlaces + ", busyPlaces=" + busyPlaces
				+ ", totalReservaions=" + totalReservations + "]";
	}
}
