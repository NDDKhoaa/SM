package dktech.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dktech.entity.Branch;
import dktech.entity.Employee;
import dktech.entity.Position;
import dktech.entity.Store;
import dktech.repository.EmployeeRepository;
import dktech.services.BranchService;
import dktech.services.EmployeeService;
import dktech.services.PositionService;
import dktech.services.StoreService;

@Repository
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository EmployeeRepository;
	@Autowired
	private PositionService positionService;
	@Autowired
	private BranchService branchService;
	@Autowired
	private StoreService storeService;

	@Override
	public Employee createEmployee(Employee employee) {
		return EmployeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return EmployeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {
		Employee employee = EmployeeRepository.findById(id).orElse(null);
		if (employee != null) {
			Position position = positionService.getPositionById(employee.getPosition().getPositionID());
			Branch branch = branchService.getBranchById(employee.getBranch().getBranchID());
			Store store = storeService.getStoreById(employee.getStore().getStoreID());
			employee.setPosition(position); // Set position
			employee.setBranch(branch); // Set branch
			employee.setStore(store);
		}
		return employee;
	}

	@Override
	public void deleteEmployee(long id) {
		EmployeeRepository.deleteById(id);
	}
}
