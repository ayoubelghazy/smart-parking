package com.transilvania.test;

import org.junit.Test;
import org.springframework.stereotype.Component;

import com.transilvania.dao.ParkingDao;
import com.transilvania.entities.Parking;

public class ParkDaoTest {

	
	@Test
	public void test() {
		ParkingDao p1 = new ParkingDao();	
		System.out.println(p1.getParkById(1));
		
		
	}
	public static void main(String[] args) {
		ParkingDao p=new ParkingDao();
		System.out.println(p.getParkById(1));
	}

}
