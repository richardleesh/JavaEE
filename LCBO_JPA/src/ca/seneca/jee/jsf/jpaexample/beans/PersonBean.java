package ca.seneca.jee.jsf.jpaexample.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ca.seneca.jee.ejb.service.PersonService;
import ca.seneca.jee.jpa.entities.Address;
import ca.seneca.jee.jpa.entities.Person;

@Named
@SessionScoped
public class PersonBean implements Serializable {

	@Inject
	private PersonService personService;

	private Person currentPerson = new Person();
	
	public void initCurrentPerson(){
		currentPerson = new Person();
	}
	
	

	public Person getCurrentPerson() {
		return currentPerson;
	}

	public void setCurrentPerson(Person currentPerson) {
		this.currentPerson = currentPerson;
	}

	private List<Person> allPersons = new ArrayList<>();

	
	public String redirectToCreatePerson(){
		currentPerson = new Person();
		return "createPerson?faces-redirect=true";
	}
	
	public String populatePerson() {

		Person person = new Person();
		person.setBorn(new Date());
		person.setFirstName("Sandra");
		person.setLastName("Polch");

		Address address = new Address();
		address.setCity("Toronto");
		address.setCountry("Canada");
		address.setPostalCode("L3B2B5");
		address.setStreet("2142 Dundas Street West");

		person.setAddress(address);

		Person createdPerson = personService.createPerson(person);

		allPersons.add(createdPerson);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Person created", "ID: " + person.getId()));

		return null;
	}
	
	public String deletePerson() {
		
		Person deletedPerson = personService.deletePerson(currentPerson);
		
		loadAllPersons();
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Person deleted", "ID: " + deletedPerson.getId()));
		
		return null;
		
	}

	public String createPerson() {

		Person createdPerson = personService.createPerson(currentPerson);

		loadAllPersons();

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Person created", "ID: " + createdPerson.getId()));

		return null;
	}

	public String loadAllPersons() {
		this.allPersons = personService.getAllPersons();
		return null;
	}

	public List<Person> getAllPersons() {
		return allPersons;
	}

	public void setAllPersons(List<Person> allPersons) {
		this.allPersons = allPersons;
	}

}
