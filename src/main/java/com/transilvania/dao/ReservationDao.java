package com.transilvania.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.transilvania.entities.Category;
import com.transilvania.entities.Parking;
import com.transilvania.entities.Reservation;
import com.transilvania.util.EntityManagerPooling;
@Component
public class ReservationDao {
	
	public void persist(Reservation reservation)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			em.getTransaction().begin();	
			em.persist(reservation);
			em.getTransaction().commit();
			em.clear();
	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	public void delete(int id)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			em.clear();
			Reservation reservation = em.find(Reservation.class,id);
			em.getTransaction().begin();			
			em.remove(reservation);;		
			em.getTransaction().commit();
			em.clear();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public List<Reservation> getAllReservations() {
		
		EntityManager em= EntityManagerPooling.createEntityManager();
		Query query = em.createQuery("Select e FROM Reservation e");
		List<Reservation> reservations = query.getResultList();
//		em.close();
//		emf.close();
		return reservations;
				
}

}
