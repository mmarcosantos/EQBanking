package com.tech.test.eq.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tech.test.eq.bank.account.exception.AccountNotFoundException;
import com.tech.test.eq.bank.account.exception.InsufficientFundsException;
import com.tech.test.eq.bank.account.exception.InvalidAmountException;
import com.tech.test.eq.bank.customer.exception.CustomerNotFoundException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<String> handleInvalidAmountException(InvalidAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler({ConstraintViolationException.class,
    	MethodArgumentNotValidException.class,
    	MissingServletRequestParameterException.class
    })
    public ResponseEntity<String> handleConstraintViolationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameters, please check request");
    }
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException ex) {
	  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}

