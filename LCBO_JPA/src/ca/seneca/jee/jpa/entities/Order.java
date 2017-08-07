package ca.seneca.jee.jpa.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="T_order")
public class Order implements Serializable {

	@Id
	@GeneratedValue
	private int id;


	@OneToMany(targetEntity=OrderItem.class, mappedBy="order",cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private List<OrderItem> orderItems =new ArrayList<>();

	private Date orderDate;


	@ManyToOne
	private Customer customer;

	public Order(List<OrderItem> ois, Customer ctr, Date dt ) {

		this.orderItems = ois;
		this.customer = ctr;
		this.orderDate = dt;		
	}

	public Order() {

	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public int getSize() {
		return orderItems.size();
	}
	
	public double getSum() {
		double total=0.0;
		for(OrderItem oi: orderItems){
			total = total + oi.getProduct().getPrice()*oi.getQuantity();
		}
		
		return total;
	}

}
