//package dktech.controller;
//
//import dktech.entity.Account;
//import dktech.entity.Employee;
//import dktech.services.AccountService;
//import dktech.services.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/accounts")
//public class AccountController {
//
//    @Autowired
//    private AccountService accountService;
//    
//    @Autowired
//    private EmployeeService employeeService;
//
//    @PostMapping
//    public ResponseEntity<Account> createAccount(@RequestBody Map<String, Object> payload) {
//        String username = payload.get("username").toString();
//        String password = payload.get("password").toString();
//        String email = payload.get("email").toString();
//        Long employeeId = Long.valueOf(payload.get("employeeId").toString());
//
//        Employee employee = employeeService.getEmployeeById(employeeId);
//        
//        Account account = new Account(username, password, email, employee);
//        Account createdAccount = accountService.saveAccount(account);
//
//        return ResponseEntity.ok(createdAccount);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Account>> getAllAccounts() {
//        return ResponseEntity.ok(accountService.getAllAccounts());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
//        Account account = accountService.getAccountById(id);
//        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
//        accountService.deleteAccount(id);
//        return ResponseEntity.noContent().build();
//    }
//}
