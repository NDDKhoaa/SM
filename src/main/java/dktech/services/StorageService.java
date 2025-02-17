package dktech.services;

import java.util.List;
import java.util.Optional;

import dktech.entity.Storage;

public interface StorageService {
	public List<Storage> getAllStorages(int page, int size);

	Optional<Storage> getStorageById(long id);

	Storage saveStorage(Storage storage);

	Storage updateStorage(long id, Storage storageDetails);

	void deleteStorage(long id);

	Storage setProductName(Storage storage);
}
