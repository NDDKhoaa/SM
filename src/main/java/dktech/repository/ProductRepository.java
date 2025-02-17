package dktech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByProduct(String name);

}