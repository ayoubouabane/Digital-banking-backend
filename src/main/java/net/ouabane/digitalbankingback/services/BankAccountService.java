package net.ouabane.digitalbankingback.services;

import net.ouabane.digitalbankingback.dtos.*;
import net.ouabane.digitalbankingback.entities.BankAccount;
import net.ouabane.digitalbankingback.entities.CurrentAccount;
import net.ouabane.digitalbankingback.entities.Customer;
import net.ouabane.digitalbankingback.entities.SavingAccount;
import net.ouabane.digitalbankingback.exceptions.BalanceNotSufficientException;
import net.ouabane.digitalbankingback.exceptions.BankAccountNotFoundException;
import net.ouabane.digitalbankingback.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    //CustomerDTO saveCustomer(CustomerDTO customerDTO);
    //CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;//throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;//throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;// throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws  CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
