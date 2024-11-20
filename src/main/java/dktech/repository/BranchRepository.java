package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Branch;


public interface BranchRepository extends JpaRepository<Branch, Long> {
	Branch findByBranchName(String branchName);
}
