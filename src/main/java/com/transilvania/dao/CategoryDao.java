package com.transilvania.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.transilvania.entities.Category;
import com.transilvania.entities.ParkingAdmin;
import com.transilvania.util.EntityManagerPooling;
@Component
public class CategoryDao {
	
	public void persist(Category category)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			em.getTransaction().begin();	
			em.persist(category);
			em.getTransaction().commit();
			em.clear();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}
	
	public Category getCategoryById(int id){
		Category category ;
		EntityManager em= EntityManagerPooling.createEntityManager();
		category=em.createQuery("SELECT e from Category e WHERE e.id_category=?1",Category.class).setParameter(1,id).getSingleResult();
		return category;
	}
	
	public void updateBusyPlaces(int id)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			Category category = em.find(Category.class,id);
			em.getTransaction().begin();	
			category.setBusy_places(category.getBusy_places()+1);
			em.getTransaction().commit();
			em.clear();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void updateBusyPlacesExit(int id)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			Category category = em.find(Category.class,id);
			em.getTransaction().begin();	
			category.setBusy_places(category.getBusy_places()-1);
			em.getTransaction().commit();
			em.clear();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void update(int id,int places)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			Category category = em.find(Category.class,id);
			em.getTransaction().begin();

			category.setBusy_places(category.getTotal_places()-places);

			
			em.getTransaction().commit();
			em.clear();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void cancelReserve(int id)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			Category category = em.find(Category.class,id);
			em.getTransaction().begin();	
			category.setBusy_places(category.getBusy_places()-1);
			System.out.println(category.getBusy_places());
			em.getTransaction().commit();
			em.clear();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void adminUpdateCategory(Category category)
	{
		try
		{		
			EntityManager em= EntityManagerPooling.createEntityManager();
			Category categ =em.find(Category.class, category.getId_category());
			
			em.getTransaction().begin();	
			
			categ.setCovered(category.getCovered());
			categ.setMax_height(category.getMax_height());
			categ.setName(category.getName());
			categ.setPrice(category.getPrice());
			
			em.getTransaction().commit();
			em.clear();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void delete(Integer categoryId){
		EntityManager em= EntityManagerPooling.createEntityManager();
		Category cat=em.find(Category.class, categoryId);
		em.getTransaction().begin();
		em.remove(cat);
		em.getTransaction().commit();
		em.clear();
	}
	
	public List<Category> getAllCategories() {
		
		EntityManager em= EntityManagerPooling.createEntityManager();
		Query query = em.createQuery("Select e FROM Category e");
		List<Category> categories = query.getResultList();

		return categories;
				
}

}
