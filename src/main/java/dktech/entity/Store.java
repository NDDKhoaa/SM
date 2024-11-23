package dktech.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "stores")
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "store_id", nullable = false)
	private long storeID;

	@Column(name = "storeName", nullable = false, length = 100)
	private String storeName;

	@Column(name = "taxNumber", nullable = true, length = 50)
	private String taxNumber;

	@Column(name = "telephone", nullable = true, length = 20)
	private String telephone;

	@Column(name = "email", nullable = true, length = 100)
	private String email;

	@Column(name = "created_date", nullable = false)
	private LocalDate createdDate;

	@Column(name = "status", nullable = false)
	private String status;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Branch> branches;

	public Store() {
		super();
	}

	public Store(String storeName, String taxNumber, String telephone, String email, LocalDate createdDate,
			String status) {
		super();
		this.storeName = storeName;
		this.taxNumber = taxNumber;
		this.telephone = telephone;
		this.email = email;
		this.createdDate = createdDate;
		this.status = status;
	}

	public Store(String storeName, String taxNumber, String telephone, String email, LocalDate createdDate,
			String status, List<Branch> branches) {
		super();
		this.storeName = storeName;
		this.taxNumber = taxNumber;
		this.telephone = telephone;
		this.email = email;
		this.createdDate = createdDate;
		this.status = status;
		this.branches = branches;
	}

	public long getStoreID() {
		return storeID;
	}

	public void setStoreID(long storeID) {
		this.storeID = storeID;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
		this.branches = branches;
	}

	@Override
	public String toString() {
		return "Store [storeID=" + storeID + ", storeName=" + storeName + ", taxNumber=" + taxNumber + ", telephone="
				+ telephone + ", email=" + email + ", createdDate=" + createdDate + ", status=" + status + ", branches="
				+ branches + "]";
	}

}
