package dktech.services;

import java.util.List;
import java.util.Optional;

import dktech.entity.Product;

public interface ProductService {
    public List<Product> getAllProducts();
    public Optional<Product> getProductById(long id);
    public Product saveProduct(Product product);
    public Product updateProduct(long id, Product productDetails);
    public boolean deleteProduct(long id);
    public Optional<Product> getProductByName(String productName);
}
