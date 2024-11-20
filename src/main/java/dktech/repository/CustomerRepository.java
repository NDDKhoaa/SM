package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
