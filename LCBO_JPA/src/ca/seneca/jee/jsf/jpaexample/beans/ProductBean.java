package ca.seneca.jee.jsf.jpaexample.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.sun.org.apache.xml.internal.security.Init;

import ca.seneca.jee.ejb.service.OrderService;
import ca.seneca.jee.ejb.service.ProductService;
import ca.seneca.jee.jpa.entities.Order;
import ca.seneca.jee.jpa.entities.OrderItem;
import ca.seneca.jee.jpa.entities.Product;

@Named
@SessionScoped
public class ProductBean implements Serializable {

	@Inject
	private ProductService productService;
	
	@Inject 
	OrderService orderService;
	

	private List<Product> allProducts = new ArrayList<>();

	private Product currentProduct = new Product();
	
	@PostConstruct
	public void initProductsTable() {
		// populate product to database
		populateProduct();
		
		//load product to bean
		loadAllProducts();		
	}
	
	public String shopAction(){
		allProducts = productService.getAllProducts();
		return "shopManagement?faces-redirect=true";
	}

	public String populateProduct() {
		Random random = new Random();

		for (int i = 1; i <= 91; i++) {
			

			String imageFileName = "image_(" + i + ").PNG";
			String name = "Product_No_" + i;
			double weight = random.nextInt(10)+0.5;
			double price = random.nextInt(5)+1.0;
			
			Product product = new Product(name, weight, price, imageFileName);

			Product createdProduct = productService.createProduct(product);
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Product created", "ID: " + product.getId()));

			allProducts.add(createdProduct);

		}
		

		return null;
	}
	
	public String loadAllProducts() {
		this.allProducts = productService.getAllProducts();
		return null;
	}
	
	
	

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public List<Product> getAllProducts() {
		return allProducts;
	}

	public void setAllProducts(List<Product> allProducts) {
		this.allProducts = allProducts;
	}

	public Product getCurrentProduct() {
		return currentProduct;
	}

	public void setCurrentProduct(Product currentProduct) {
		this.currentProduct = currentProduct;
	}

}
