package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dktech.entity.Product;
import dktech.entity.Category;
import dktech.services.ProductService;
import dktech.services.CategoryService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Create Product
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Map<String, Object> payload) {
        // Extract data from request payload
        String productName = payload.get("product").toString();
        LocalDate manufactureDate = LocalDate.parse(payload.get("manufactureDate").toString());
        LocalDate expirationDate = LocalDate.parse(payload.get("expirationDate").toString());
        String color = payload.get("color").toString();
        String type = payload.get("type").toString();
        double price = Double.parseDouble(payload.get("price").toString());
        Long categoryId = Long.valueOf(payload.get("categoryId").toString());

        // Fetch the Category entity
        Category category = categoryService.getCategoryById(categoryId);

        // Create a new Product
        LocalDate createdDate = LocalDate.now();
        Product product = new Product(productName, manufactureDate, expirationDate, color, category, type, price, createdDate);
        Product createdProduct = productService.addProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // Update Product
    @PutMapping("/{productID}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productID, @RequestBody Map<String, Object> payload) {
        Optional<Product> existingProductOptional = Optional.ofNullable(productService.getProductById(productID));
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();

            // Update product attributes from payload
            existingProduct.setProduct(payload.get("product").toString());
            existingProduct.setManufactureDate(LocalDate.parse(payload.get("manufactureDate").toString()));
            existingProduct.setExpirationDate(LocalDate.parse(payload.get("expirationDate").toString()));
            existingProduct.setColor(payload.get("color").toString());
            existingProduct.setType(payload.get("type").toString());
            existingProduct.setPrice(Double.parseDouble(payload.get("price").toString()));

            if (payload.containsKey("categoryId")) {
                Long categoryId = Long.valueOf(payload.get("categoryId").toString());
                Category category = categoryService.getCategoryById(categoryId);
                existingProduct.setCategory(category);
            }

            // Save the updated product
            Product updatedProduct = productService.addProduct(existingProduct);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Get All Products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
