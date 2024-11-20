//package dktech.controller;
//
//import dktech.entity.Authorize;
//import dktech.entity.Account;
//import dktech.entity.AuthorizeGroup;
//import dktech.entity.Sanction;
//import dktech.services.AuthorizeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/authorizations")
//public class AuthorizeController {
//
//    @Autowired
//    private AuthorizeService authorizeService;
//
//    @PostMapping
//    public ResponseEntity<Authorize> createAuthorize(@RequestBody Map<String, Object> payload) {
//        Long authorizeGroupId = Long.valueOf(payload.get("authorizeGroupId").toString());
//        Long accountId = Long.valueOf(payload.get("accountId").toString());
//        Long sanctionId = Long.valueOf(payload.get("sanctionId").toString());
//
//        AuthorizeGroup authorizeGroup = new AuthorizeGroup(); // Fetch from DB
//        Account account = new Account(); // Fetch from DB
//        Sanction sanction = new Sanction(); // Fetch from DB
//
//        Authorize authorize = new Authorize(authorizeGroup, account, sanction);
//        Authorize createdAuthorize = authorizeService.saveAuthorize(authorize);
//
//        return ResponseEntity.ok(createdAuthorize);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Authorize> getAuthorizeById(@PathVariable Long id) {
//        Authorize authorize = authorizeService.getAuthorizeById(id);
//        return authorize != null ? ResponseEntity.ok(authorize) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteAuthorize(@PathVariable Long id) {
//        authorizeService.deleteAuthorize(id);
//        return ResponseEntity.noContent().build();
//    }
//}
