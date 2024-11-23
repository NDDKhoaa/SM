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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id", nullable = false)
	private long employeeID;

	@Column(name = "employeeFirstName", nullable = false, length = 50)
	private String employeeFirstName;

	@Column(name = "employeeLastName", nullable = false, length = 50)
	private String employeeLastName;

	@Column(name = "gender", nullable = false, length = 10)
	private String gender;

	@Column(name = "dob", nullable = false)
	private LocalDate dob;

	@Column(name = "idNumber", nullable = false, length = 20)
	private String idNumber;

	@Column(name = "address", nullable = true, length = 255)
	private String address;

	@Column(name = "nationality", nullable = true, length = 50)
	private String nationality;

	@Column(name = "taxNumber", nullable = true, length = 20)
	private String taxNumber;

	@Column(name = "accountID", nullable = true, length = 50)
	private String accountID;

	@Column(name = "telephone", nullable = true, length = 20)
	private String telephone;

	@Column(name = "email", nullable = true, length = 100)
	private String email;

	@Column(name = "salary", nullable = false)
	private double salary;

	@Column(name = "created_date", nullable = true)
	private LocalDate createdDate;

	@Column(name = "status", nullable = false, length = 20)
	private String status;

	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Customer> customers;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "position_id", nullable = true)
	private Position position;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "branch_id", nullable = true)
	private Branch branch;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "store_id", nullable = true)
	private Store store;

	@JsonProperty("positionID")
	public Long getPositionID() {
		return position != null ? position.getPositionID() : null;
	}

	@JsonProperty("branchID")
	public Long getBranchID() {
		return branch != null ? branch.getBranchID() : null;
	}

	@JsonProperty("storeID")
	public Long getStoreID() {
		return store != null ? store.getStoreID() : null;
	}

	@JsonProperty("customerIDs")
	public List<Long> getCustomerIDs() {
		return customers != null ? customers.stream().map(Customer::getCustomerID).toList() : null;
	}

	// Constructors, Getters, Setters, and toString

	public Employee() {
		super();
	}

	public Employee(String employeeFirstName, String employeeLastName, String gender, LocalDate dob, String idNumber,
			String address, String nationality, String taxNumber, Position position, Branch branch, Store store,
			String accountID, String telephone, String email, double salary, LocalDate createdDate, String status) {
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.gender = gender;
		this.dob = dob;
		this.idNumber = idNumber;
		this.address = address;
		this.nationality = nationality;
		this.taxNumber = taxNumber;
		this.position = position;
		this.branch = branch;
		this.store = store;
		this.accountID = accountID;
		this.telephone = telephone;
		this.email = email;
		this.salary = salary;
		this.createdDate = createdDate;
		this.status = status;
	}

	public Employee(String employeeFirstName, String employeeLastName, String gender, LocalDate dob, String idNumber,
			String address, String nationality, String taxNumber, Position position, Branch branch, Store store,
			String accountID, String telephone, String email, Double salary, String status, LocalDate createdDate) {
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.gender = gender;
		this.dob = dob;
		this.idNumber = idNumber;
		this.address = address;
		this.nationality = nationality;
		this.taxNumber = taxNumber;
		this.position = position;
		this.branch = branch;
		this.store = store;
		this.accountID = accountID;
		this.telephone = telephone;
		this.email = email;
		this.salary = salary;
		this.status = status;
		this.createdDate = createdDate; // You can also set the createdDate to LocalDate.now() directly if you prefer
	}

	public Employee(long employeeID, String employeeFirstName, String employeeLastName, String gender, LocalDate dob,
			String idNumber, String address, String nationality, String taxNumber, Position position, Branch branch,
			Store store, String accountID, String telephone, String email, double salary, LocalDate createdDate,
			String status, List<Customer> customers) {
		super();
		this.employeeID = employeeID;
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.gender = gender;
		this.dob = dob;
		this.idNumber = idNumber;
		this.address = address;
		this.nationality = nationality;
		this.taxNumber = taxNumber;
		this.position = position;
		this.branch = branch;
		this.store = store;
		this.accountID = accountID;
		this.telephone = telephone;
		this.email = email;
		this.salary = salary;
		this.createdDate = createdDate;
		this.status = status;
		this.customers = customers;
	}

	public Employee(String employeeFirstName, String employeeLastName, String gender, LocalDate dob, String idNumber,
			String address, String nationality, String taxNumber, Position position, Branch branch, Store store,
			String accountID, String telephone, String email, double salary, String status) {
		this.employeeFirstName = employeeFirstName;
		this.employeeLastName = employeeLastName;
		this.gender = gender;
		this.dob = dob;
		this.idNumber = idNumber;
		this.address = address;
		this.nationality = nationality;
		this.taxNumber = taxNumber;
		this.position = position;
		this.branch = branch;
		this.store = store;
		this.accountID = accountID;
		this.telephone = telephone;
		this.email = email;
		this.salary = salary;
		this.status = status;
		this.createdDate = LocalDate.now(); // Sets the current date as createdDate
	}

	// Getters and Setters

	public long getEmployeeID() {
		return employeeID;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void setEmployeeID(long employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeFirstName() {
		return employeeFirstName;
	}

	public void setEmployeeFirstName(String employeeFirstName) {
		this.employeeFirstName = employeeFirstName;
	}

	public String getEmployeeLastName() {
		return employeeLastName;
	}

	public void setEmployeeLastName(String employeeLastName) {
		this.employeeLastName = employeeLastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
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

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
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
		return "Employee [employeeID=" + employeeID + ", employeeFirstName=" + employeeFirstName + ", employeeLastName="
				+ employeeLastName + ", gender=" + gender + ", dob=" + dob + ", idNumber=" + idNumber + ", address="
				+ address + ", nationality=" + nationality + ", taxNumber=" + taxNumber + ", position=" + position
				+ ", branch=" + branch + ", store=" + store + ", accountID=" + accountID + ", telephone=" + telephone
				+ ", email=" + email + ", salary=" + salary + ", createdDate=" + createdDate + ", status=" + status
				+ "]";
	}
}
