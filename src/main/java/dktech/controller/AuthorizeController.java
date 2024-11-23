// src/main/java/dktech/controller/AuthorizeController.java
package dktech.controller;

import dktech.entity.Authorize;
import dktech.services.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authorizations")
public class AuthorizeController {

    @Autowired
    private AuthorizeService authorizeService;

    @GetMapping
    public List<Authorize> getAllAuthorizations() {
        return authorizeService.getAllAuthorizations();
    }

    @GetMapping("/{id}")
    public Authorize getAuthorizationById(@PathVariable Long id) {
        return authorizeService.getAuthorizationById(id);
    }

    @PostMapping
    public Authorize createAuthorization(@RequestBody Authorize authorize) {
        return authorizeService.createAuthorization(authorize);
    }

    @PutMapping("/{id}")
    public Authorize updateAuthorization(@PathVariable Long id, @RequestBody Authorize authorize) {
        return authorizeService.updateAuthorization(id, authorize);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthorization(@PathVariable Long id) {
        authorizeService.deleteAuthorization(id);
    }
}
