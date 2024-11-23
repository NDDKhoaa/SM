// src/main/java/dktech/controller/AuthorizeGroupController.java
package dktech.controller;

import dktech.entity.AuthorizeGroup;
import dktech.services.AuthorizeGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authorization-groups")
public class AuthorizeGroupController {

    @Autowired
    private AuthorizeGroupService authorizeGroupService;

    @GetMapping
    public List<AuthorizeGroup> getAllAuthorizationGroups() {
        return authorizeGroupService.getAllAuthorizationGroups();
    }

    @GetMapping("/{id}")
    public AuthorizeGroup getAuthorizationGroupById(@PathVariable Long id) {
        return authorizeGroupService.getAuthorizationGroupById(id);
    }

    @PostMapping
    public AuthorizeGroup createAuthorizationGroup(@RequestBody AuthorizeGroup authorizeGroup) {
        return authorizeGroupService.createAuthorizationGroup(authorizeGroup);
    }

    @PutMapping("/{id}")
    public AuthorizeGroup updateAuthorizationGroup(@PathVariable Long id, @RequestBody AuthorizeGroup authorizeGroup) {
        return authorizeGroupService.updateAuthorizationGroup(id, authorizeGroup);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthorizationGroup(@PathVariable Long id) {
        authorizeGroupService.deleteAuthorizationGroup(id);
    }
}
