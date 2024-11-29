package dktech.configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dktech.entity.Account;
import dktech.entity.Authorize;
import dktech.entity.AuthorizeGroup;
import dktech.entity.Branch;
import dktech.entity.Category;
import dktech.entity.Department;
import dktech.entity.Employee;
import dktech.entity.Position;
import dktech.entity.Sanction;
import dktech.entity.Store;
import dktech.services.AuthorizeGroupService;
import dktech.services.AuthorizeService;
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
import dktech.services.impl.AccountDetailsServiceImpl;

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

	@Autowired
	private AccountDetailsServiceImpl accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthorizeGroupService authorizeGroupService;

	@Autowired
	private AuthorizeService authorizeService;

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

		if (positionService.findPositionByName("Manager") == null) {
			Department managementDepartment = departmentService.getDepartmentByName("Management");
			Position managerPosition = new Position("Manager", managementDepartment, "Manages the team",
					LocalDate.now());
			positionService.createPosition(managerPosition);
		}

		if (positionService.findPositionByName("HR Specialist") == null) {
			Department hrDepartment = departmentService.getDepartmentByName("HR");
			Position managerPosition = new Position("HR Specialist", hrDepartment, "Hiring Resources", LocalDate.now());
			positionService.createPosition(managerPosition);
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
			Store dkBilliard = new Store("DK Billiard", "123456789", "0768080998", "dkgearstore@gmail.com",
					LocalDate.now(), "Active");
			storeService.saveStore(dkBilliard);
		}

		// Check if branches already exist
		Store dkBilliard = storeService.getStoreByTaxNumber("123456789");
		if (dkBilliard != null) {
			List<Branch> dkBilliardBranches = new ArrayList<Branch>();
			if (branchService.getBranchByName("DK Billiard HCM") == null) {
				Branch dkBilliardHCM = new Branch("DK Billiard HCM", "HCM", "0768080998", "dkgearstore@gmail.com",
						LocalDate.now(), "Active", dkBilliard);
				branchService.saveBranch(dkBilliardHCM);

			}

			if (branchService.getBranchByName("DK Billiard HN") == null) {
				Branch dkBilliardHN = new Branch("DK Billiard HN", "HN", "0768080998", "dkgearstore@gmail.com",
						LocalDate.now(), "Active", dkBilliard);
				branchService.saveBranch(dkBilliardHN);

			}
			dkBilliardBranches.add(branchService.getBranchByName("DK Billiard HCM"));
			dkBilliardBranches.add(branchService.getBranchByName("DK Billiard HN"));
			dkBilliard.setBranches(dkBilliardBranches);
			storeService.saveStore(dkBilliard);
		}

		if (employeeService.getEmployeeById(1) == null) {
			Employee employee = new Employee();
			employee.setEmployeeFirstName("John");
			employee.setEmployeeLastName("Doe");
			employee.setGender("Male");
			employee.setDob(LocalDate.of(1990, 1, 1));
			employee.setIdNumber("ID123456");
			employee.setAddress("123 Main St");
			employee.setNationality("American");
			employee.setTaxNumber("TAX123456");
			employee.setPosition(positionService.findPositionByName("Manager"));
			employee.setBranch(branchService.getBranchByName("DK Billiard HCM"));
			employee.setStore(storeService.getStoreByTaxNumber("123456789"));
			employee.setAccountID("admin");
			employee.setTelephone("123-456-7890");
			employee.setEmail("john.doe@example.com");
			employee.setSalary(50000);
			employee.setStatus("Active");
			employeeService.createEmployee(employee);
		}

		if (employeeService.getEmployeeById(2) == null) {
			Employee employee = new Employee();
			employee.setEmployeeFirstName("Jane");
			employee.setEmployeeLastName("Smith");
			employee.setGender("Female");
			employee.setDob(LocalDate.of(1992, 2, 2));
			employee.setIdNumber("ID654321");
			employee.setAddress("456 Elm St");
			employee.setNationality("American");
			employee.setTaxNumber("TAX654321");
			employee.setPosition(positionService.findPositionByName("HR Specialist"));
			employee.setBranch(branchService.getBranchByName("DK Billiard HN"));
			employee.setStore(storeService.getStoreByTaxNumber("123456789"));
			employee.setAccountID("admin");
			employee.setTelephone("987-654-3210");
			employee.setEmail("jane.smith@example.com");
			employee.setSalary(45000);
			employee.setStatus("Active");
			employeeService.createEmployee(employee);
		}
		if (accountService.findAccountbyUsername("admin") == null) {
			Account adminAccount = new Account();
			adminAccount.setUsername("admin");
			adminAccount.setPassword(passwordEncoder.encode("admin"));
			adminAccount.setEmail("nddkhoaa2408@gmail.com");
			adminAccount.setEmployee(employeeService.getEmployeeById(1));
			accountService.saveAccount(adminAccount);
		}

		if (sanctionService.getSanctionById(1) == null) {
			Sanction sanction = new Sanction("Store Main Menu");
			sanctionService.saveSanction(sanction);
		}
		if (sanctionService.getSanctionById(2) == null) {
			Sanction sanction = new Sanction("Store");
			sanctionService.saveSanction(sanction);
		}
		if (sanctionService.getSanctionById(3) == null) {
			Sanction sanction = new Sanction("Department");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(4) == null) {
			Sanction sanction = new Sanction("Position");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(5) == null) {
			Sanction sanction = new Sanction("Branch");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(6) == null) {
			Sanction sanction = new Sanction("Employee");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(7) == null) {
			Sanction sanction = new Sanction("Customer");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(8) == null) {
			Sanction sanction = new Sanction("Product Main Menu");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(9) == null) {
			Sanction sanction = new Sanction("Product");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(10) == null) {
			Sanction sanction = new Sanction("Category");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(11) == null) {
			Sanction sanction = new Sanction("Storage");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(12) == null) {
			Sanction sanction = new Sanction("Security");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(13) == null) {
			Sanction sanction = new Sanction("Account");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(14) == null) {
			Sanction sanction = new Sanction("Authorize");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(15) == null) {
			Sanction sanction = new Sanction("Authorize Group");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(16) == null) {
			Sanction sanction = new Sanction("Sanction");
			sanctionService.saveSanction(sanction);
		}

		if (sanctionService.getSanctionById(17) == null) {
			Sanction sanction = new Sanction("Bill");
			sanctionService.saveSanction(sanction);
		}

		if (authorizeGroupService.getAuthorizationGroupByName("Store") == null) {
			AuthorizeGroup group1 = new AuthorizeGroup("Store", "Store Sanctions");
			Set<Sanction> sanctionSet = new HashSet<Sanction>();
			if (sanctionService.getSanctionById(1) == null) {
				Sanction sanction1 = sanctionService.getSanctionById(1);
				sanctionSet.add(sanction1);
			}
			if (sanctionService.getSanctionById(2) == null) {
				Sanction sanction2 = sanctionService.getSanctionById(2);
				sanctionSet.add(sanction2);
			}
			if (sanctionService.getSanctionById(3) == null) {
				Sanction sanction2 = sanctionService.getSanctionById(3);
				sanctionSet.add(sanction2);
			}
			if (sanctionService.getSanctionById(4) == null) {
				Sanction sanction2 = sanctionService.getSanctionById(4);
				sanctionSet.add(sanction2);
			}
			if (sanctionService.getSanctionById(5) == null) {
				Sanction sanction2 = sanctionService.getSanctionById(5);
				sanctionSet.add(sanction2);
			}
			if (sanctionService.getSanctionById(6) == null) {
				Sanction sanction2 = sanctionService.getSanctionById(6);
				sanctionSet.add(sanction2);
			}
			if (sanctionService.getSanctionById(7) == null) {
				Sanction sanction2 = sanctionService.getSanctionById(7);
				sanctionSet.add(sanction2);
			}
			group1.setSanctions(sanctionSet);
			authorizeGroupService.createAuthorizationGroup(group1);
		}

		if (authorizeGroupService.getAuthorizationGroupByName("Product") == null) {
			AuthorizeGroup group2 = new AuthorizeGroup("Product", "Product Sanctions");
			Set<Sanction> sanctionSet = new HashSet<Sanction>();
			if (sanctionService.getSanctionById(8) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(8);
				sanctionSet.add(sanction3);
			}
			if (sanctionService.getSanctionById(9) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(9);
				sanctionSet.add(sanction3);
			}
			if (sanctionService.getSanctionById(9) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(10);
				sanctionSet.add(sanction3);
			}
			if (sanctionService.getSanctionById(10) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(11);
				sanctionSet.add(sanction3);
			}
			group2.setSanctions(sanctionSet);
			authorizeGroupService.createAuthorizationGroup(group2);
		}

		if (authorizeGroupService.getAuthorizationGroupByName("Security") == null) {
			AuthorizeGroup group3 = new AuthorizeGroup("Security", "Security Sanctions");
			Set<Sanction> sanctionSet = new HashSet<Sanction>();
			if (sanctionService.getSanctionById(11) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(12);
				sanctionSet.add(sanction3);
			}
			if (sanctionService.getSanctionById(13) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(13);
				sanctionSet.add(sanction3);
			}
			if (sanctionService.getSanctionById(14) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(14);
				sanctionSet.add(sanction3);
			}
			if (sanctionService.getSanctionById(15) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(15);
				sanctionSet.add(sanction3);
			}
			if (sanctionService.getSanctionById(16) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(16);
				sanctionSet.add(sanction3);
			}
			if (sanctionService.getSanctionById(17) == null) {
				Sanction sanction3 = sanctionService.getSanctionById(17);
				sanctionSet.add(sanction3);
			}
			group3.setSanctions(sanctionSet);
			authorizeGroupService.createAuthorizationGroup(group3);
		}

		Account adminAccount = accountService.findAccountbyUsername("admin");

		if (adminAccount != null) {
			AuthorizeGroup group1 = authorizeGroupService.getAuthorizationGroupByName("Store");
			AuthorizeGroup group2 = authorizeGroupService.getAuthorizationGroupByName("Product");
			AuthorizeGroup group3 = authorizeGroupService.getAuthorizationGroupByName("Security");

			Sanction[] sanctions = new Sanction[18];
			for (int i = 1; i <= 17; i++) {
				sanctions[i] = sanctionService.getSanctionById(i);
			}

			Set<Authorize> existingAuthorizations = new HashSet<>(authorizeService.getAllAuthorizations());

			if (group1 != null) {
				createAuthorizationIfNotExists(existingAuthorizations, group1, adminAccount, sanctions[1], 1);
				createAuthorizationIfNotExists(existingAuthorizations, group1, adminAccount, sanctions[2], 2);
				createAuthorizationIfNotExists(existingAuthorizations, group1, adminAccount, sanctions[3], 3);
				createAuthorizationIfNotExists(existingAuthorizations, group1, adminAccount, sanctions[4], 4);
				createAuthorizationIfNotExists(existingAuthorizations, group1, adminAccount, sanctions[5], 5);
				createAuthorizationIfNotExists(existingAuthorizations, group1, adminAccount, sanctions[6], 6);
				createAuthorizationIfNotExists(existingAuthorizations, group1, adminAccount, sanctions[7], 7);
			}

			if (group2 != null) {
				createAuthorizationIfNotExists(existingAuthorizations, group2, adminAccount, sanctions[8], 8);
				createAuthorizationIfNotExists(existingAuthorizations, group2, adminAccount, sanctions[9], 9);
				createAuthorizationIfNotExists(existingAuthorizations, group2, adminAccount, sanctions[10], 10);
				createAuthorizationIfNotExists(existingAuthorizations, group2, adminAccount, sanctions[11], 11);
			}

			if (group3 != null) {
				createAuthorizationIfNotExists(existingAuthorizations, group3, adminAccount, sanctions[12], 12);
				createAuthorizationIfNotExists(existingAuthorizations, group3, adminAccount, sanctions[13], 13);
				createAuthorizationIfNotExists(existingAuthorizations, group3, adminAccount, sanctions[14], 14);
				createAuthorizationIfNotExists(existingAuthorizations, group3, adminAccount, sanctions[15], 15);
				createAuthorizationIfNotExists(existingAuthorizations, group3, adminAccount, sanctions[16], 16);
				createAuthorizationIfNotExists(existingAuthorizations, group3, adminAccount, sanctions[17], 17);
			}
		}
	}

	private void createAuthorizationIfNotExists(Set<Authorize> existingAuthorizations, AuthorizeGroup group,
			Account account, Sanction sanction, long id) {
		if (sanction != null && existingAuthorizations.stream().noneMatch(auth -> auth.getAuthorizeID() == id)) {
			Authorize authorize = new Authorize(group, account, sanction);
			authorize.setAuthorizeID(id);
			authorizeService.createAuthorization(authorize);
		}
	}

}
