package dktech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/branch")
    public String branchPage(Model model) {
        model.addAttribute("page", "branch");
        return "branch";
    }

    @GetMapping("/employee")
    public String employeePage() {
        return "employee"; // Maps to employee.html
    }

    @GetMapping("/customer")
    public String customerPage() {
        return "customer"; // Maps to customer.html
    }

    @GetMapping("/store")
    public String storePage() {
        return "store"; // Maps to store.html
    }

    @GetMapping("/product")
    public String productPage() {
        return "product"; // Maps to product.html
    }

    @GetMapping("/bill")
    public String billPage() {
        return "bill"; // Maps to bill.html
    }
    
    @GetMapping("/category")
    public String categoryPage(Model model) {
        model.addAttribute("page", "category");
        return "category"; // Maps to category.html
    }

    // Mappings for Department, Storage, and Sanction
    @GetMapping("/department")
    public String departmentPage(Model model) {
        model.addAttribute("page", "department");
        return "department"; // Maps to department.html
    }

    @GetMapping("/storage")
    public String storagePage(Model model) {
        model.addAttribute("page", "storage");
        return "storage"; // Maps to storage.html
    }

    @GetMapping("/sanction")
    public String sanctionPage(Model model) {
        model.addAttribute("page", "sanction");
        return "sanction"; // Maps to sanction.html
    }

    @GetMapping("/position")
    public String positionPage(Model model) {
        model.addAttribute("page", "position");
        return "position"; // Maps to position.html
    }

    @GetMapping("/billinfo")
    public String billinfoPage(Model model) {
        model.addAttribute("page", "billinfo");
        return "billinfo"; // Maps to billinfo.html
    }

    // New mappings for Account, Authorize, and AuthorizeGroup
    @GetMapping("/account")
    public String accountPage(Model model) {
        model.addAttribute("page", "account");
        return "account"; // Maps to account.html
    }

    @GetMapping("/authorize")
    public String authorizePage(Model model) {
        model.addAttribute("page", "authorize");
        return "authorize"; // Maps to authorize.html
    }

    @GetMapping("/authorizegroup")
    public String authorizeGroupPage(Model model) {
        model.addAttribute("page", "authorizegroup");
        return "authorizegroup"; // Maps to authorize-group.html
    }
}
