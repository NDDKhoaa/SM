package dktech.services.impl;

import dktech.entity.Storage;
import dktech.repository.StorageRepository;
import dktech.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageRepository StorageRepository;

    @Override
    public Storage saveStorage(Storage Storage) {
        return StorageRepository.save(Storage);
    }

    @Override
    public List<Storage> getAllStorages() {
        return StorageRepository.findAll();
    }

    @Override
    public Storage getStorageById(long id) {
        return StorageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteStorage(long id) {
        StorageRepository.deleteById(id);
    }
}
