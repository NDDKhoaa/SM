package dktech.services;

import java.util.List;
import dktech.entity.Storage;

public interface StorageService {
    Storage saveStorage(Storage Storage);
    List<Storage> getAllStorages();
    Storage getStorageById(long id);
    void deleteStorage(long id);
}
