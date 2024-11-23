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
@Table(name = "storages")
public class Storage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "storage_id", nullable = false)
	private long storageID;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "status", nullable = false, length = 20)
	private String status;

	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@JsonProperty("productID")
	public Long getProductID() {
		return product != null ? product.getProductID() : null;
	}

	// Constructors, Getters, Setters, and toString

	public Storage() {
		super();
	}

	public Storage(Product product, int quantity, String status, LocalDate createdDate) {
		this.product = product;
		this.quantity = quantity;
		this.status = status;
		this.createdDate = createdDate;
	}

	// Getters and Setters
	public long getStorageID() {
		return storageID;
	}

	public void setStorageID(long storageID) {
		this.storageID = storageID;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "Storage [storageID=" + storageID + ", product=" + product + ", quantity=" + quantity + ", status="
				+ status + ", createdDate=" + createdDate + "]";
	}
}
