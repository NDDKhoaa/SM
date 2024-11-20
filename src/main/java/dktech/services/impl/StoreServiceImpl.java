package dktech.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dktech.entity.Store;
import dktech.repository.StoreRepository;
import dktech.services.StoreService;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public Store saveStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreById(long id) {
        return storeRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteStore(long id) {
        storeRepository.deleteById(id);
    }
    
    @Override
    public Store getStoreByTaxNumber(String taxNumber) {
        return storeRepository.findByTaxNumber(taxNumber);
    }
}
