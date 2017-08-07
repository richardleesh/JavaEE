package ca.seneca.jee.ejb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ca.seneca.jee.jpa.entities.Customer;
import ca.seneca.jee.jpa.entities.Order;
import ca.seneca.jee.jpa.entities.OrderItem;
import ca.seneca.jee.jpa.entities.Person;

/**
 * Session Bean implementation class OrderService
 */

@Stateless
public class OrderService {

	@PersistenceContext(unitName = "assignment_2")
	private EntityManager em;
	
	
	public Order createOrder(Order order) {
		if (order == null) {
			return null;
		}

		em.persist(order);

		return order;
	}
	
	

	public Order createOrder(List<OrderItem> orderItems, Customer c) {
		if (orderItems == null) {
			return null;
		}
		
		Order order = new Order();
		order.setOrderDate(new Date());
		//em.persist(order);
		
		order.setCustomer(c);
		
		
		for(OrderItem oi: orderItems){			
			em.persist(oi);
			oi.setOrder(order);			
		}
	
		em.persist(order);

		return order;
	}
	
	
	public void deleteOrder(Order order){
		// order is detatched , get same order from database
		order.setCustomer(null);
		
		List<OrderItem> orderItems = order.getOrderItems();
		for(OrderItem oi : orderItems){
			oi.setOrder(null);
			oi.setProduct(null);				
			em.remove(em.merge(oi));
		}		
		
		em.remove(em.merge(order) );
		
		
	}


	
}
