package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping
	public ResponseEntity<List<Storage>> getAllStorages() {
		return ResponseEntity.ok(storageService.getAllStorages());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Storage> getStorageById(@PathVariable Long id) {
		Storage storage = storageService.getStorageById(id);
		return storage != null ? ResponseEntity.ok(storage) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStorage(@PathVariable Long id) {
		storageService.deleteStorage(id);
		return ResponseEntity.noContent().build();
	}
}
