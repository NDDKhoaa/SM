package dktech.services.impl;

import dktech.entity.BillInfo;
import dktech.repository.BillInfoRepository;
import dktech.services.BillInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillInfoServiceImpl implements BillInfoService {

    @Autowired
    private BillInfoRepository billInfoRepository;

    @Override
    public BillInfo createBillInfo(BillInfo billInfo) {
        return billInfoRepository.save(billInfo);
    }

    @Override
    public List<BillInfo> getAllBillInfo() {
        return billInfoRepository.findAll();
    }

    @Override
    public BillInfo getBillInfoById(long id) {
        return billInfoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBillInfo(long id) {
        billInfoRepository.deleteById(id);
    }
}
