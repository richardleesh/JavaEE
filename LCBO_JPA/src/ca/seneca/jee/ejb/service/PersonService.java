package ca.seneca.jee.ejb.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ca.seneca.jee.jpa.entities.Person;

/**
 * Session Bean implementation class PersonService
 */
@Stateless
public class PersonService {

	@PersistenceContext(unitName="assignment_2")
	private EntityManager em;
	
    public PersonService() {
    
    }
    
    public Person deletePerson(Person person) {
    	if(person == null) {
    		return null;
    	}

    	em.remove(em.contains(person) ? person : em.merge(person));
    	
    	return person;
    }

    public Person createPerson(Person person) {
    	if(person == null) {
    		return null;
    	}
//    	em.persist(person.getAddress());
    	em.persist(person);
    	
    	return person;
    }
    
    public List<Person> getAllPersons() {
    	TypedQuery<Person> query = em.createNamedQuery("Person.findAll", Person.class);
    	List<Person> results = query.getResultList();
    	return results;
    }
    
}
