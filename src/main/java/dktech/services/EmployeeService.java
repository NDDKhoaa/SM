package dktech.services;

import dktech.entity.Employee;
import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee Employee);
    List<Employee> getAllEmployees();
    Employee getEmployeeById(long id);
    void deleteEmployee(long id);
}
