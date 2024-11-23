
// Authorize.java
package dktech.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "authorizations")
public class Authorize {

	@Id
	@Column(name = "authorize_id", nullable = false)
	private long authorizeID;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "authorize_group_id", nullable = false)
	private AuthorizeGroup authorizeGroup;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "sanction_id", nullable = false)
	private Sanction sanction;

	// Constructors, Getters, Setters, and toString

	public Authorize() {
		super();
	}

	public Authorize(AuthorizeGroup authorizeGroup, Account account, Sanction sanction) {
		this.authorizeGroup = authorizeGroup;
		this.account = account;
		this.sanction = sanction;
	}

	public long getAuthorizeID() {
		return authorizeID;
	}

	public void setAuthorizeID(long authorizeID) {
		this.authorizeID = authorizeID;
	}

	public AuthorizeGroup getAuthorizeGroup() {
		return authorizeGroup;
	}

	public void setAuthorizeGroup(AuthorizeGroup authorizeGroup) {
		this.authorizeGroup = authorizeGroup;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Sanction getSanction() {
		return sanction;
	}

	public void setSanction(Sanction sanction) {
		this.sanction = sanction;
	}

	@JsonProperty("authorizeGroupID")
	public Long getAuthorizeGroupID() {
		return authorizeGroup != null ? authorizeGroup.getAuthorizeGroupID() : null;
	}

	@JsonProperty("accountID")
	public Long getAccountID() {
		return account != null ? account.getAccountID() : null;
	}

	@JsonProperty("sanctionID")
	public Long getSanctionID() {
		return sanction != null ? sanction.getSanctionID() : null;
	}

	@Override
	public String toString() {
		return "Authorize [authorizeID=" + authorizeID + ", authorizeGroup=" + authorizeGroup + ", account=" + account
				+ ", sanction=" + sanction + "]";
	}
}
