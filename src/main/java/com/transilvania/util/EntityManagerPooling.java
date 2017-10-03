package com.transilvania.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerPooling {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager[]=new EntityManager[15];
	
	public static EntityManager createEntityManager() {
		EntityManager freeEm = null;
		
			try {
				if(entityManagerFactory==null)
					entityManagerFactory = Persistence.createEntityManagerFactory("SmartCity");
				for(int i=0;i<15;i++){
					if(entityManager[i]!=null){
						if(entityManager[i].getTransaction().isActive()==false)
							return entityManager[i];
					}
				}
				for(int i=0;i<15;i++){
					if(entityManager[i]==null){
						entityManager[i]=entityManagerFactory.createEntityManager();
						freeEm=entityManager[i];
						break;
					}
				}
			} catch (ExceptionInInitializerError e) {
				throw e;
			}
		
		return freeEm;
	}
	
}
