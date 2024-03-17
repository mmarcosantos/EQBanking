package com.tech.test.eq.bank.transactions.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tech.test.eq.bank.account.enums.TransactionType;
import com.tech.test.eq.bank.account.model.Account;
import com.tech.test.eq.bank.account.model.TransactionsReport;
import com.tech.test.eq.bank.transactions.repository.TransactionsRepository;

@ExtendWith(MockitoExtension.class)
class TransactionsServiceTest {

    @Mock
    private TransactionsRepository repository;
    
    @InjectMocks
    private TransactionsService transactionsService;

    @Test
    void testRetrieveTransactionsHistory() {
        Long accountId = 1L;
        List<TransactionsReport> transactions = new ArrayList<>();
        when(repository.findAllByAccountId(accountId)).thenReturn(transactions);  
        List<TransactionsReport> retrievedTransactions = transactionsService.retrieveTransactionsHistory(accountId);
        assertEquals(transactions, retrievedTransactions);
    }

    @Test
    void testRecordTransaction() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(BigDecimal.valueOf(1000));
        BigDecimal amount = BigDecimal.valueOf(100.0);
        TransactionType type = TransactionType.DEPOSIT;
        transactionsService.recordTransaction(account, amount, type);
        verify(repository).save(any(TransactionsReport.class));
    }

}
