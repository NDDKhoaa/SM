package dktech.services;

import java.util.List;

import dktech.entity.Employee;

public interface EmployeeService {
    Employee createEmployee(Employee Employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(long id);
    void deleteEmployee(long id);
}
