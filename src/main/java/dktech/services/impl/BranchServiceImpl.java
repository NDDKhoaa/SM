package dktech.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dktech.entity.Branch;
import dktech.repository.BranchRepository;
import dktech.services.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public Branch getBranchById(long id) {
        return branchRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteBranch(long id) {
        branchRepository.deleteById(id);
    }
    
    @Override
    public Branch getBranchByName(String name) {
        return branchRepository.findByBranchName(name);
    }
}
