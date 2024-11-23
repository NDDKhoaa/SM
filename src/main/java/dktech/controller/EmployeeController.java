package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    private PositionService positionService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private StoreService storeService;

    // Create Employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> payload) {
        // Extract employee details from the request payload
        String firstName = payload.get("firstName").toString();
        String lastName = payload.get("lastName").toString();
        String gender = payload.get("gender").toString();
        String dob = payload.get("dob").toString(); // assuming it's in ISO format (yyyy-MM-dd)
        String idNumber = payload.get("idNumber").toString();
        String address = payload.get("address").toString();
        String nationality = payload.get("nationality").toString();
        String taxNumber = payload.get("taxNumber").toString();
        Long positionId = Long.valueOf(payload.get("positionID").toString());
        Long branchId = Long.valueOf(payload.get("branchID").toString());
        Long storeId = Long.valueOf(payload.get("storeID").toString());
        String accountId = payload.get("accountId").toString();
        String telephone = payload.get("telephone").toString();
        String email = payload.get("email").toString();
        Double salary = Double.valueOf(payload.get("salary").toString());
        String status = payload.get("status").toString();
        
        // Fetch the position, branch, and store from their respective services
        Position position = positionService.getPositionById(positionId);
        Branch branch = branchService.getBranchById(branchId);
        Store store = storeService.getStoreById(storeId);

        // Create and save the new Employee
        Employee employee = new Employee(firstName, lastName, gender, LocalDate.parse(dob), idNumber, address, 
                nationality, taxNumber, position, branch, store, accountId, telephone, email, salary, status);
        
        Employee createdEmployee = employeeService.createEmployee(employee);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    // Update Employee
    @PutMapping("/{employeeID}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long employeeID, @RequestBody Map<String, Object> payload) {
        // Fetch the existing employee by ID
        Optional<Employee> existingEmployeeOptional = Optional.ofNullable(employeeService.getEmployeeById(employeeID));
        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();

            // Update employee attributes from the payload
            String firstName = payload.get("firstName").toString();
            String lastName = payload.get("lastName").toString();
            String gender = payload.get("gender").toString();
            String dob = payload.get("dob").toString();
            String idNumber = payload.get("idNumber").toString();
            String address = payload.get("address").toString();
            String nationality = payload.get("nationality").toString();
            String taxNumber = payload.get("taxNumber").toString();
            
            // Extracting position, branch, and store objects from the payload
            Map<String, Object> positionMap = (Map<String, Object>) payload.get("position");
            Map<String, Object> branchMap = (Map<String, Object>) payload.get("branch");
            Map<String, Object> storeMap = (Map<String, Object>) payload.get("store");

            // Extracting IDs from the nested objects
            Long positionId = Long.valueOf(positionMap.get("positionID").toString());
            Long branchId = Long.valueOf(branchMap.get("branchID").toString());
            Long storeId = Long.valueOf(storeMap.get("storeID").toString());

            // Extracting other attributes
            String accountId = payload.get("accountID").toString();
            String telephone = payload.get("telephone").toString();
            String email = payload.get("email").toString();
            Double salary = Double.valueOf(payload.get("salary").toString());
            String status = payload.get("status").toString();

            // Update the fields of the existing employee
            existingEmployee.setEmployeeFirstName(firstName);
            existingEmployee.setEmployeeLastName(lastName);
            existingEmployee.setGender(gender);
            existingEmployee.setDob(LocalDate.parse(dob));
            existingEmployee.setIdNumber(idNumber);
            existingEmployee.setAddress(address);
            existingEmployee.setNationality(nationality);
            existingEmployee.setTaxNumber(taxNumber);

            // Fetch related position, branch, and store by ID
            existingEmployee.setPosition(positionService.getPositionById(positionId));
            existingEmployee.setBranch(branchService.getBranchById(branchId));
            existingEmployee.setStore(storeService.getStoreById(storeId));

            existingEmployee.setAccountID(accountId);
            existingEmployee.setTelephone(telephone);
            existingEmployee.setEmail(email);
            existingEmployee.setSalary(salary);
            existingEmployee.setStatus(status);

            // Save the updated employee
            Employee updatedEmployee = employeeService.createEmployee(existingEmployee);
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // Get all Employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Get Employee by ID
    @GetMapping("/{employeeID}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long employeeID) {
        Employee employee = employeeService.getEmployeeById(employeeID);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    // Delete Employee
    @DeleteMapping("/{employeeID}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long employeeID) {
        Optional<Employee> employeeOptional = Optional.ofNullable(employeeService.getEmployeeById(employeeID));
        if (employeeOptional.isPresent()) {
            employeeService.deleteEmployee(employeeID);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
