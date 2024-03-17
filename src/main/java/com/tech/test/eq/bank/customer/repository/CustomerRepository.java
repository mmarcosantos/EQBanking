package com.tech.test.eq.bank.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tech.test.eq.bank.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}