package net.ouabane.digitalbankingback.repositories;

import net.ouabane.digitalbankingback.entities.BankAccount;
import net.ouabane.digitalbankingback.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
