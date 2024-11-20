package dktech.controller;

import dktech.entity.BillInfo;
import dktech.services.BillInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill-info")
public class BillInfoController {

    @Autowired
    private BillInfoService billInfoService;

    @PostMapping
    public ResponseEntity<BillInfo> createBillInfo(@RequestBody BillInfo billInfo) {
        return ResponseEntity.ok(billInfoService.createBillInfo(billInfo));
    }

    @GetMapping
    public ResponseEntity<List<BillInfo>> getAllBillInfo() {
        return ResponseEntity.ok(billInfoService.getAllBillInfo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillInfo> getBillInfoById(@PathVariable Long id) {
        BillInfo billInfo = billInfoService.getBillInfoById(id);
        return billInfo != null ? ResponseEntity.ok(billInfo) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBillInfo(@PathVariable Long id) {
        billInfoService.deleteBillInfo(id);
        return ResponseEntity.noContent().build();
    }
}
