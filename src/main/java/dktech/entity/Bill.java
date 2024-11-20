package dktech.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bills")
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bill_id", nullable = false)
	private long billID;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "branch_id", nullable = false)
	private Branch branch;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Column(name = "tax",nullable = false)
	private double tax;

	@Column(name = "total",nullable = false)
	private double total;

	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;
	
	@Column(name = "status",nullable = false, length = 20)
	private String status;

	// Constructors, Getters, Setters, and toString

	public Bill() {
		super();
	}

	public Bill(Employee employee, Branch branch, Customer customer, double tax, double total, LocalDate createdDate,
			String status) {
		this.employee = employee;
		this.branch = branch;
		this.customer = customer;
		this.tax = tax;
		this.total = total;
		this.createdDate = createdDate;
		this.status = status;
	}

	// Getters and Setters
	public long getBillID() {
		return billID;
	}

	public void setBillID(long billID) {
		this.billID = billID;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Bill [billID=" + billID + ", employee=" + employee + ", branch=" + branch + ", customer=" + customer
				+ ", tax=" + tax + ", total=" + total + ", createdDate=" + createdDate + ", status=" + status + "]";
	}
}
