package com.tech.test.eq.bank.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tech.test.eq.bank.account.enums.TransactionType;
import com.tech.test.eq.bank.account.exception.AccountNotFoundException;
import com.tech.test.eq.bank.account.exception.InsufficientFundsException;
import com.tech.test.eq.bank.account.model.Account;
import com.tech.test.eq.bank.account.repository.AccountRepository;
import com.tech.test.eq.bank.customer.model.Customer;
import com.tech.test.eq.bank.customer.service.CustomerService;
import com.tech.test.eq.bank.transactions.service.TransactionsService;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository repository;
    
    @Mock
    private CustomerService customerService;
    
    @Mock
    private TransactionsService transactionsService;
    
    @InjectMocks
    private AccountService accountService;

    @Test
    void testCreateAccount() {
        Customer customer = new Customer();
        customer.setId(1L);
        Account newAccount = new Account();
        newAccount.setCustomer(customer);
        when(customerService.retrieveCustomer(1L)).thenReturn(customer);
        when(repository.save(newAccount)).thenReturn(newAccount);
        Account createdAccount = accountService.createAccount(newAccount);
        assertNotNull(createdAccount);
        assertEquals(customer, createdAccount.getCustomer());
        verify(transactionsService).recordTransaction(createdAccount, createdAccount.getBalance(), TransactionType.CREATED);
    }

    @Test
    void testRetrieveAccount() {
        Long accountId = 1L;
        Account account = new Account();
        when(repository.findById(accountId)).thenReturn(Optional.of(account));
        Account retrievedAccount = accountService.retrieveAccount(accountId);
        assertNotNull(retrievedAccount);
        assertEquals(account, retrievedAccount);
    }

    @Test
    void testRetrieveAccountNotFound() {
        Long accountId = 1L;
        when(repository.findById(accountId)).thenReturn(Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.retrieveAccount(accountId));
    }

    @Test
    void testGetAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        when(repository.findAll()).thenReturn(accounts);
        List<Account> retrievedAccounts = accountService.getAllAccounts();
        assertEquals(accounts, retrievedAccounts);
    }



    @Test
    void testWithdrawFromAccountInsufficientFunds() {
        Long accountId = 1L;
        BigDecimal amount = BigDecimal.valueOf(600.0);
        Account account = new Account();
        account.setBalance(BigDecimal.valueOf(500.0));
        when(repository.findById(accountId)).thenReturn(Optional.of(account));
        assertThrows(InsufficientFundsException.class, () -> accountService.withdrawFromAccount(accountId, amount));
    }

}
