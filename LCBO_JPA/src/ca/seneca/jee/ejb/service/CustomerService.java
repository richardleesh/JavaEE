package ca.seneca.jee.ejb.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ca.seneca.jee.jpa.entities.Address;
import ca.seneca.jee.jpa.entities.Customer;
import ca.seneca.jee.jpa.entities.CustomerProfile;
import ca.seneca.jee.jpa.entities.Order;
import ca.seneca.jee.jpa.entities.OrderItem;
import ca.seneca.jee.jsf.jpaexample.beans.ApplicationBean;

/**
 * Session Bean implementation class PersonService
 */
@Stateless
public class CustomerService {

	@PersistenceContext(unitName = "assignment_2")
	private EntityManager em;

	@Inject
	ApplicationBean applicationBean;

	Logger logger = Logger.getLogger(CustomerService.class.getName());

	public Customer updateProfile(Customer c) {
		em.merge(c.getCustomerProfile());
		String customerThemeName = c.getCustomerProfile().getLayout();
		applicationBean.setCurrentThemeName(customerThemeName);
		return c;
	}

	public Customer createCustomer(Customer customer) {
		if (customer == null) {
			return null;
		}
		logger.log(Level.INFO, "About to persist customer: \n" + customer);
		em.persist(customer);

		logger.log(Level.INFO, "persist customer done. ID: " + customer.getId());
		return customer;
	}

	public Address createAddress(Address address) {
		if (address == null) {
			return null;
		}
		logger.log(Level.INFO, "About to persist customer: \n" + address);
		em.persist(address);

		logger.log(Level.INFO, "persist customer done. ID: " + address.getId());
		return address;
	}

	public CustomerProfile createCustomerProfile(CustomerProfile customerProfile) {
		if (customerProfile == null) {
			return null;
		}
		logger.log(Level.INFO, "About to persist customer: \n" + customerProfile);
		em.persist(customerProfile);
		logger.log(Level.INFO, "persist customer done. ID: " + customerProfile.getId());
		return customerProfile;

	}

	public List<Customer> getAllCustomers() {
		TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);
		List<Customer> results = query.getResultList();
		return results;
	}

	public Customer getCustomer(Integer id) {
		return em.find(Customer.class, id);
	}

	public Customer getCurrentCustomer() {

		String userName = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
		Query query = em.createQuery("select c from Customer c where c.userName=:arg1");
		query.setParameter("arg1", userName);

		List<Customer> result = query.getResultList();

		Customer customer = result.get(0);
		for (Order order : customer.getOrderList()) {
			String order_id = "" + order.getId();
			Query query2 = em.createQuery("select oi from OrderItem oi where order_id=:arg1");
			query2.setParameter("arg1", order_id);
			List<OrderItem> orderItemsResult = query2.getResultList();
			order.setOrderItems(orderItemsResult);
		}

		return customer;
	}

	public void deleteOrder(Order order) {

		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem oi : orderItems) {

			oi.setOrder(null);
			oi.setProduct(null);
			em.remove(em.find(OrderItem.class, oi.getId()));
		}

		em.remove(em.merge(order));

	}

}
