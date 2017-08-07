package ca.seneca.jee.ejb.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ca.seneca.jee.jpa.entities.Person;
import ca.seneca.jee.jpa.entities.Product;

/**
 * Session Bean implementation class ProductService
 */

@Stateless
public class ProductService {

	@PersistenceContext(unitName = "assignment_2")
	private EntityManager em;

	public ProductService() {
		super();
	}

	
	
	public Product createProduct(Product product) {
		if (product == null) {
			return null;
		}
		// em.persist(product.getAddress());
		em.persist(product);

		return product;
	}

	public List<Product> getAllProducts() {
		TypedQuery<Product> query = em.createNamedQuery("Product.findAll", Product.class);
		List<Product> results = query.getResultList();
		return results;
	}

}
