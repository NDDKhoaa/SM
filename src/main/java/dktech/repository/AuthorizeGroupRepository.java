package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.AuthorizeGroup;

public interface AuthorizeGroupRepository extends JpaRepository<AuthorizeGroup, Long> {
}
