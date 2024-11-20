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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dktech.entity.Department;
import dktech.services.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	// Create a new department
	@PostMapping
	public ResponseEntity<Department> createDepartment(@RequestBody Map<String, Object> payload) {
		try {
			Department department = new Department(payload.get("department").toString(),
					payload.get("description").toString(),LocalDate.now());

			Department savedDepartment = departmentService.saveDepartment(department);
			return ResponseEntity.ok(savedDepartment);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}

	// Get all departments
	@GetMapping
	public ResponseEntity<List<Department>> getAllDepartments() {
		List<Department> departments = departmentService.getAllDepartments();
		return ResponseEntity.ok(departments);
	}

	// Get department by ID
	@GetMapping("/{id}")
	public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
		Department department = departmentService.getDepartmentById(id);
		if (department != null) {
			return ResponseEntity.ok(department);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Update department
	@PutMapping("/{id}")
	public ResponseEntity<Department> updateDepartment(@PathVariable Long id,
			@RequestBody Map<String, Object> payload) {
		try {
			Department existingDepartment = departmentService.getDepartmentById(id);
			if (existingDepartment == null) {
				return ResponseEntity.notFound().build();
			}

			existingDepartment.setDepartment(payload.get("department").toString());
			existingDepartment
					.setDescription(payload.get("description") != null ? payload.get("description").toString() : null);

			Department updatedDepartment = departmentService.saveDepartment(existingDepartment);
			return ResponseEntity.ok(updatedDepartment);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
	}

	// Delete department
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
		Department department = departmentService.getDepartmentById(id);
		if (department != null) {
			departmentService.deleteDepartment(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
