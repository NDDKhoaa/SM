package dktech.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dktech.entity.Account;
import dktech.repository.AccountRepository;
import dktech.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Account getAccountById(Long id) {
		return accountRepository.findById(id).orElse(null);
	}

	@Override
	public Account createAccount(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public Account updateAccount(Long id, Account account) {
		Optional<Account> existingAccount = accountRepository.findById(id);
		if (existingAccount.isPresent()) {
			Account updatedAccount = existingAccount.get();
			updatedAccount.setUsername(account.getUsername());
			updatedAccount.setPassword(account.getPassword());
			updatedAccount.setEmail(account.getEmail());
			updatedAccount.setEmployee(account.getEmployee());
			return accountRepository.save(updatedAccount);
		}
		return null;
	}

	@Override
	public void deleteAccount(Long id) {
		accountRepository.deleteById(id);
	}
}
