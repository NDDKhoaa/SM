
package dktech.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bill_infos")
public class BillInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_info_id", nullable = false)
	private long billInfoID;

	@ManyToOne
	@JoinColumn(name = "bill_id", nullable = false)
	@JsonIgnore
	private Bill bill;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "discount", nullable = false)
	private double discount;

	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;

	// Default constructor
	public BillInfo() {
	}

	// Constructor with parameters
	public BillInfo(Bill bill, Product product, int quantity, double price, double discount, LocalDate createdDate) {
		this.bill = bill;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.discount = discount;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
}
