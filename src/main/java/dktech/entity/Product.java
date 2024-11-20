package dktech.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id",nullable = false)
	private long productID;

	@Column(name = "product",nullable = false, length = 100)
	private String product;

	@Column(name = "manufacture_date",nullable = false)
	private LocalDate manufactureDate;

	@Column(name = "expiration_date",nullable = false)
	private LocalDate expirationDate;

	@Column(name = "color",nullable = true, length = 50)
	private String color;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@Column(name = "type",nullable = false, length = 50)
	private String type;

	@Column(name = "price",nullable = false)
	private double price;

	@Column(name = "created_date",nullable = false)
	private LocalDate createdDate;

	// Constructors, Getters, Setters, and toString

	public Product() {
		super();
	}

	public Product(String product, LocalDate manufactureDate, LocalDate expirationDate, String color, Category category,
			String type, double price, LocalDate createdDate) {
		this.product = product;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
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
		return "Product [productID=" + productID + ", product=" + product + ", manufactureDate=" + manufactureDate
				+ ", expirationDate=" + expirationDate + ", color=" + color + ", category=" + category + ", type="
				+ type + ", price=" + price + ", createdDate=" + createdDate + "]";
	}
}
