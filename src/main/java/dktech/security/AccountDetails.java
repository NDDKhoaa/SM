
// src/main/java/dktech/security/AccountDetails.java
package dktech.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dktech.entity.Account;
import dktech.entity.Authorize;
import dktech.entity.Sanction;

public class AccountDetails implements UserDetails {

	private final Account account;

	public AccountDetails(Account account) {
		this.account = account;
	}
	
    public Account getAccount() {
        return account;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return account.getAuthorizeGroups().stream().map(group -> (GrantedAuthority) group::getName)
				.collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getUsername();
	}

	public String getFullName() {
		return account.getEmployee().getEmployeeFirstName() + " " + account.getEmployee().getEmployeeLastName();
	}

	public Set<Sanction> getSanctions() {
		// Fetch authorizations explicitly
		Set<Authorize> authorizations = account.getAuthorizations();
		Set<Sanction> groupSanctions = account.getAuthorizeGroups().stream()
				.flatMap(group -> group.getSanctions().stream()).collect(Collectors.toSet());

		Set<Sanction> directSanctions = authorizations.stream().map(Authorize::getSanction).collect(Collectors.toSet());

		groupSanctions.addAll(directSanctions);
		return groupSanctions;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
