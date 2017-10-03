package com.transilvania.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.transilvania.entities.ParkingAdmin;
import com.transilvania.entities.User;
import com.transilvania.util.EntityManagerPooling;
@Component
public class ParkingAdminDao {
	
	public void persist(ParkingAdmin parkingAdmin)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			em.getTransaction().begin();	
			em.persist(parkingAdmin);
			em.getTransaction().commit();
			em.clear();
	
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	public ParkingAdmin getParkingAdminByEmail(String email){
		ParkingAdmin admin;
		
		EntityManager em= EntityManagerPooling.createEntityManager();
		try {
			admin=em.createQuery("SELECT e FROM ParkingAdmin e WHERE e.email=?1",ParkingAdmin.class).setParameter(1, email).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;	
		}

		
		return admin;
	}
	public ParkingAdmin getParkingById(int id){
		ParkingAdmin parkingAdmin ;
		EntityManager em= EntityManagerPooling.createEntityManager();
		parkingAdmin=em.createQuery("SELECT e from ParkingAdmin e WHERE e.id_parkingadmin=?1",ParkingAdmin.class).setParameter(1,id).getSingleResult();
		return parkingAdmin;
	}
	
	
	public List<ParkingAdmin> getAllParkingAdmins() {
		
		EntityManager em= EntityManagerPooling.createEntityManager();
		Query query = em.createQuery("Select e FROM ParkingAdmin e");
		List<ParkingAdmin> parkingAdmins = query.getResultList();
//		em.close();
//		emf.close();
		return parkingAdmins;
				
}

}
