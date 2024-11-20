package dktech.services;

import java.util.List;
import dktech.entity.Department;

public interface DepartmentService {
    Department saveDepartment(Department Department);
    List<Department> getAllDepartments();
    Department getDepartmentById(long id);
    void deleteDepartment(long id);
    public Department getDepartmentByName(String name);
}
