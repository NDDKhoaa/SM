package dktech.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dktech.entity.Product;
import dktech.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/list")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@GetMapping("/view/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable long id) {
		Optional<Product> product = productService.getProductById(id);
		return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/create")
	public Product createProduct(@RequestBody Product product) {
		return productService.saveProduct(product);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product productDetails) {
		return ResponseEntity.ok(productService.updateProduct(id, productDetails));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteStorage(@PathVariable long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}