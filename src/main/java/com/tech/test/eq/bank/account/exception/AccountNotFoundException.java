package com.tech.test.eq.bank.account.exception;

public class AccountNotFoundException extends RuntimeException {
	public AccountNotFoundException(String message) {
        super(message);
    }
}

