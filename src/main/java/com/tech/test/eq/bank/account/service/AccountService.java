package com.tech.test.eq.bank.account.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.tech.test.eq.bank.account.enums.TransactionType;
import com.tech.test.eq.bank.account.exception.AccountNotFoundException;
import com.tech.test.eq.bank.account.exception.InsufficientFundsException;
import com.tech.test.eq.bank.account.model.Account;
import com.tech.test.eq.bank.account.repository.AccountRepository;
import com.tech.test.eq.bank.customer.model.Customer;
import com.tech.test.eq.bank.customer.service.CustomerService;
import com.tech.test.eq.bank.transactions.controller.TransactionsService;

@Service
public class AccountService {
	
    private final AccountRepository repository;

	private final CustomerService customerService;
	
	private final TransactionsService transactionsService;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, TransactionsService transactionsService) {
		this.repository = accountRepository;
		this.customerService = customerService;
		this.transactionsService = transactionsService;
	}
    @Transactional
	public Account createAccount(@RequestBody Account newAccount){
		Customer retrieveCustomer = customerService.retrieveCustomer(newAccount.getCustomer().getId());
        newAccount.setCustomer(retrieveCustomer);
        return repository.save(newAccount);
		
	}

	public Account retrieveAccount(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));
	}

	public List<Account> getAllAccounts() {
		 return repository.findAll();
	}

   @Transactional	
	public Account depositToAccount(Long id, BigDecimal amount) {
	   Account retrievedAccount = retrieveAccount(id);
	   retrievedAccount.setBalance(retrievedAccount.getBalance().add(amount));
	   transactionsService.recordTransaction(retrievedAccount,amount,TransactionType.DEPOSIT);
       return repository.save(retrievedAccount);
	}
   @Transactional
	public Account withdrawFromAccount(Long id, BigDecimal amount) throws InsufficientFundsException {
	   Account retrievedAccount = retrieveAccount(id);
	    if (retrievedAccount.getBalance().compareTo(amount) < 0) {
	    	throw new InsufficientFundsException("Insufficient funds");
	    }

	   retrievedAccount.setBalance(retrievedAccount.getBalance().subtract(amount));
	   transactionsService.recordTransaction(retrievedAccount,amount,TransactionType.WITHDRAWAL);
       return repository.save(retrievedAccount);
	}
   

}
