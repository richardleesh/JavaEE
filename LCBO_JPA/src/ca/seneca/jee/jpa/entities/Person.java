package ca.seneca.jee.jpa.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable{
	
	@Id
	@GeneratedValue
	private int id;
	
	private String firstName;
	private String lastName;
	
	@Temporal(TemporalType.DATE)
	private Date born;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Address_ID")
	private Address address;
	
	
	
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Person() {
		creationDate = new Date();
		address = new Address();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBorn() {
		return born;
	}
	public void setBorn(Date born) {
		this.born = born;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ",firstName=" + firstName + ", lastName=" + lastName + ", born=" + born + ", creationDate="
				+ creationDate + ", address=" + address + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
