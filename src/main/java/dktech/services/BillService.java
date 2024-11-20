package dktech.services;

import java.util.List;

import dktech.dto.BillInfoRequest;
import dktech.entity.Bill;

public interface BillService {
	public Bill createBill(Long customerId, Long employeeId, Long branchId, List<BillInfoRequest> billInfoRequests);
    List<Bill> getAllBills();
    Bill getBillById(long id);
    void deleteBill(long id);
}
