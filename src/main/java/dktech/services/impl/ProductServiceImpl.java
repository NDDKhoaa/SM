package dktech.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dktech.entity.Product;
import dktech.repository.ProductRepository;
import dktech.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setProductID(id);
            return productRepository.save(product);
        }
        return null;
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
