package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dktech.entity.Store;


@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	Store findByTaxNumber(String taxNumber);
}
