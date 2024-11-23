
package dktech.services;

import java.util.List;

import dktech.dto.BillInfoRequest;
import dktech.entity.Bill;

public interface BillService {
	Bill createBill(Bill bill, List<BillInfoRequest> billInfoRequests);

	List<Bill> getAllBills();

	Bill getBillById(Long id);

	void deleteBill(Long id);
}
