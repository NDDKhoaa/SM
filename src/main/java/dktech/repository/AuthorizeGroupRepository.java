package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.AuthorizeGroup;
import java.util.List;


public interface AuthorizeGroupRepository extends JpaRepository<AuthorizeGroup, Long> {
	AuthorizeGroup findByName(String name);
}
