package com.transilvania.test;

import org.junit.Test;

import com.transilvania.dao.CategoryDao;
import com.transilvania.dao.ParkingDao;
import com.transilvania.entities.Category;
import com.transilvania.entities.Parking;

public class CategoryDaoTest {

	@Test
	public void test() {
		
		CategoryDao c1 = new CategoryDao();
		//System.out.println(c1.getAllCategories());
		System.out.println(c1.getAllCategories().get(0).getPark());

	}

}
