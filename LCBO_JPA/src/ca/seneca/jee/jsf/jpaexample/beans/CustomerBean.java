package ca.seneca.jee.jsf.jpaexample.beans;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ca.seneca.jee.ejb.service.CustomerService;
import ca.seneca.jee.ejb.service.OrderService;
import ca.seneca.jee.ejb.service.ProductService;
import ca.seneca.jee.jpa.entities.Address;
import ca.seneca.jee.jpa.entities.Customer;
import ca.seneca.jee.jpa.entities.CustomerProfile;
import ca.seneca.jee.jpa.entities.Order;
import ca.seneca.jee.jpa.entities.OrderItem;

import ca.seneca.jee.jpa.entities.Product;

@Named
@SessionScoped
public class CustomerBean implements Serializable {

	private List<Customer> customers = new ArrayList<Customer>();

	@Inject
	private CustomerService customerService;

	@Inject
	private OrderService orderService;

	@Inject
	private ProductService productService;

	private Customer currentCustomer = new Customer();
	private Order selectedOrder = new Order();

	@PostConstruct
	public void init() {

		// Do your stuff here.
		populateCustomers();

		// set current customer
		currentCustomer = customerService.getCurrentCustomer();
	}
	
	
	
	public Order getSelectedOrder() {
		return selectedOrder;
	}



	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}



	public void updateSelectedOrder(Order order) {
		selectedOrder = order;
	}
	
	public void deleteOrder(Order order) {
		
		customerService.deleteOrder(order);
		currentCustomer.getOrderList().remove(order);
		selectedOrder = new Order();
		
	}
	

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public void loadAllCustomers() {
		this.customers = customerService.getAllCustomers();

	}

	public String prepareEditProfile() {
		currentCustomer = customerService.getCurrentCustomer();
		return "editProfile?request-redirect=true";
	}

	public void updateProfile() {
		currentCustomer = customerService.updateProfile(currentCustomer);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"your profile is updated", currentCustomer.getFirstName() + "," + currentCustomer.getLastName()));
	}

	public void populateCustomers() {
		Date sampleDate = null;
		;
		try {
			sampleDate = new SimpleDateFormat("dd/MM/yyyy").parse("03/10/1979");
		} catch (ParseException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "DOB in wrong format", "Could not parse date!"));
			return;
		}

		Customer customer = new Customer();
		customer.setBorn(sampleDate);
		customer.setFirstName("YongFu");
		customer.setLastName("Liu");
		customer.setUserName("yong");

		Address address = new Address();
		address.setCity("Toronto");
		address.setCountry("Canada");
		address.setPostalCode("M4G5K6");
		address.setStreet("70 Pond Rd");

		address = customerService.createAddress(address);
		customer.setAddress(address);

		CustomerProfile customerProfile = new CustomerProfile();

		customerProfile.setEmail("yongfuliu@mysenecac.ca");
		customerProfile.setLayout("afternoon");
		customerProfile.setMaxTableItems(5);

		customerProfile = customerService.createCustomerProfile(customerProfile);
		customer.setCustomerProfile(customerProfile);
		customer= customerService.createCustomer(customer);

		// sample product 1
		Product product = new Product();
		product.setImageFileName("image_(17).PNG");
		product.setName("Product_No_1000");
		product.setPrice(3.5);
		product.setWeight(1.5);
		product = productService.createProduct(product);

		// sample product1 2
		Product product1 = new Product();
		product1.setImageFileName("image_(19).PNG");
		product1.setName("product1_No_1001");
		product1.setPrice(3.5);
		product1.setWeight(1.5);
		product1 = productService.createProduct(product1);
		

		
		// order 1 
		Order order = new Order();
		order.setOrderDate(new Date());
		

		OrderItem orderItem1 =new OrderItem(product, 2);

		OrderItem orderItem2 = new OrderItem(product1,5);		

		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(orderItem1);
		orderItems.add(orderItem2);

	
		orderService.createOrder(orderItems,customer);
		
		
		

		Customer customer1 = new Customer();
		try {
			sampleDate = new SimpleDateFormat("dd/MM/yyyy").parse("12/05/1981");
		} catch (ParseException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "DOB in wrong format", "Could not parse date!"));
			return;
		}
		customer1.setBorn(sampleDate);
		customer1.setFirstName("Pourya");
		customer1.setLastName("Harirbafan");
		customer1.setUserName("pourya");

		Address address1 = new Address();
		address1.setCity("London");
		address1.setCountry("Canada");
		address1.setPostalCode("L5B2K1");
		address1.setStreet("1253 Dundas W");

		address1 = customerService.createAddress(address1);
		customer1.setAddress(address1);

		CustomerProfile customerProfile1 = new CustomerProfile();

		customerProfile1.setEmail("pourya@mysenecac.ca");
		customerProfile1.setLayout("afternoon");
		customerProfile1.setMaxTableItems(5);

		customerProfile1 = customerService.createCustomerProfile(customerProfile1);
		customer1.setCustomerProfile(customerProfile1);
		customer1 = customerService.createCustomer(customer1);

		// order 2 
				Order order1 = new Order();
				order1.setOrderDate(new Date());				

				OrderItem orderItem3 =new OrderItem(product, 8);
				OrderItem orderItem4 = new OrderItem(product1,12);		

				List<OrderItem> orderItems1 = new ArrayList<OrderItem>();
				orderItems1.add(orderItem3);
				orderItems1.add(orderItem4);

				orderService.createOrder(orderItems1,customer1);

		loadAllCustomers();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Customer created", "Number of Customers: 2" ));

	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

}
