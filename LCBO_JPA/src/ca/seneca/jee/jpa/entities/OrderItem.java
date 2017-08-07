package ca.seneca.jee.jpa.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.*;


@Entity
public class OrderItem {

	@Id
	@GeneratedValue
	private int id;

	@OneToOne
	@JoinColumn(name = "Product_ID")
	private Product product;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "Order_ID")
	private Order order;	
	

	private int quantity;

	
	

	public OrderItem(Product product, int quantity) {

		this.product = product;
		this.quantity = quantity;
	
	}

	public OrderItem() {

	}

	public String getProductName() {
		return product.getName();
	}

	public void setProductName(String productName) {
		product.setName(productName);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return product.getPrice();
	}

	public void setPrice(double price) {
		product.setPrice(price);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
