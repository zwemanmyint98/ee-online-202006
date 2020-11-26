package com.lei.shopping.listeners;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.lei.shopping.model.service.CategoryService;

@WebListener
public class EntityManagerFactoryListener implements ServletContextListener {

   
	private EntityManagerFactory emf;

	
    public void contextDestroyed(ServletContextEvent sce)  { 
    	emf.close();
    }

	
    public void contextInitialized(ServletContextEvent sce)  { 
    	emf= Persistence.createEntityManagerFactory("lei-os");
    	sce.getServletContext().setAttribute("emf",emf);
    	CategoryService catService = new CategoryService(emf.createEntityManager());
    	sce.getServletContext().setAttribute("categories", catService.getAll());
    }
	
}
