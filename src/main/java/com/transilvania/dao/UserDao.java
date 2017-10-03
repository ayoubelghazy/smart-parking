package com.transilvania.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.transilvania.entities.User;
import com.transilvania.util.EntityManagerPooling;
@Component
public class UserDao {
	
	public void persist(User user)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			em.getTransaction().begin();	
			em.persist(user);
			em.getTransaction().commit();
			em.clear();

		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	public User getUserById(int id){
		User p;
		EntityManager em= EntityManagerPooling.createEntityManager();
		em.clear();
		p=em.createQuery("SELECT e from User e WHERE e.id_normaluser=?1",User.class).setParameter(1,id).getSingleResult();
		System.out.println(p);
		return p;
	}
	
	
	public List<User> getAllUsers() {
		
		EntityManager em= EntityManagerPooling.createEntityManager();
		Query query = em.createQuery("Select e FROM User e");
		List<User> users = query.getResultList();
//		em.close();
//		emf.close();
		return users;
				
}

}
