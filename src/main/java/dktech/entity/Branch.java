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
@Table(name = "branches")
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branch_id",nullable = false)
	private long branchID;

	@Column(name = "branch_name",nullable = false, length = 100)
	private String branchName;

	@Column(name = "location",nullable = true, length = 200)
	private String location;

	@Column(name = "telephone",nullable = true, length = 20)
	private String telephone;

	@Column(name = "email",nullable = true, length = 100)
	private String email;

	@Column(name = "created_date",nullable = false)
	private LocalDate createdDate;

	@Column(name = "status",nullable = false)
	private String status;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @JsonIgnore // Keep the full store object ignored
    private Store store;

    // Add a getter for storeID to expose only the storeID
    @JsonProperty("storeID")
    public Long getStoreID() {
        return store != null ? store.getStoreID() : null; // Safely return store ID or null
    }
	public Branch() {
		super();
	}

	public Branch(String branchName, String location, String telephone, String email, LocalDate createdDate,
			String status, Store store) {
		super();
		this.branchName = branchName;
		this.location = location;
		this.telephone = telephone;
		this.email = email;
		this.createdDate = createdDate;
		this.status = status;
		this.store = store;
	}

	public long getBranchID() {
		return branchID;
	}

	public void setBranchID(long branchID) {
		this.branchID = branchID;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	@Override
	public String toString() {
		return "Branch [branchID=" + branchID + ", branchName=" + branchName + ", location=" + location + ", telephone="
				+ telephone + ", email=" + email + ", createdDate=" + createdDate + ", status=" + status + ", store="
				+ store + "]";
	}

}
