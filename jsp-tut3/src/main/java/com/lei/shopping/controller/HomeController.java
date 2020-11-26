package com.lei.shopping.controller;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lei.shopping.model.entity.Product;
import com.lei.shopping.model.entity.Sale;
import com.lei.shopping.model.entity.SaleDetails;
import com.lei.shopping.model.service.CategoryService;
import com.lei.shopping.model.service.ProductService;
import com.lei.shopping.model.service.SaleService;


@WebServlet(urlPatterns = {"/home",  "/add-to-cart" , },loadOnStartup = 1)
public class HomeController extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	
	private ProductService proService;
	private SaleService salService;
	
	
	@Override
	public void init() throws ServletException {
		EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
		
		
		proService = new ProductService(emf.createEntityManager());
		salService = new SaleService(emf.createEntityManager());
		
		
	}
	

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		

			
			
		 if("/add-to-cart".equals(path)) {
			
			addToCart(req);
		}
		
		 searchProduct(req);
		
		getServletContext().getRequestDispatcher("/views/home.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	String action = req.getParameter("action");
	HttpSession session = req.getSession(true);
	
	if("Clear".equals(action)) {
		session.removeAttribute("cart");;
		
	} else if("Paid".equals(action)) {
		Sale sale = (Sale) session.getAttribute("cart");
		
		if(null != sale && sale.getTotal() >0) {
			salService.save(sale);
			session.invalidate();
		}
	}
	resp.sendRedirect(req.getContextPath().concat("/home"));
	}
	private void addToCart(HttpServletRequest req) {
		
		//find sale object from session
		
		HttpSession session = req.getSession(true);
		Sale sale = (Sale) session.getAttribute("cart");
		
		if(null == sale) {
		 sale = new Sale();
		 session.setAttribute("cart", sale);
		}
		
		String strId = req.getParameter("id");
		int id = Integer.parseInt(strId);
		
		for(SaleDetails d : sale.getDetails()) {
			if(d.getProduct().getId() == id) {
				d.setQuentity(d.getQuentity() +1);
				return;
			}
		}
		
		Product p = proService.findById(id);
		SaleDetails details = new SaleDetails();
		details.setProduct(p);
		details.setQuentity(1);
		sale.addDetails(details);
		
	}

	private void searchProduct(HttpServletRequest req) {
		
		String category = req.getParameter("category");
		String name = req.getParameter("name");
		
		
		req.setAttribute("products", proService.search(category, name));
	}
}
