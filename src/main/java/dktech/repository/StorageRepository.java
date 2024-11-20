package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Storage;

public interface StorageRepository extends JpaRepository<Storage, Long> {
}
