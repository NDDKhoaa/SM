package dktech.services;

import dktech.entity.Branch;
import java.util.List;

public interface BranchService {
	public Branch saveBranch(Branch branch);
    List<Branch> getAllBranches();
    Branch getBranchById(long id);
    void deleteBranch(long id);
    public Branch getBranchByName(String name);
}
