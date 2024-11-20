package dktech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dktech.dto.BillInfoRequest;
import dktech.entity.Bill;
import dktech.services.BillService;

@RestController
@RequestMapping("/api/bills")
public class BillController {

	@Autowired
	private BillService billService;

	@PostMapping("/create")
	public ResponseEntity<Bill> createBill(@RequestParam Long customerId, @RequestParam Long employeeId,
			@RequestParam Long branchId, @RequestBody List<BillInfoRequest> billInfoRequests) {
		try {
			// Call the service layer to create the bill
			Bill bill = billService.createBill(customerId, employeeId, branchId, billInfoRequests);

			// Return the created bill as the response
			return new ResponseEntity<>(bill, HttpStatus.CREATED);
		} catch (Exception e) {
			// Handle any errors during the creation process
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
