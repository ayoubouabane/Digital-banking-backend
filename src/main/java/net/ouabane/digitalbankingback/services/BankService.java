package net.ouabane.digitalbankingback.services;

import jakarta.transaction.Transactional;
import net.ouabane.digitalbankingback.entities.BankAccount;
import net.ouabane.digitalbankingback.entities.CurrentAccount;
import net.ouabane.digitalbankingback.entities.SavingAccount;
import net.ouabane.digitalbankingback.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("1949405d-c16a-4207-ad38-c7157551bcda").orElse(null);
        if (bankAccount != null) {
            System.out.println("***************************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount) {
                System.out.println("Over Draft => " + ((CurrentAccount) bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate => " + ((SavingAccount) bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println(op.getType() + "\n" + op.getOperationDate() + "\n" + op.getAmount());
            });
        }
    }
}
