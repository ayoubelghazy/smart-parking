package com.transilvania.test;

import org.junit.Test;

import com.transilvania.dao.ParkingDao;
import com.transilvania.dao.UserDao;
import com.transilvania.entities.Category;

public class UserDaoTest {

	@Test
	public void test() {
		//UserDao u1 = new UserDao();
		//System.out.println(u1.getAllUsers());
		ParkingDao p = new ParkingDao();	
		System.out.println(p.getParkById(2));
		
//		for(Category c : p.getParkById(1).getCategories())
//		{
//			System.out.println("r : " + c.getReservations());
//		}
		
	}

}
	