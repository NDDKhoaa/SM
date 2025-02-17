package dktech.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dktech.entity.Product;
import dktech.repository.ProductRepository;
import dktech.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProductById(long id) {
		return productRepository.findById(id);
	}

	public Product saveProduct(Product product) {
		return productRepository.save(product);
	}

	public Product updateProduct(long id, Product productDetails) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
		product.setProduct(productDetails.getProduct());
		product.setDescription(productDetails.getDescription());
		product.setColor(productDetails.getColor());
		product.setType(productDetails.getType());
		product.setPrice(productDetails.getPrice());
		product.setManufactureDate(productDetails.getManufactureDate());
		product.setExpirationDate(productDetails.getExpirationDate());
		product.setCreatedDate(productDetails.getCreatedDate());
		product.setCategory(productDetails.getCategory());
		return productRepository.save(product);
	}

	public boolean deleteProduct(long id) {
		if (productRepository.existsById(id)) {
			productRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
    public Optional<Product> getProductByName(String productName) {
        return productRepository.findByProduct(productName);
    }
}