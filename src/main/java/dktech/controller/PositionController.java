package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dktech.entity.Department;
import dktech.entity.Position;
import dktech.services.DepartmentService;
import dktech.services.PositionService;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    // Create a new Position
    @PostMapping
    public ResponseEntity<Position> createPosition(@RequestBody Map<String, Object> payload) {
        String positionName = payload.get("position").toString();
        Long departmentId = Long.parseLong(payload.get("departmentId").toString());
        String description = payload.get("description").toString();
        LocalDate createdDate = LocalDate.now();

        Department department = departmentService.getDepartmentById(departmentId); // Ensure this exists
        Position position = new Position(positionName, department, description, createdDate);
        Position createdPosition = positionService.createPosition(position);

        return ResponseEntity.ok(createdPosition);
    }


    // Get all Positions
    @GetMapping
    public ResponseEntity<List<Position>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }

    // Get a specific Position by ID
    @GetMapping("/{id}")
    public ResponseEntity<Position> getPositionById(@PathVariable Long id) {
        Position position = positionService.getPositionById(id);
        return position != null ? ResponseEntity.ok(position) : ResponseEntity.notFound().build();
    }

    // Delete a Position by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}
