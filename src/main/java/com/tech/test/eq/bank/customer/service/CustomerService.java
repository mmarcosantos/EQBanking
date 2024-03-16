package com.tech.test.eq.bank.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tech.test.eq.bank.customer.exception.CustomerNotFoundException;
import com.tech.test.eq.bank.customer.model.Customer;
import com.tech.test.eq.bank.customer.repository.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository repository;
	public CustomerService(CustomerRepository repository) {
		this.repository = repository;
	}

	public Customer retrieveCustomer(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
	}

	public List<Customer> retrieveAllCustomers() {
		return repository.findAll();
	}

	public Customer createCustomer(Customer newCustomer) {
		return repository.save(newCustomer);
	}

	public Customer updateCustomerName(Long id, Customer updatedCustomer) {
		Customer existingCustomer = repository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
		BeanUtils.copyProperties(updatedCustomer, existingCustomer, "id");
		return repository.save(existingCustomer);

	}

}
