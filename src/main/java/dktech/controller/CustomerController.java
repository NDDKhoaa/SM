package dktech.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dktech.entity.Customer;
import dktech.entity.Employee;
import dktech.services.CustomerService;
import dktech.services.EmployeeService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    // Create a new customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Map<String, Object> payload) {
        try {
            // Extract createdBy (Employee ID) from payload
            Long employeeId = Long.valueOf(payload.get("createdBy").toString());
            Employee employee = employeeService.getEmployeeById(employeeId);

            if (employee == null) {
                return ResponseEntity.badRequest().body(null); // Handle invalid employee ID
            }

            // Create a new Customer
            Customer customer = new Customer(
                    payload.get("firstName").toString(),
                    payload.get("lastName").toString(),
                    payload.get("gender").toString(),
                    payload.get("telephone") != null ? payload.get("telephone").toString() : null,
                    payload.get("email") != null ? payload.get("email").toString() : null,
                    payload.get("address") != null ? payload.get("address").toString() : null,
                    employee
            );

            Customer savedCustomer = customerService.createCustomer(customer);
            return ResponseEntity.ok(savedCustomer);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // Get all customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        try {
            Customer existingCustomer = customerService.getCustomerById(id);
            if (existingCustomer == null) {
                return ResponseEntity.notFound().build();
            }

            // Update fields
            existingCustomer.setFirstName(payload.get("firstName").toString());
            existingCustomer.setLastName(payload.get("lastName").toString());
            existingCustomer.setGender(payload.get("gender").toString());
            existingCustomer.setTelephone(payload.get("telephone") != null ? payload.get("telephone").toString() : null);
            existingCustomer.setEmail(payload.get("email") != null ? payload.get("email").toString() : null);
            existingCustomer.setAddress(payload.get("address") != null ? payload.get("address").toString() : null);

            // Update createdBy (if provided)
            if (payload.get("createdBy") != null) {
                Long employeeId = Long.valueOf(payload.get("createdBy").toString());
                Employee employee = employeeService.getEmployeeById(employeeId);
                if (employee != null) {
                    existingCustomer.setCreatedBy(employee);
                }
            }

            Customer updatedCustomer = customerService.createCustomer(existingCustomer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // Delete customer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
