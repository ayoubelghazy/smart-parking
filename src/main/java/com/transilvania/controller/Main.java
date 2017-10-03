package com.transilvania.controller;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transilvania.dao.ParkingAdminDao;
import com.transilvania.dao.ParkingDao;
import com.transilvania.entities.Category;
import com.transilvania.entities.Parking;

public class Main {

	public static void main(String[] args) throws JsonProcessingException {
		
		System.out.println(getFree(1));

	}
	
	public static int getReservationForParking(int id)
	{
		int totalReservations=0;
		ParkingDao parkingDAO = new ParkingDao();	
		ArrayList<Parking> parkings=(ArrayList<Parking>) parkingDAO.getAllParks();
		System.out.println(parkings);
		for(Parking p : parkings)
		{
			if(p.getId_parking()==id)
			{
				for(Category c : p.getCategories())
				{
					totalReservations+= c.getReservations().size();
				}
			}
		
		}
		System.out.println("t:" + totalReservations);
		return totalReservations;
	}
	
	public static int getFree(int id)
	{
		int freePlaces=0;
		ParkingDao parkingDAO = new ParkingDao();	
		ArrayList<Parking> parkings=(ArrayList<Parking>) parkingDAO.getAllParks();
		System.out.println(parkings);
		for(Parking p : parkings)
		{
			if(p.getId_parking()==id)
			{
				for(Category c : p.getCategories())
				{
					freePlaces+=c.getTotal_places()-c.getBusy_places();
				}
			}
		
		}
		System.out.println("f:" + freePlaces);
		return freePlaces;
	}
	


}
