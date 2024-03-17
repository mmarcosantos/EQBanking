package com.tech.test.eq.bank.transactions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.test.eq.bank.account.model.TransactionsReport;

public interface TransactionsRepository extends JpaRepository<TransactionsReport, Long> {

	List<TransactionsReport> findAllByAccountId(Long id);

} 