package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
