package dktech.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dktech.entity.Product;
import dktech.entity.Storage;
import dktech.repository.ProductRepository;
import dktech.repository.StorageRepository;

@Service
public class StorageServiceImpl {

	@Autowired
	private StorageRepository storageRepository;

	@Autowired
	private ProductRepository productRepository;

	public List<Storage> getAllStorages(int page, int size) {
	    Pageable pageable = PageRequest.of(page, size);
	    Page<Storage> storagePage = storageRepository.findAll(pageable);
	    return storagePage.getContent();
	}

	public Optional<Storage> getStorageById(long id) {
		Optional<Storage> storage = storageRepository.findById(id);
		return storage.map(this::setProductName);
	}

	public Storage saveStorage(Storage storage) {
		return storageRepository.save(storage);
	}

	public Storage updateStorage(long id, Storage storageDetails) {
		Storage storage = storageRepository.findById(id).orElseThrow(() -> new RuntimeException("Storage not found"));
		storage.setQuantity(storageDetails.getQuantity());
		storage.setMeasurement(storageDetails.getMeasurement());
		storage.setCreatedDate(storageDetails.getCreatedDate());
		storage.setProductID(storageDetails.getProductID());
		return storageRepository.save(storage);
	}

	public void deleteStorage(long id) {
		storageRepository.deleteById(id);
	}

	private Storage setProductName(Storage storage) {
		Optional<Product> product = productRepository.findById(storage.getProductID());
		product.ifPresent(p -> storage.setProductName(p.getProduct()));
		return storage;
	}
}
