package com.tech.test.eq.bank.customer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.test.eq.bank.customer.model.Customer;
import com.tech.test.eq.bank.customer.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping("{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		Customer customer = service.retrieveCustomer(id);
		return ResponseEntity.ok(customer);
	}

	@GetMapping()
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = service.retrieveAllCustomers();
		if (customers.isEmpty()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok().body(customers);
		}

	}

	@PostMapping()
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer newCustomer) {
		Customer customer = service.createCustomer(newCustomer);
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}

	@PatchMapping("{id}")
	public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer){
		service.updateCustomerName(id, updatedCustomer);

		return ResponseEntity.noContent().build();

	}
}
