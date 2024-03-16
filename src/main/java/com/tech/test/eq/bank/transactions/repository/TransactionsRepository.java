package com.tech.test.eq.bank.transactions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.test.eq.bank.account.model.TransactionsReport;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsReport, Long> {

	List<TransactionsReport> findAllByAccountId(Long id);

	List<TransactionsReport> findAllByaccountId(Long id);
} 