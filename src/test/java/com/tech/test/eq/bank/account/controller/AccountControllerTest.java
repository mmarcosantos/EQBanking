package com.tech.test.eq.bank.account.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tech.test.eq.bank.account.model.Account;
import com.tech.test.eq.bank.account.repository.AccountRepository;
import com.tech.test.eq.bank.account.service.AccountService;
import com.tech.test.eq.bank.customer.model.Customer;
import com.tech.test.eq.bank.customer.service.CustomerService;
import com.tech.test.eq.bank.transactions.service.TransactionsService;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {
	
    @Mock
    private AccountService service;
    
    @Mock
	private CustomerService customerService;
    
    @Mock
	private TransactionsService transactionsService;
	
    @Mock
    private AccountRepository repository;
    
    @InjectMocks
    private AccountController accountController;
	@Test
	void testGetAccountById() {
		Account mockAccount = new Account();
        mockAccount.setId(1L);
        mockAccount.setBalance(BigDecimal.valueOf(100));
        when(service.retrieveAccount(1L)).thenReturn(mockAccount);
        ResponseEntity<Account> responseEntity = accountController.getAccountById(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getId().longValue());
        assertEquals(BigDecimal.valueOf(100), responseEntity.getBody().getBalance());
    }
	
    @Test
    public void testGetAllAccountsWhenNotEmpty() {
        List<Account> mockAccounts = new ArrayList<>();
        Customer customer1 = new Customer("John");
        Customer customer2 = new Customer("Jane");
        mockAccounts.add(new Account(customer1, BigDecimal.valueOf(100.0)));
        mockAccounts.add(new Account(customer2, BigDecimal.valueOf(200.0)));
        when(service.getAllAccounts()).thenReturn(mockAccounts);
        ResponseEntity<List<Account>> responseEntity = accountController.getAllAccounts();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals("John", responseEntity.getBody().get(0).getCustomer().getName());
        assertEquals(BigDecimal.valueOf(100.0), responseEntity.getBody().get(0).getBalance());
    }

    @Test
    public void testGetAllAccountsWhenEmpty() {
        List<Account> mockAccounts = new ArrayList<>();
        when(service.getAllAccounts()).thenReturn(mockAccounts);
        ResponseEntity<List<Account>> responseEntity = accountController.getAllAccounts();
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
    
    @Test
    void testCreateAccount() {
        Account newAccount = new Account();
        newAccount.setId(1L);
        newAccount.setBalance(BigDecimal.valueOf(100));
        when(service.createAccount(any(Account.class))).thenReturn(newAccount);
        ResponseEntity<Account> responseEntity = accountController.createAccount(newAccount);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(1L, responseEntity.getBody().getId());
        assertEquals(BigDecimal.valueOf(100), responseEntity.getBody().getBalance());
    }
}
