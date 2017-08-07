package ca.seneca.jee.jsf.jpaexample.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import ca.seneca.jee.ejb.service.CustomerService;
import ca.seneca.jee.ejb.service.OrderService;
import ca.seneca.jee.ejb.service.PersonService;
import ca.seneca.jee.jpa.entities.Customer;
import ca.seneca.jee.jpa.entities.Order;
import ca.seneca.jee.jpa.entities.OrderItem;
import ca.seneca.jee.jpa.entities.Product;

@Named
@SessionScoped
public class OrderBean implements Serializable {

	@Inject
	private OrderService orderService;
	
	private List<Order> orders = new ArrayList<>();
	private List<OrderItem> currentorderItems = new ArrayList<OrderItem>();
	private Product currentProduct = new Product();

	private Customer currentCustomer;
	private OrderItem currentItem;
	private Order selectedOrder = new Order();

	private int updatingQuatity = 0;

	double currentTotal = 0;
	

	public void deleteOrder(Order order) {
		orderService.deleteOrder(order);		
		
	}

	public void deleteOrderItem(OrderItem item) {
		currentorderItems.remove(item);
	}

	public void updateSelectedOrder(Order order) {
		selectedOrder = order;
	}

	public void updateQuatity(ValueChangeEvent event) {

		updatingQuatity = (int) event.getNewValue();
		System.out.println("Update quatiity - " + updatingQuatity);
	}

	public void updateItem(OrderItem oi) {
		System.out.println("UpdateItem - " + updatingQuatity);
		oi.setQuantity(updatingQuatity);
	}

	public void addToShoppingCart() {
		Product product = new Product(currentProduct);

		long currProdId = product.getId();
		boolean isCurrentProductAlreadInCurrentOrderItems = false;
		for (OrderItem oi : currentorderItems) {
			if (oi.getProduct().getId() == currProdId) {
				isCurrentProductAlreadInCurrentOrderItems = true;
				oi.setQuantity(oi.getQuantity() + 1);
			}
		}
		if (isCurrentProductAlreadInCurrentOrderItems == false) {
			OrderItem currentOrderItem = new OrderItem(product, 1);
			currentorderItems.add(currentOrderItem);
		}
	}

	public void createOrder() {
		if (currentorderItems.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Order is Not created: shopping cart  can not be empty", null));
		} else {
			Order createdOrder = orderService.createOrder(currentorderItems, currentCustomer);

			currentorderItems = new ArrayList<OrderItem>();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Order created", "ID: " + createdOrder.getId()));
		}
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<OrderItem> getCurrentorderItems() {
		return currentorderItems;
	}

	public void setCurrentorderItems(List<OrderItem> currentorderItems) {
		this.currentorderItems = currentorderItems;
	}

	public Product getCurrentProduct() {
		return currentProduct;
	}

	public void setCurrentProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
	}

	public OrderItem getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(OrderItem currentItem) {
		this.currentItem = currentItem;
	}

	public double getCurrentTotal() {
		double total = 0.0;
		for (OrderItem oi : currentorderItems) {
			total = total + oi.getProduct().getPrice() * oi.getQuantity();
		}
		currentTotal = total;
		return currentTotal;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public Order getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public int getUpdatingQuatity() {
		return updatingQuatity;
	}

	public void setUpdatingQuatity(int updatingQuatity) {
		this.updatingQuatity = updatingQuatity;
	}

}
