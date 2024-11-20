package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dktech.entity.Branch;
import dktech.entity.Store;
import dktech.services.BranchService;
import dktech.services.StoreService;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;
    
    @Autowired
    private StoreService storeService;

    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody Map<String, Object> payload) {
        Long storeId = Long.valueOf(payload.get("storeId").toString());
        String branchName = payload.get("branchName").toString();
        String location = payload.get("location").toString();
        String telephone = payload.get("telephone").toString();
        String email = payload.get("email").toString();
        String status = payload.get("status").toString();

        // Use storeId to fetch the Store entity
        Store store = storeService.getStoreById(storeId);
        LocalDate createdDate = LocalDate.now();
        Branch branch = new Branch(branchName, location, telephone, email,createdDate, status, store);
        Branch createdBranch = branchService.saveBranch(branch);

        return ResponseEntity.ok(createdBranch);
    }

    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        Branch branch = branchService.getBranchById(id);
        return branch != null ? ResponseEntity.ok(branch) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
