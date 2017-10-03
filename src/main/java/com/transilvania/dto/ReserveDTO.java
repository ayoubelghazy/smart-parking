package com.transilvania.dto;

public class ReserveDTO {
	
	private String uniqueCode;
	private Integer arriveTime;
	private Boolean hasReservation;


	public Boolean getHasReservation() {
		return hasReservation;
	}

	public void setHasReservation(Boolean hasReservation) {
		this.hasReservation = hasReservation;
	}

	public Integer getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Integer arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getUniqueCode() {
		return uniqueCode;
	}

	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}


	

}
