package dktech.services;

import dktech.entity.Account;
import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    Account getAccountById(Long id);
    Account createAccount(Account account);
    Account updateAccount(Long id, Account account);
    void deleteAccount(Long id);
}
