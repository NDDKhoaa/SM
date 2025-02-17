package dktech.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import dktech.entity.Product;
import dktech.entity.Storage;
import dktech.services.impl.ProductServiceImpl;
import dktech.services.impl.StorageServiceImpl;
import dktech.util.ErrorHandling;

@RestController
@RequestMapping("/storage")
public class StorageController extends ErrorHandling {

	@Autowired
	private StorageServiceImpl storageService;

	@Autowired
	private ProductServiceImpl productService;

	@GetMapping("/list")
	public ResponseEntity<List<Storage>> getAllStorages(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		List<Storage> storages = storageService.getAllStorages(page, size);
		return ResponseEntity.ok(storages);
	}

	@GetMapping("/view/{id}")
	public ResponseEntity<Storage> getProductById(@PathVariable long id) {
		Optional<Storage> storage = storageService.getStorageById(id);
		return storage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/create")
	public Storage createStorage(@RequestBody Storage storage) {
		return storageService.saveStorage(storage);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Storage> updateStorage(@PathVariable long id, @RequestBody Storage storageDetails) {
		return ResponseEntity.ok(storageService.updateStorage(id, storageDetails));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteStorage(@PathVariable long id) {
		storageService.deleteStorage(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/export")
	public ResponseEntity<StreamingResponseBody> exportStorages(@RequestParam(required = false) String createdDate,
			@RequestParam(required = false) String productName, @RequestParam(required = false) String measurement, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		List<Storage> storages = storageService.getAllStorages(page, size); // Fetch storages with pagination
		final List<Storage> filteredStorages;

		if ((createdDate != null && !createdDate.isEmpty()) || (productName != null && !productName.isEmpty() || (measurement != null && !measurement.isEmpty()))) {
			filteredStorages = storages.stream()
	                .filter(storage -> (createdDate == null || createdDate.isEmpty()
                    || storage.getCreatedDate().toString().equals(createdDate))
                    && (productName == null || productName.isEmpty()
                            || storage.getProductName().toLowerCase().contains(productName.toLowerCase()))
                    && (measurement == null || measurement.isEmpty()
                            || storage.getMeasurement().toLowerCase().contains(measurement.toLowerCase())))
            .collect(Collectors.toList());
		} else {
			filteredStorages = storages;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		StreamingResponseBody stream = out -> {
			PrintWriter writer = new PrintWriter(out);
			writer.println("ID,Product,Quantity,Measurement,Created Date");
			for (Storage storage : filteredStorages) {
				Product product = productService.getProductById(storage.getProductID()).orElse(null);
				String productNameExport = (product != null) ? product.getProduct() : "Unknown";
				String formattedDate = storage.getCreatedDate().format(formatter);
				writer.printf("%d,%s,%.2f,%s,%s%n", storage.getStorageID(), productNameExport, storage.getQuantity(),
						storage.getMeasurement(), formattedDate);
			}
			writer.flush();
		};

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=storages.csv");

		return new ResponseEntity<>(stream, headers, HttpStatus.OK);
	}

	@PostMapping("/import")
	public ResponseEntity<Void> importStorages(@RequestParam("file") MultipartFile file) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			// Skip the first line (header)
			reader.readLine();
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				long id = 0;
				if (fields.length == 5) {
					if (!fields[0].isBlank()) {
						id = Long.parseLong(fields[0]);
					}
					String productName = fields[1];
					double quantity = Double.parseDouble(fields[2]);
					String measurement = fields[3];
					LocalDate createdDate = localDate(fields[4]);

					long productID = productService.getProductByName(productName).map(Product::getProductID)
							.orElseThrow(() -> new IllegalArgumentException("Product not found: " + productName));

					Optional<Storage> existingStorage = storageService.getStorageById(id);
					Storage storage = new Storage();
					storageSetter(storage, productName, quantity, measurement, createdDate, productID);
					if (existingStorage.isPresent()) {
						storage.setStorageID(existingStorage.get().getStorageID());
						storageService.updateStorage(existingStorage.get().getStorageID(), storage);
					} else {
						storageService.saveStorage(storage);
					}
				}
			}
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok().build();
	}

	private void storageSetter(Storage storage, String productName, double quantity, String measurement,
			LocalDate createdDate, long productID) {
		storage.setProductName(productName);
		storage.setQuantity(quantity);
		storage.setMeasurement(measurement);
		storage.setCreatedDate(createdDate);
		storage.setProductID(productID);
	}

}
