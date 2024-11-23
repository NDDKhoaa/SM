package dktech.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dktech.dto.BillInfoRequest;
import dktech.entity.Bill;
import dktech.entity.BillInfo;
import dktech.entity.Product;
import dktech.repository.BillInfoRepository;
import dktech.repository.BillRepository;
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

	@Override
	public Bill createBill(Bill bill, List<BillInfoRequest> billInfoRequests) {
		bill = billRepository.save(bill);

		double total = 0.0;
		double tax = 0.0;

		for (BillInfoRequest request : billInfoRequests) {
			Product product = productRepository.findById(request.getProductId())
					.orElseThrow(() -> new RuntimeException("Product not found"));
			double productTotal = product.getPrice() * request.getQuantity();
			BillInfo billInfo = new BillInfo(bill, product, request.getQuantity(), product.getPrice(), 0.0,
					LocalDate.now());
			billInfoRepository.save(billInfo);
			total += productTotal;
		}

		tax = total * 0.15;
		total += tax;

		bill.setTax(tax);
		bill.setTotal(total);
		billRepository.save(bill);

		return bill;
	}

	@Override
	public List<Bill> getAllBills() {
		return billRepository.findAll();
	}

	@Override
	public Bill getBillById(Long id) {
		return billRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteBill(Long id) {
		billRepository.deleteById(id);
	}
}
