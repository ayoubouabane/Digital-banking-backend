package net.ouabane.digitalbankingback;

import ch.qos.logback.core.net.SyslogOutputStream;
import net.ouabane.digitalbankingback.dtos.BankAccountDTO;
import net.ouabane.digitalbankingback.dtos.CurrentBankAccountDTO;
import net.ouabane.digitalbankingback.dtos.CustomerDTO;
import net.ouabane.digitalbankingback.dtos.SavingBankAccountDTO;
import net.ouabane.digitalbankingback.entities.*;
import net.ouabane.digitalbankingback.enums.AccountStatus;
import net.ouabane.digitalbankingback.enums.OperationType;
import net.ouabane.digitalbankingback.exceptions.BalanceNotSufficientException;
import net.ouabane.digitalbankingback.exceptions.BankAccountNotFoundException;
import net.ouabane.digitalbankingback.exceptions.CustomerNotFoundException;
import net.ouabane.digitalbankingback.repositories.AccountOperationRepository;
import net.ouabane.digitalbankingback.repositories.BankAccountRepository;
import net.ouabane.digitalbankingback.repositories.CustomerRepository;
import net.ouabane.digitalbankingback.services.BankAccountService;
import net.ouabane.digitalbankingback.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingBackApplication.class, args);
    }
    // Consultation
    @Bean
    /*
    CommandLineRunner commandLineRunner (BankService bankService) {
        return args -> {
            bankService.consulter();
        };
    }
    */
    CommandLineRunner commandLineRunner (BankAccountService bankAccountService) {
        return args -> {
            Stream.of("Ayoub", "Anas", "Mohamed", "Imane").forEach(name -> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            //for (CustomerDTO customer : bankAccountService.listCustomers()) {
            bankAccountService.listCustomers().forEach(customer->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random() * 90000, 9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random() * 120000, 5.5, customer.getId());
                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount : bankAccounts) {
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getId();
                    } else{
                        accountId=((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId, 10000 + Math.random() * 120000, "Credit");
                    bankAccountService.debit(accountId, 1000 + Math.random() * 2000, "Debit");
                }
            }
        };
    }
    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Ayoub", "Anas", "Mohamed","Imane").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust ->{
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(98000);
                bankAccountRepository.save(currentAccount);


                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*85000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(2.5);
                bankAccountRepository.save(savingAccount);

            });
            bankAccountRepository.findAll().forEach(acc->{
                for (int i =0;i<10;i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setBankAccount(acc);
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*15000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }
}
