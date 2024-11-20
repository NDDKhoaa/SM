package dktech.controller;

import dktech.entity.Store;
import dktech.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

	@Autowired
	private StoreService storeService;

	@PostMapping
	public ResponseEntity<Store> createStore(@RequestBody Store store) {
		// Set the createdDate to the current date when creating a new store
		store.setCreatedDate(LocalDate.now());
		Store createdStore = storeService.saveStore(store);
		return ResponseEntity.ok(createdStore);
	}

	@GetMapping
	public ResponseEntity<List<Store>> getAllStores() {
		return ResponseEntity.ok(storeService.getAllStores());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Store> updateStore(@PathVariable long id, @RequestBody Store store) {
		// Get the existing store and preserve its createdDate
		Store existingStore = storeService.getStoreById(id);

		if (existingStore != null) {
			// Preserve the createdDate when updating the store
			store.setCreatedDate(existingStore.getCreatedDate());
			Store updatedStore = storeService.saveStore(store);
			return ResponseEntity.ok(updatedStore);
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Store> getStoreById(@PathVariable long id) {
		Store store = storeService.getStoreById(id);
		if (store != null) {
			return ResponseEntity.ok(store);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStore(@PathVariable long id) {
		Store store = storeService.getStoreById(id);
		if (store != null) {
			storeService.deleteStore(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
