
// src/main/java/dktech/controller/ViewController.java
package dktech.controller;

import dktech.security.AccountDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	private void addSanctionsToModel(Authentication authentication, Model model) {
		if (authentication != null && authentication.getPrincipal() instanceof AccountDetails) {
			AccountDetails accountDetails = (AccountDetails) authentication.getPrincipal();
			model.addAttribute("sanctions", accountDetails.getSanctions());
		}
	}
	
	@GetMapping("/")
	public String indexPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "index");
		return "index";
	}

	@GetMapping("/branch")
	public String branchPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "branch");
		return "branch";
	}

	@GetMapping("/employee")
	public String employeePage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "employee");
		return "employee"; // Maps to employee.html
	}

	@GetMapping("/customer")
	public String customerPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "customer");
		return "customer"; // Maps to customer.html
	}

	@GetMapping("/store")
	public String storePage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "store");
		return "store"; // Maps to store.html
	}

	@GetMapping("/product")
	public String productPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "product");
		return "product"; // Maps to product.html
	}

	@GetMapping("/bill")
	public String billPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "bill");
		return "bill"; // Maps to bill.html
	}

	@GetMapping("/category")
	public String categoryPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "category");
		return "category"; // Maps to category.html
	}

	@GetMapping("/department")
	public String departmentPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "department");
		return "department"; // Maps to department.html
	}

	@GetMapping("/storage")
	public String storagePage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "storage");
		return "storage"; // Maps to storage.html
	}

	@GetMapping("/sanction")
	public String sanctionPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "sanction");
		return "sanction"; // Maps to sanction.html
	}

	@GetMapping("/position")
	public String positionPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "position");
		return "position"; // Maps to position.html
	}

	@GetMapping("/billinfo")
	public String billinfoPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "billinfo");
		return "billinfo"; // Maps to billinfo.html
	}

	@GetMapping("/account")
	public String accountPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "account");
		return "account"; // Maps to account.html
	}

	@GetMapping("/authorize")
	public String authorizePage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "authorize");
		return "authorize"; // Maps to authorize.html
	}

	@GetMapping("/authorizegroup")
	public String authorizeGroupPage(Authentication authentication, Model model) {
		addSanctionsToModel(authentication, model);
		model.addAttribute("page", "authorizegroup");
		return "authorizegroup"; // Maps to authorize-group.html
	}
}
