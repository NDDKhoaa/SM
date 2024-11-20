package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}