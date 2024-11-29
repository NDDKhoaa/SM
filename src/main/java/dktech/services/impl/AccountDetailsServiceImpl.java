package dktech.services.impl;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dktech.entity.Account;
import dktech.repository.AccountRepository;
import dktech.security.AccountDetails;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(AccountDetailsServiceImpl.class);
	private final AccountRepository accountRepository;

	public AccountDetailsServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override

	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.debug("Attempting to load user by username: {}", username);

		Account account = accountRepository.findByUsername(username);

		if (account == null) {
			logger.error("User not found: {}", username);
			throw new UsernameNotFoundException("User not found");
		}

		Hibernate.initialize(account.getAuthorizations());
		Hibernate.initialize(account.getAuthorizeGroups());
		Hibernate.initialize(account.getSanctions()); // Initialize sanctions

		logger.debug("User found: {}", account);
		logger.debug("User found: {}", account.getAuthorizeGroupIDs());

		// Set the Authentication object
		AccountDetails accountDetails = new AccountDetails(account);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(accountDetails,
				null, accountDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return accountDetails;
	}

	public Account findAccountbyUsername(String username) {
		logger.debug("Finding account by username: {}", username);
		return accountRepository.findByUsername(username);
	}

	public Account saveAccount(Account account) {
		logger.debug("Saving account: {}", account);
		return accountRepository.save(account);
	}

	@Transactional
	public Account getAccountWithAuthorizations(String username) {
		Account account = accountRepository.findByUsername(username);

		return account;
	}

}