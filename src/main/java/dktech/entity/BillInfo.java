package dktech.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bill_info")
public class BillInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "billInfo_id",nullable = false)
	private long billInfoID;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "bill_id", nullable = false)
	private Bill bill;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "quantity",nullable = false)
	private int quantity;

	@Column(name = "price",nullable = false)
	private double price;

	@Column(name = "promotion",nullable = true, length = 50)
	private String promotion;

	@Column(name = "created_date",nullable = false)
	private LocalDate createdDate;

	// Constructors, Getters, Setters, and toString

	public BillInfo() {
		super();
	}

	public BillInfo(Bill bill, Product product, int quantity, double price, String promotion, LocalDate createdDate) {
		this.bill = bill;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.promotion = promotion;
		this.createdDate = createdDate;
	}

	// Getters and Setters
	public long getBillInfoID() {
		return billInfoID;
	}

	public void setBillInfoID(long billInfoID) {
		this.billInfoID = billInfoID;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "BillInfo [billInfoID=" + billInfoID + ", bill=" + bill + ", product=" + product + ", quantity="
				+ quantity + ", price=" + price + ", promotion=" + promotion + ", createdDate=" + createdDate + "]";
	}
}
