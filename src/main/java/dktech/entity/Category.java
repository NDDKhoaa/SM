package dktech.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id", nullable = false)
	private long categoryID;

	@Column(name = "category", nullable = false, length = 100)
	private String category;

	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;

	public Category() {
		super();
	}

	public Category(String category, LocalDate createdDate) {
		this.category = category;
		this.createdDate = createdDate;
	}

	// Getters and Setters
	public long getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(long categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Category [categoryID=" + categoryID + ", category=" + category + ", createdDate=" + createdDate + "]";
	}
}
