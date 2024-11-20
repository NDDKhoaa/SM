package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Sanction;

public interface SanctionRepository extends JpaRepository<Sanction, Long> {
}
