
// AuthorizeGroup.java
package dktech.entity;

import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "authorize_groups")
public class AuthorizeGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authorize_group_id", nullable = false)
	private long authorizeGroupID;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "description", nullable = false, length = 255)
	private String description;

	@ManyToMany(mappedBy = "authorizeGroups")
	private Set<Account> accounts;

	@ManyToMany
	@JoinTable(name = "authorize_group_sanction", joinColumns = @JoinColumn(name = "authorize_group_id"), inverseJoinColumns = @JoinColumn(name = "sanction_id"))
	private Set<Sanction> sanctions;

	// Constructors, Getters, Setters, and toString

	public AuthorizeGroup() {
		super();
	}

	public AuthorizeGroup(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public long getAuthorizeGroupID() {
		return authorizeGroupID;
	}

	public void setAuthorizeGroupID(long authorizeGroupID) {
		this.authorizeGroupID = authorizeGroupID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Sanction> getSanctions() {
		return sanctions;
	}

	public void setSanctions(Set<Sanction> sanctions) {
		this.sanctions = sanctions;
	}

	@JsonProperty("accountIDs")
	public Set<Long> getAccountIDs() {
		return accounts != null ? accounts.stream().map(Account::getAccountID).collect(Collectors.toSet()) : null;
	}

	@JsonProperty("sanctionIDs")
	public Set<Long> getSanctionIDs() {
		return sanctions != null ? sanctions.stream().map(Sanction::getSanctionID).collect(Collectors.toSet()) : null;
	}

	@Override
	public String toString() {
		return "AuthorizeGroup [authorizeGroupID=" + authorizeGroupID + ", name=" + name + ", description="
				+ description + "]";
	}
}
