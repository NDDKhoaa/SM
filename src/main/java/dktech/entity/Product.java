package dktech.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id", nullable = false)
	private long productID;

	@Column(name = "product", nullable = false, length = 100)
	private String product;

	@Column(name = "description", nullable = true, length = 255)
	private String description;

	@Column(name = "color", nullable = true, length = 50)
	private String color;

	@Column(name = "type", nullable = true, length = 50)
	private String type;

	@Column(name = "price", nullable = true)
	private double price;
	
	@Column(name = "manufacture_date", nullable = true)
	private LocalDate manufactureDate;

	@Column(name = "expiration_date", nullable = true)
	private LocalDate expirationDate;

	@Column(name = "created_date", nullable = true)
	private LocalDate createdDate;

	@Column(name = "category", nullable = true)
	private String category;

	public Product() {
		super();
	}

	public Product(String product, String description, LocalDate manufactureDate, LocalDate expirationDate,
			String color, String category, String type, double price, LocalDate createdDate) {
		this.product = product;
		this.description = description;
		this.manufactureDate = manufactureDate;
		this.expirationDate = expirationDate;
		this.color = color;
		this.category = category;
		this.type = type;
		this.price = price;
		this.createdDate = createdDate;
	}

	// Getters and Setters
	public long getProductID() {
		return productID;
	}

	public void setProductID(long productID) {
		this.productID = productID;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(LocalDate manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", product=" + product + ", description=" + description
				+ ", manufactureDate=" + manufactureDate + ", expirationDate=" + expirationDate + ", color=" + color
				+ ", type=" + type + ", price=" + price + ", createdDate=" + createdDate + ", category=" + category
				+ "]";
	}

}
