package dktech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dktech.entity.Product;
import dktech.services.impl.ProductServiceImpl;

@RestController
@RequestMapping(value="/api/products")
public class ProductController {

	@Autowired
	private ProductServiceImpl productService;

	@PostMapping
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
	    System.out.println("Received product: " + product);  // Log received product
	    if (product.getProduct() == null || product.getProduct().trim().isEmpty()) {
	        return ResponseEntity.badRequest().body(null);  
	    }
	    Product createdProduct = productService.addProduct(product);
	    return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
}
