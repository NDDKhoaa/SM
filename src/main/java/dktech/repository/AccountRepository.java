package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
