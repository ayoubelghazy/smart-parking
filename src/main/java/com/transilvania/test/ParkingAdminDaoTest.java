package com.transilvania.test;

import org.junit.Test;

import com.transilvania.dao.ParkingAdminDao;

public class ParkingAdminDaoTest {

	@Test
	public void test() {
		ParkingAdminDao p1 = new ParkingAdminDao();
		System.out.println(p1.getAllParkingAdmins());
	}

}
