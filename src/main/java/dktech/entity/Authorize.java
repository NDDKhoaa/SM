package dktech.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "authorizations")
public class Authorize {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	// Getters and Setters
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

	@Override
	public String toString() {
		return "Authorize [authorizeID=" + authorizeID + ", authorizeGroup=" + authorizeGroup + ", account=" + account
				+ ", sanction=" + sanction + "]";
	}

}
