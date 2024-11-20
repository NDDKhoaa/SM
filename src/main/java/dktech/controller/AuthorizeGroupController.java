//package dktech.controller;
//
//import dktech.entity.AuthorizeGroup;
//import dktech.services.AuthorizeGroupService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/authorize-groups")
//public class AuthorizeGroupController {
//
//    @Autowired
//    private AuthorizeGroupService authorizeGroupService;
//
//    @PostMapping
//    public ResponseEntity<AuthorizeGroup> createAuthorizeGroup(@RequestBody Map<String, Object> payload) {
//        String name = payload.get("name").toString();
//        String description = payload.get("description").toString();
//        
//        AuthorizeGroup authorizeGroup = new AuthorizeGroup(name, description);
//        AuthorizeGroup createdAuthorizeGroup = authorizeGroupService.saveAuthorizeGroup(authorizeGroup);
//
//        return ResponseEntity.ok(createdAuthorizeGroup);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<AuthorizeGroup>> getAllAuthorizeGroups() {
//        return ResponseEntity.ok(authorizeGroupService.getAllAuthorizeGroups());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<AuthorizeGroup> getAuthorizeGroupById(@PathVariable Long id) {
//        AuthorizeGroup authorizeGroup = authorizeGroupService.getAuthorizeGroupById(id);
//        return authorizeGroup != null ? ResponseEntity.ok(authorizeGroup) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteAuthorizeGroup(@PathVariable Long id) {
//        authorizeGroupService.deleteAuthorizeGroup(id);
//        return ResponseEntity.noContent().build();
//    }
//}
