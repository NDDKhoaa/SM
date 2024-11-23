package dktech.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Product> products;

	@JsonProperty("productIDs")
	public List<Long> getProductIDs() {
		return products != null ? products.stream().map(Product::getProductID).toList() : null;
	}

	// Constructors, Getters, Setters, and toString

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category [categoryID=" + categoryID + ", category=" + category + ", createdDate=" + createdDate + "]";
	}
}
