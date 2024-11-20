package dktech.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dktech.dto.BillInfoRequest;
import dktech.entity.Bill;
import dktech.entity.BillInfo;
import dktech.entity.Branch;
import dktech.entity.Customer;
import dktech.entity.Employee;
import dktech.entity.Product;
import dktech.repository.BillInfoRepository;
import dktech.repository.BillRepository;
import dktech.repository.BranchRepository;
import dktech.repository.CustomerRepository;
import dktech.repository.EmployeeRepository;
import dktech.repository.ProductRepository;
import dktech.services.BillService;

@Service
public class BillServiceImpl implements BillService {

	 @Autowired
	    private BillRepository billRepository;

	    @Autowired
	    private BillInfoRepository billInfoRepository;

	    @Autowired
	    private ProductRepository productRepository;

	    @Autowired
	    private EmployeeRepository employeeRepository;

	    @Autowired
	    private BranchRepository branchRepository;

	    @Autowired
	    private CustomerRepository customerRepository;

    @Override
    public Bill createBill(Long customerId, Long employeeId, Long branchId, List<BillInfoRequest> billInfoRequests) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        Branch branch = branchRepository.findById(branchId).orElseThrow(() -> new RuntimeException("Branch not found"));

        Bill bill = new Bill(employee, branch, customer, 0.0, 0.0, LocalDate.now(), "Created");
        bill = billRepository.save(bill);  // Save the bill first to generate ID

        double total = 0.0;
        double tax = 0.0;

        for (BillInfoRequest request : billInfoRequests) {
            Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            // Calculate total price for this product and add it to the total
            double productTotal = product.getPrice() * request.getQuantity();
            BillInfo billInfo = new BillInfo(bill, product, request.getQuantity(), product.getPrice(), request.getPromotion(), LocalDate.now());
            billInfoRepository.save(billInfo);

            total += productTotal;
            if (request.getPromotion() != null) {
                // Apply a simple discount based on promotion (just for illustration)
                total -= total * 0.1; // 10% off if a promotion is applied
            }
        }

        // Apply tax and update the total amount
        tax = total * 0.15; // Example: 15% tax
        total += tax;

        bill.setTax(tax);
        bill.setTotal(total);
        billRepository.save(bill);  // Save the updated bill with total and tax

        return bill;
    }

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public Bill getBillById(long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBill(long id) {
        billRepository.deleteById(id);
    }
}
