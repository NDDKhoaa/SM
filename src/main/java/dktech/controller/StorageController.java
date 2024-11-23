package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dktech.entity.Product;
import dktech.entity.Storage;
import dktech.services.ProductService;
import dktech.services.StorageService;

@RestController
@RequestMapping("/api/storages")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private ProductService productService;

    // Create or Update Storage
    @PostMapping
    public ResponseEntity<Storage> createStorage(@RequestBody Map<String, Object> payload) {
        Long productId = Long.valueOf(payload.get("productId").toString());
        Product product = productService.getProductById(productId);
        int quantity = Integer.parseInt(payload.get("quantity").toString());
        String status = payload.get("status").toString();

        Storage storage = new Storage(product, quantity, status, LocalDate.now());
        Storage createdStorage = storageService.saveStorage(storage);

        return ResponseEntity.ok(createdStorage);
    }

    // Update Storage
    @PutMapping("/{storageID}")
    public ResponseEntity<Storage> updateStorage(@PathVariable Long storageID, @RequestBody Map<String, Object> payload) {
        Storage existingStorage = storageService.getStorageById(storageID);
        if (existingStorage != null) {
            Long productId = Long.valueOf(payload.get("productId").toString());
            Product product = productService.getProductById(productId);
            int quantity = Integer.parseInt(payload.get("quantity").toString());
            String status = payload.get("status").toString();

            existingStorage.setProduct(product);
            existingStorage.setQuantity(quantity);
            existingStorage.setStatus(status);
            existingStorage.setCreatedDate(LocalDate.now());  // Optionally update the creation date

            Storage updatedStorage = storageService.saveStorage(existingStorage);
            return ResponseEntity.ok(updatedStorage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all storages
    @GetMapping
    public ResponseEntity<List<Storage>> getAllStorages() {
        return ResponseEntity.ok(storageService.getAllStorages());
    }

    // Get storage by ID
    @GetMapping("/{id}")
    public ResponseEntity<Storage> getStorageById(@PathVariable Long id) {
        Storage storage = storageService.getStorageById(id);
        return storage != null ? ResponseEntity.ok(storage) : ResponseEntity.notFound().build();
    }

    // Delete a storage
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStorage(@PathVariable Long id) {
        if (storageService.getStorageById(id) != null) {
            storageService.deleteStorage(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
