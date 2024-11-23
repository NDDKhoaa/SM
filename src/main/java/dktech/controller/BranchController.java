package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    // Create Branch
    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody Map<String, Object> payload) {
        // Extract the data from the request payload
        Long storeId = Long.valueOf(payload.get("storeId").toString());
        String branchName = payload.get("branchName").toString();
        String location = payload.get("location").toString();
        String telephone = payload.get("telephone").toString();
        String email = payload.get("email").toString();
        String status = payload.get("status").toString();

        // Use storeId to fetch the Store entity
        Store store = storeService.getStoreById(storeId);
        LocalDate createdDate = LocalDate.now();

        // Create a new Branch
        Branch branch = new Branch(branchName, location, telephone, email, createdDate, status, store);
        Branch createdBranch = branchService.saveBranch(branch);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBranch);
    }

    // Update Branch
    @PutMapping("/{branchID}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long branchID, @RequestBody Map<String, Object> payload) {
        // Fetch the existing branch by its ID
        Optional<Branch> existingBranchOptional = Optional.ofNullable(branchService.getBranchById(branchID));
        if (existingBranchOptional.isPresent()) {
            Branch existingBranch = existingBranchOptional.get();

            // Update branch attributes from the payload
            String branchName = payload.get("branchName").toString();
            String location = payload.get("location").toString();
            String telephone = payload.get("telephone").toString();
            String email = payload.get("email").toString();
            String status = payload.get("status").toString();

            existingBranch.setBranchName(branchName);
            existingBranch.setLocation(location);
            existingBranch.setTelephone(telephone);
            existingBranch.setEmail(email);
            existingBranch.setStatus(status);

            // Optionally, update the store if storeId is provided in payload
            if (payload.containsKey("storeId")) {
                Long storeId = Long.valueOf(payload.get("storeId").toString());
                Store store = storeService.getStoreById(storeId);
                existingBranch.setStore(store);
            }

            // Save the updated branch
            Branch updatedBranch = branchService.saveBranch(existingBranch);
            return ResponseEntity.ok(updatedBranch);
        } else {
            // If branch is not found, return not found status
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Get All Branches
    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        return ResponseEntity.ok(branchService.getAllBranches());
    }

    // Get Branch by ID
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        Branch branch = branchService.getBranchById(id);
        return branch != null ? ResponseEntity.ok(branch) : ResponseEntity.notFound().build();
    }

    // Delete Branch
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
