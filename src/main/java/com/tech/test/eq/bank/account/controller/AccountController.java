package com.tech.test.eq.bank.account.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.test.eq.bank.account.exception.InsufficientFundsException;
import com.tech.test.eq.bank.account.model.Account;
import com.tech.test.eq.bank.account.model.TransactionsReport;
import com.tech.test.eq.bank.account.service.AccountService;
import com.tech.test.eq.bank.account.transaction.TransactionRequest;
import com.tech.test.eq.bank.transactions.controller.TransactionsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {


	private final AccountService service;
	private final TransactionsService transactionsService;

	public AccountController(AccountService service, TransactionsService transactionsService) {
		this.service = service;
		this.transactionsService = transactionsService;
	}
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
		Account account = service.retrieveAccount(id);
		return ResponseEntity.ok(account);

	}
	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> accounts = service.getAllAccounts();
		if (accounts.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(accounts);
		}
	}
	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody @Valid Account newAccount) {
		Account createdAccount = service.createAccount(newAccount);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
	}


	@PostMapping("/{id}/deposit")
	public ResponseEntity<Account> depositToAccount(@PathVariable Long id, @RequestBody @Valid TransactionRequest depositRequest) {
		service.depositToAccount(id, depositRequest.getAmount());
		return ResponseEntity.noContent().build();

	}
	@PostMapping("/{id}/withdraw")
	public ResponseEntity<Account> withdrawFromAccount(@PathVariable Long id, @RequestBody @Valid TransactionRequest withdrawRequest) throws InsufficientFundsException {
		service.withdrawFromAccount(id, withdrawRequest.getAmount());
		return ResponseEntity.noContent().build();

	}
	
	@GetMapping("/{id}/transactions")
	public ResponseEntity<List<TransactionsReport>> retrieveTransactionHistory(@PathVariable Long id){
		List<TransactionsReport> report = transactionsService.retrieveTransactionsHistory(id);
		if (report.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(report);
		}
	}

}
