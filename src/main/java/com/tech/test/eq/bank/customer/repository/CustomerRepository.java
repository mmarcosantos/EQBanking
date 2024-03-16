package com.tech.test.eq.bank.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tech.test.eq.bank.customer.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}