package dktech.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dktech.entity.Department;
import dktech.repository.DepartmentRepository;
import dktech.services.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository DepartmentRepository;

    @Override
    public Department saveDepartment(Department Department) {
        return DepartmentRepository.save(Department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return DepartmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(long id) {
        return DepartmentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteDepartment(long id) {
        DepartmentRepository.deleteById(id);
    }
    
    @Override
    public Department getDepartmentByName(String name) {
        return DepartmentRepository.findByDepartment(name);
    }
}
