package dktech.configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import dktech.entity.Branch;
import dktech.entity.Category;
import dktech.entity.Department;
import dktech.entity.Store;
import dktech.services.BranchService;
import dktech.services.CategoryService;
import dktech.services.CustomerService;
import dktech.services.DepartmentService;
import dktech.services.EmployeeService;
import dktech.services.PositionService;
import dktech.services.ProductService;
import dktech.services.SanctionService;
import dktech.services.StorageService;
import dktech.services.StoreService;

@Component
@Lazy
public class SpringBootInitialData implements ApplicationRunner {

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private SanctionService sanctionService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
	    // Check if departments already exist
	    if (departmentService.getDepartmentByName("Management") == null) {
	        Department managementDepartment = new Department("Management", "Management Team", LocalDate.now());
	        departmentService.saveDepartment(managementDepartment);
	    }

	    if (departmentService.getDepartmentByName("HR") == null) {
	        Department hrDepartment = new Department("HR", "Human Resource", LocalDate.now());
	        departmentService.saveDepartment(hrDepartment);
	    }

	    if (departmentService.getDepartmentByName("Employee") == null) {
	        Department employeeDepartment = new Department("Employee", "Casual Employee", LocalDate.now());
	        departmentService.saveDepartment(employeeDepartment);
	    }

	    // Check if categories already exist
	    if (categoryService.getCategoryByName("Dairy") == null) {
	        Category dairy = new Category("Dairy", LocalDate.now());
	        categoryService.createCategory(dairy);
	    }

	    if (categoryService.getCategoryByName("Food") == null) {
	        Category food = new Category("Food", LocalDate.now());
	        categoryService.createCategory(food);
	    }

	    if (categoryService.getCategoryByName("Billiards") == null) {
	        Category billiard = new Category("Billiards", LocalDate.now());
	        categoryService.createCategory(billiard);
	    }

	    // Check if store already exists
	    if (storeService.getStoreByTaxNumber("123456789") == null) {
	        Store dkBilliard = new Store("DK Billiard", "123456789", "0768080998", "dkgearstore@gmail.com", LocalDate.now(), "Active");
	        storeService.saveStore(dkBilliard);
	    }

	    // Check if branches already exist
	    Store dkBilliard = storeService.getStoreByTaxNumber("123456789");
	    if (dkBilliard != null) {
	    	List<Branch> dkBilliardBranches = new ArrayList<Branch>();
	        if (branchService.getBranchByName("DK Billiard HCM") == null) {
	            Branch dkBilliardHCM = new Branch("DK Billiard HCM", "HCM", "0768080998", "dkgearstore@gmail.com", LocalDate.now(), "Active", dkBilliard);
	            branchService.saveBranch(dkBilliardHCM);
	            
	        }
	        
	        if (branchService.getBranchByName("DK Billiard HN") == null) {
	            Branch dkBilliardHN = new Branch("DK Billiard HN", "HN", "0768080998", "dkgearstore@gmail.com", LocalDate.now(), "Active", dkBilliard);
	            branchService.saveBranch(dkBilliardHN);
	            
	        }
	        dkBilliardBranches.add(branchService.getBranchByName("DK Billiard HCM"));
	        dkBilliardBranches.add(branchService.getBranchByName("DK Billiard HN"));
	        dkBilliard.setBranches(dkBilliardBranches);
	        storeService.saveStore(dkBilliard);
	    }
	}


}

