package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dktech.dto.BillInfoRequest;
import dktech.entity.Bill;
import dktech.entity.Branch;
import dktech.entity.Customer;
import dktech.entity.Employee;
import dktech.services.BillService;
import dktech.services.BranchService;
import dktech.services.CustomerService;
import dktech.services.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/bills")
public class BillController {

	@Autowired
	private BillService billService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private BranchService branchService;

	@PostMapping("/create")
	public ResponseEntity<Bill> createBill(@RequestBody Map<String, Object> payload) {
		try {
			Long customerId = Long.valueOf(payload.get("customerId").toString());
			Long employeeId = Long.valueOf(payload.get("employeeId").toString());
			Long branchId = Long.valueOf(payload.get("branchId").toString());
			List<Map<String, Object>> billInfoRequestMaps = (List<Map<String, Object>>) payload.get("billInfoRequests");

			List<BillInfoRequest> billInfoRequests = billInfoRequestMaps.stream().map(map -> {
				BillInfoRequest request = new BillInfoRequest();
				request.setProductId(Long.valueOf(map.get("productId").toString()));
				request.setQuantity(Integer.parseInt(map.get("quantity").toString()));
				request.setPrice(Double.parseDouble(map.get("price").toString()));
				return request;
			}).collect(Collectors.toList());

			Customer customer = customerService.getCustomerById(customerId);
			Employee employee = employeeService.getEmployeeById(employeeId);
			Branch branch = branchService.getBranchById(branchId);

			Bill bill = new Bill(employee, branch, customer, 0.0, 0.0, LocalDate.now(), "Created");
			bill = billService.createBill(bill, billInfoRequests);

			return new ResponseEntity<>(bill, HttpStatus.CREATED);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<Bill>> getAllBills() {
		return ResponseEntity.ok(billService.getAllBills());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
		Bill bill = billService.getBillById(id);
		return bill != null ? ResponseEntity.ok(bill) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBill(@PathVariable Long id) {
		billService.deleteBill(id);
		return ResponseEntity.noContent().build();
	}
}
