package dktech.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity
@Table(name = "accounts")
@ToString(exclude = { "employee", "authorizeGroups" })
public class Account {

	@Id
	@Column(name = "account_id", nullable = false)
	private long accountID;

	@Column(name = "username", nullable = false, length = 50)
	private String username;

	@Column(name = "password", nullable = false, length = 100)
	private String password;

	@Column(name = "email", nullable = false, length = 100)
	private String email;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;

	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "account_authorize_group", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "authorize_group_id"))
	private Set<AuthorizeGroup> authorizeGroups;

	@OneToMany(mappedBy = "account")
	@JsonIgnore
	private Set<Authorize> authorizations;

	// Constructors, Getters, Setters

	public Account() {
		super();
	}

	public Account(String username, String password, String email, Employee employee) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.employee = employee;
		this.authorizeGroups = new HashSet<>();
	}

	public long getAccountID() {
		return accountID;
	}

	public void setAccountID(long accountID) {
		this.accountID = accountID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Set<AuthorizeGroup> getAuthorizeGroups() {
		return authorizeGroups;
	}

	public void setAuthorizeGroups(Set<AuthorizeGroup> authorizeGroups) {
		this.authorizeGroups = authorizeGroups;
	}

	public Set<Authorize> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(Set<Authorize> authorizations) {
		this.authorizations = authorizations;
	}

	public Set<Sanction> getSanctions() {
		Set<Sanction> sanctions = new HashSet<>();

		// Add sanctions from authorize groups
		for (AuthorizeGroup group : authorizeGroups) {
			sanctions.addAll(group.getSanctions());
		}

		// Add sanctions from authorizations
		for (Authorize authorize : authorizations) {
			sanctions.add(authorize.getSanction());
		}

		return sanctions;
	}

	@JsonProperty("employeeID")
	public Long getEmployeeID() {
		return employee != null ? employee.getEmployeeID() : null;
	}

	@JsonProperty("authorizeGroupIDs")
	public Set<Long> getAuthorizeGroupIDs() {
		return authorizeGroups != null
				? authorizeGroups.stream().map(AuthorizeGroup::getAuthorizeGroupID).collect(Collectors.toSet())
				: null;
	}

	@JsonProperty("authorizeIDs")
	public Set<Long> getAuthorizeIDs() {
		return authorizations != null
				? authorizations.stream().map(Authorize::getAuthorizeID).collect(Collectors.toSet())
				: null;
	}
}