package com.transilvania.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;



@Table(name="reservation")
@Entity
public class Reservation {
	
	@Id
	@Column(name="id_Reservation")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id_reservation;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_normaluser",referencedColumnName = "id_normaluser")
	private User user;
	
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_category",referencedColumnName = "id_category")
    private Category category;
	
	@Column(name="reservation_date")
	private Timestamp reservation_date;
	
	@Column(name="reservation_code")
	private String reservation_code;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getId_reservation() {
		return id_reservation;
	}

	public void setId_reservation(Integer id_reservation) {
		this.id_reservation = id_reservation;
	}

	public Timestamp getReservation_date() {
		return reservation_date;
	}

	public void setReservation_date(Timestamp reservation_date) {
		this.reservation_date = reservation_date;
	}

	public String getReservation_code() {
		return reservation_code;
	}

	public void setReservation_code(String reservation_code) {
		this.reservation_code = reservation_code;
	}

	@Override
	public String toString() {
		return "Reservation [id_reservation=" + id_reservation + ", reservation_date=" + reservation_date
				+ ", reservation_code=" + reservation_code + "]";
	}


}
