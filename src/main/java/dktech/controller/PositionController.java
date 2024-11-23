package dktech.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@PostMapping
	public ResponseEntity<Position> createPosition(@RequestBody Position position) {
		// Ensure the department exists
		Department department = departmentService.getDepartmentById(position.getDepartment().getDepartmentID());
		if (department != null) {
			position.setDepartment(department);
			position.setCreatedDate(LocalDate.now()); // Set createdDate when creating
			Position createdPosition = positionService.createPosition(position);
			return ResponseEntity.ok(createdPosition);
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Position> updatePosition(@PathVariable long id, @RequestBody Position position) {
		Position existingPosition = positionService.getPositionById(id);
		if (existingPosition != null) {
			// Set the department based on departmentID in request
			Department department = departmentService.getDepartmentById(position.getDepartment().getDepartmentID());
			if (department != null) {
				position.setDepartment(department);
				position.setCreatedDate(existingPosition.getCreatedDate()); // Preserve createdDate
				Position updatedPosition = positionService.createPosition(position);
				return ResponseEntity.ok(updatedPosition);
			}
			return ResponseEntity.badRequest().build(); // Return bad request if department not found
		}
		return ResponseEntity.notFound().build(); // Return not found if position doesn't exist
	}

	// Get a list of all positions
	@GetMapping
	public ResponseEntity<List<Position>> getAllPositions() {
		List<Position> positions = positionService.getAllPositions();
		return ResponseEntity.ok(positions);
	}

	// Get a specific position by ID
	@GetMapping("/{id}")
	public ResponseEntity<Position> getPositionById(@PathVariable long id) {
		Position position = positionService.getPositionById(id);
		if (position != null) {
			return ResponseEntity.ok(position);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Delete a position by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePosition(@PathVariable long id) {
		Position position = positionService.getPositionById(id);
		if (position != null) {
			positionService.deletePosition(id);
			return ResponseEntity.noContent().build(); // Successfully deleted
		} else {
			return ResponseEntity.notFound().build(); // If position doesn't exist
		}
	}
}
