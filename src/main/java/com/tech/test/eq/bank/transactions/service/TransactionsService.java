package com.tech.test.eq.bank.transactions.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tech.test.eq.bank.account.enums.TransactionType;
import com.tech.test.eq.bank.account.model.Account;
import com.tech.test.eq.bank.account.model.TransactionsReport;
import com.tech.test.eq.bank.transactions.repository.TransactionsRepository;

@Service
public class TransactionsService {
	private final TransactionsRepository repository;

	
	public TransactionsService(TransactionsRepository repository) {
		this.repository = repository;
	}


	public List<TransactionsReport> retrieveTransactionsHistory(Long id) {
		return repository.findAllByAccountId(id);
	}

	
	public void recordTransaction(Account retrievedAccount, BigDecimal amount, TransactionType type) {
		TransactionsReport transaction = new TransactionsReport();
        transaction.setAccountId(retrievedAccount.getId());
        transaction.setAmount(amount);
        transaction.setBalance(retrievedAccount.getBalance());
        transaction.setType(type);
        transaction.setTimestamp(LocalDateTime.now());
        repository.save(transaction);
	}

}
