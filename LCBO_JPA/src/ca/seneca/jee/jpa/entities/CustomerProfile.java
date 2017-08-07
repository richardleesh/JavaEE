package ca.seneca.jee.jpa.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CustomerProfile implements Serializable{

	@Id
	@GeneratedValue
	private int id;
	
	@OneToOne(fetch=FetchType.LAZY, mappedBy="address")
	private Customer customer;

	private String layout;
	private int maxTableItems;
	private String email;
	
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public int getMaxTableItems() {
		return maxTableItems;
	}
	public void setMaxTableItems(int maxTableItems) {
		this.maxTableItems = maxTableItems;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
