package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import dktech.entity.Storage;

public interface StorageRepository extends JpaRepository<Storage, Long>, PagingAndSortingRepository<Storage, Long> {
}
