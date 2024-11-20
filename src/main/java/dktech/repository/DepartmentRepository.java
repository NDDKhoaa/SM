package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	Department findByDepartment(String name);
}
