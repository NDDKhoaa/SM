package dktech.services;

import dktech.entity.BillInfo;
import java.util.List;

public interface BillInfoService {
    BillInfo createBillInfo(BillInfo billInfo);
    List<BillInfo> getAllBillInfo();
    BillInfo getBillInfoById(long id);
    void deleteBillInfo(long id);
}
