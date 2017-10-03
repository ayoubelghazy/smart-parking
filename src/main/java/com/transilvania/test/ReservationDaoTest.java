package com.transilvania.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Test;

import com.transilvania.dao.ReservationDao;
import com.transilvania.entities.Reservation;

public class ReservationDaoTest {

	@Test
	public void test() {
		

		ReservationDao d1 = new ReservationDao();
		System.out.println(d1.getAllReservations());

	}

}
