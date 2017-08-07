package ca.seneca.jee.jpa.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name="Product.findAll", query="SELECT prod FROM Product prod")
public class Product implements Serializable{
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	private double weight;
	private double price;
	private String imageFileName;
	
	public Product(){
		
	}
	public Product(Product p){
		this.id = p.id;
		this.name = p.name;
		this.weight = p.weight;
		this.price= p.price;
		this.imageFileName = p.imageFileName;	
	}
	public Product(String name, double weight, double price, String imageFileName) {
		this.name = name;
		this.weight = weight;
		this.price= price;
		this.imageFileName = imageFileName;		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
}
