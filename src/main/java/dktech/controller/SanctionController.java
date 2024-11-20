package dktech.controller;

import dktech.entity.Sanction;
import dktech.services.SanctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sanctions")
public class SanctionController {

    @Autowired
    private SanctionService sanctionService;

    @PostMapping
    public ResponseEntity<Sanction> createSanction(@RequestBody Map<String, Object> payload) {
        String description = payload.get("description").toString();

        Sanction sanction = new Sanction(description);
        Sanction createdSanction = sanctionService.saveSanction(sanction);

        return ResponseEntity.ok(createdSanction);
    }

    @GetMapping
    public ResponseEntity<List<Sanction>> getAllSanctions() {
        return ResponseEntity.ok(sanctionService.getAllSanctions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sanction> getSanctionById(@PathVariable Long id) {
        Sanction sanction = sanctionService.getSanctionById(id);
        return sanction != null ? ResponseEntity.ok(sanction) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSanction(@PathVariable Long id) {
        sanctionService.deleteSanction(id);
        return ResponseEntity.noContent().build();
    }
}
