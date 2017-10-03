package com.transilvania.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.transilvania.entities.Parking;
import com.transilvania.util.EntityManagerPooling;
@Component
public class ParkingDao {
	

	
	public void delete(Integer id){
		EntityManager em= EntityManagerPooling.createEntityManager();
		Parking parking=em.find(Parking.class, id);
		em.getTransaction().begin();	
		em.remove(parking);
		em.getTransaction().commit();
		em.clear();
	
	}
	
	public void persist(Parking park)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			em.getTransaction().begin();	
			em.persist(park);
			em.getTransaction().commit();
			em.clear();
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public Parking getParkById(int id){
		Parking p;
		EntityManager em= EntityManagerPooling.createEntityManager();
		p=em.createQuery("SELECT e from Parking e WHERE e.id_parking=?1",Parking.class).setParameter(1,id).getSingleResult();
		System.out.println(p);
		return p;
	}
	
	
	public List<Parking> getAllParks() {
		
		EntityManager em= EntityManagerPooling.createEntityManager();
		Query query = em.createQuery("Select e FROM Parking e");
		List<Parking> parks = query.getResultList();
		return parks;
				
}
	public void updatePark(Parking park){
		EntityManager em= EntityManagerPooling.createEntityManager();
		Parking p=em.find(Parking.class, park.getId_parking());
		em.getTransaction().begin();
		p.setArrive_time(park.getArrive_time());
		p.setCredit_card(park.getCredit_card());
		p.setDescription(park.getDescription());
		p.setImage(park.getImage());
		p.setName(park.getName());
		em.getTransaction().commit();
		em.clear();
		
	}
	

}
