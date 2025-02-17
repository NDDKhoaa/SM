
package dktech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping("/product")
	public String viewProducts() {
		return "product";
	}

	@GetMapping("/product-form")
	public String viewProductForm() {
		return "product-form";
	}

	@GetMapping("/category")
	public String viewCategories() {
		return "category";
	}

	@GetMapping("/storage")
	public String viewStorage() {
		return "storage";
	}
	
	@GetMapping("/storage-form")
	public String viewStorageForm() {
		return "storage-form";
	}
}
