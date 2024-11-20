package dktech.services;

import java.util.List;
import dktech.entity.Store;

public interface StoreService {
    Store saveStore(Store store);
    List<Store> getAllStores();
    Store getStoreById(long id);
    void deleteStore(long id);
    public Store getStoreByTaxNumber(String taxNumber);
}
