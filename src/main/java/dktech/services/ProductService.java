package dktech.services;

import java.util.List;

import dktech.entity.Product;

public interface ProductService {
	public List<Product> getAllProducts();

	public Product getProductById(long id);

	public Product addProduct(Product product);

	public Product updateProduct(long id, Product product);

	public void deleteProduct(long id);
}
