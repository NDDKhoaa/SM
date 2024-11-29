
// src/main/java/dktech/controller/AccountController.java
package dktech.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import dktech.entity.Account;
import dktech.entity.Employee;
import dktech.services.AccountService;
import dktech.services.EmployeeService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/create")
	public ResponseEntity<Account> createAccount(@RequestBody Map<String, Object> payload) {
		try {
			String username = payload.get("username").toString();
			String password = payload.get("password").toString();
			String email = payload.get("email").toString();
			Long employeeId = Long.valueOf(payload.get("employeeId").toString());

			Employee employee = employeeService.getEmployeeById(employeeId);

			Account account = new Account(username, password, email, employee);
			account = accountService.createAccount(account);

			return new ResponseEntity<>(account, HttpStatus.CREATED);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts() {
		return ResponseEntity.ok(accountService.getAllAccounts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
		Account account = accountService.getAccountById(id);
		return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
		try {
			String username = payload.get("username").toString();
			String password = payload.get("password").toString();
			String email = payload.get("email").toString();
			Long employeeId = Long.valueOf(payload.get("employeeId").toString());

			Employee employee = employeeService.getEmployeeById(employeeId);

			Account account = new Account(username, password, email, employee);
			account = accountService.updateAccount(id, account);

			return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
		} catch (NumberFormatException e) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
		accountService.deleteAccount(id);
		return ResponseEntity.noContent().build();
	}
}
