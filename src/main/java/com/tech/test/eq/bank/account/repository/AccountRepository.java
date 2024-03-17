package com.tech.test.eq.bank.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.test.eq.bank.account.model.Account;

public interface AccountRepository  extends JpaRepository<Account, Long> {

}
