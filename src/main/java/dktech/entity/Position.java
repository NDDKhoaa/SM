package dktech.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "positions")
public class Position {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "position_id",nullable = false)
	private long positionID;

	@Column(name = "position",nullable = false, length = 100)
	private String position;

	@ManyToOne
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;

	@Column(name = "description",nullable = true, length = 255)
	private String description;

	@Column(name = "created_date",nullable = false)
	private LocalDate createdDate;

	// Constructors, Getters, Setters, and toString

	public Position() {
		super();
	}

	public Position(String position, Department department, String description, LocalDate createdDate) {
		this.position = position;
		this.department = department;
		this.description = description;
		this.createdDate = createdDate;
	}

	// Getters and Setters
	public long getPositionID() {
		return positionID;
	}

	public void setPositionID(long positionID) {
		this.positionID = positionID;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
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

	@Override
	public String toString() {
		return "Position [positionID=" + positionID + ", position=" + position + ", department=" + department
				+ ", description=" + description + ", createdDate=" + createdDate + "]";
	}
}
