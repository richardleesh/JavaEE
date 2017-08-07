package ca.seneca.jee.jpa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer extends Person {

	@OneToMany(targetEntity=Order.class, mappedBy = "customer",  cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Order> orderList = new ArrayList<Order>();
	

	@OneToOne
	@JoinColumn(name = "Profile_ID")
	private CustomerProfile customerProfile;

	private String userName;	
	
	
	
	public CustomerProfile getCustomerProfile() {
		return customerProfile;
	}
	public void setCustomerProfile(CustomerProfile customerProfile) {
		this.customerProfile = customerProfile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
}
