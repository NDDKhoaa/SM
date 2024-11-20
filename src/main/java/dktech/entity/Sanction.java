package dktech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sanctions")
public class Sanction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sanction_id", nullable = false)
	private long sanctionID;

	@Column(name = "description", nullable = false, length = 255)
	private String description;

	// Constructors, Getters, Setters, and toString

	public Sanction() {
		super();
	}

	public Sanction(String description) {
		this.description = description;
	}

	// Getters and Setters
	public long getSanctionID() {
		return sanctionID;
	}

	public void setSanctionID(long sanctionID) {
		this.sanctionID = sanctionID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Sanction [sanctionID=" + sanctionID + ", description=" + description + "]";
	}
}
