package dktech.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id",nullable = false)
	private long departmentID;

	@Column(name = "department",nullable = false, length = 100)
	private String department;

	@Column(name = "description",nullable = true, length = 255)
	private String description;

	@Column(name = "created_date",nullable = false)
	private LocalDate createdDate;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Position> positions;

	// Constructors, Getters, Setters, and toString

	public Department() {
		super();
	}

	public Department(String department, String description, LocalDate createdDate) {
		this.department = department;
		this.description = description;
		this.createdDate = createdDate;
	}

	public long getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(long departmentID) {
		this.departmentID = departmentID;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	@Override
	public String toString() {
		return "Department [departmentID=" + departmentID + ", department=" + department + ", description="
				+ description + ", createdDate=" + createdDate + ", positions=" + positions + "]";
	}
}
