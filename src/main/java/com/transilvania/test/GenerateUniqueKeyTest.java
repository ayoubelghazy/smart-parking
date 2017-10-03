package com.transilvania.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class GenerateUniqueKeyTest {

	@Test
	public void test() {
		String reservationCode="0F32DVC";  
		System.out.println(reservationCode.hashCode());
		reservationCode=reservationCode+reservationCode.hashCode();
		System.out.println(reservationCode);

	}

}
