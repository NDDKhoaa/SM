package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Authorize;

public interface AuthorizeRepository extends JpaRepository<Authorize, Long> {
}
