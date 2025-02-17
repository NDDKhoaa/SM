package dktech.configuration;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import dktech.entity.Category;
import dktech.entity.Product;
import dktech.entity.Storage;
import dktech.services.impl.CategoryServiceImpl;
import dktech.services.impl.ProductServiceImpl;
import dktech.services.impl.StorageServiceImpl;

@Component
@Lazy
public class SpringBootInitialData implements ApplicationRunner {

    @Autowired
    private StorageServiceImpl storageService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Ensure categories exist by ID
        Category additives = categoryService.getCategoryById(1).orElseGet(() -> {
            Category newCategory = new Category("Additives", LocalDate.now());
            return categoryService.saveCategory(newCategory);
        });

        Category casing = categoryService.getCategoryById(2).orElseGet(() -> {
            Category newCategory = new Category("Casing", LocalDate.now());
            return categoryService.saveCategory(newCategory);
        });

        Category filter = categoryService.getCategoryById(3).orElseGet(() -> {
            Category newCategory = new Category("Filter", LocalDate.now());
            return categoryService.saveCategory(newCategory);
        });

        Category tobacco = categoryService.getCategoryById(4).orElseGet(() -> {
            Category newCategory = new Category("Tobacco", LocalDate.now());
            return categoryService.saveCategory(newCategory);
        });

        // Ensure example products exist
        if (productService.getProductById(1).isEmpty()) {
            Product product1 = new Product();
            product1.setProduct("Premium Tobacco");
            product1.setDescription("High-quality tobacco for cigarettes");
            product1.setColor("Brown");
            product1.setType("Leaf");
            product1.setPrice(49.99);
            product1.setManufactureDate(LocalDate.of(2024, 1, 1));
            product1.setExpirationDate(LocalDate.of(2026, 1, 1));
            product1.setCreatedDate(LocalDate.now());
            product1.setCategory("Tobacco");
            productService.saveProduct(product1);
        }

        if (productService.getProductById(2).isEmpty()) {
            Product product2 = new Product();
            product2.setProduct("Natural Filter");
            product2.setDescription("Eco-friendly cigarette filter");
            product2.setColor("White");
            product2.setType("Organic");
            product2.setPrice(19.99);
            product2.setManufactureDate(LocalDate.of(2024, 2, 1));
            product2.setExpirationDate(LocalDate.of(2026, 2, 1));
            product2.setCreatedDate(LocalDate.now());
            product2.setCategory("Filter");
            productService.saveProduct(product2);
        }

        // Ensure example storage items exist
        if (storageService.getStorageById(1).isEmpty()) {
            Storage storage1 = new Storage();
            storage1.setQuantity(100);
            storage1.setMeasurement("Kg");
            storage1.setCreatedDate(LocalDate.now());
            storage1.setProductID(productService.getProductById(1).get().getProductID());
            storageService.saveStorage(storage1);
        }

        if (storageService.getStorageById(2).isEmpty()) {
            Storage storage2 = new Storage();
            storage2.setQuantity(200);
            storage2.setMeasurement("Kg");
            storage2.setCreatedDate(LocalDate.now());
            storage2.setProductID(productService.getProductById(2).get().getProductID());
            storageService.saveStorage(storage2);
        }
    }
}
