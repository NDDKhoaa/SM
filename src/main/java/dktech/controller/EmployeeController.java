package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dktech.entity.Branch;
import dktech.entity.Employee;
import dktech.entity.Position;
import dktech.entity.Store;
import dktech.services.BranchService;
import dktech.services.EmployeeService;
import dktech.services.PositionService;
import dktech.services.StoreService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private BranchService branchService;

	@Autowired
	private StoreService storeService;

	@Autowired
	private PositionService positionService;

	@PostMapping
	public ResponseEntity<Employee> addEmployee(@RequestBody Map<String, Object> payload) {
		// Extract the values from the payload
		String employeeFirstName = payload.get("employeeFirstName").toString();
		String employeeLastName = payload.get("employeeLastName").toString();
		String gender = payload.get("gender").toString();
		LocalDate dob = LocalDate.parse(payload.get("dob").toString()); // Ensure the date format is correct
		String idNumber = payload.get("idNumber").toString();
		String address = payload.get("address").toString();
		String nationality = payload.get("nationality").toString();
		String taxNumber = payload.get("taxNumber").toString();
		Long positionId = Long.parseLong(payload.get("positionId").toString());
		Long branchId = Long.parseLong(payload.get("branchId").toString());
		Long storeId = Long.parseLong(payload.get("storeId").toString());
		String accountID = payload.get("accountID").toString();
		String telephone = payload.get("telephone").toString();
		String email = payload.get("email").toString();
		Double salary = Double.parseDouble(payload.get("salary").toString());
		String status = payload.get("status").toString();

		// Fetch the related entities (Position, Branch, Store) based on their IDs
		Position position = positionService.getPositionById(positionId); // Ensure the position exists
		Branch branch = branchService.getBranchById(branchId); // Ensure the branch exists
		Store store = storeService.getStoreById(storeId); // Ensure the store exists

		// Create the Employee object
		Employee employee = new Employee(employeeFirstName, employeeLastName, gender, dob, idNumber, address,
				nationality, taxNumber, position, branch, store, accountID, telephone, email, salary, status,
				LocalDate.now());

		// Save the employee
		Employee createdEmployee = employeeService.createEmployee(employee);

		return ResponseEntity.ok(createdEmployee); // Return the created employee
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		return ResponseEntity.ok(employeeService.getAllEmployees());
	}
}
