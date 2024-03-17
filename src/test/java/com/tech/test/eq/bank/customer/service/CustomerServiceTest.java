package com.tech.test.eq.bank.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tech.test.eq.bank.customer.exception.CustomerNotFoundException;
import com.tech.test.eq.bank.customer.model.Customer;
import com.tech.test.eq.bank.customer.repository.CustomerRepository;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	 @Mock
	    private CustomerRepository repository;
	    
	    @InjectMocks
	    private CustomerService customerService;

	    @Test
	    void testRetrieveCustomer() {
	        Long customerId = 1L;
	        Customer customer = new Customer();
	        when(repository.findById(customerId)).thenReturn(Optional.of(customer));
	        Customer retrievedCustomer = customerService.retrieveCustomer(customerId);
	        assertNotNull(retrievedCustomer);
	        assertEquals(customer, retrievedCustomer);
	    }

	    @Test
	    void testRetrieveCustomerNotFound() {
	        Long customerId = 1L;
	        when(repository.findById(customerId)).thenReturn(Optional.empty());
	        assertThrows(CustomerNotFoundException.class, () -> customerService.retrieveCustomer(customerId));
	    }

	    @Test
	    void testRetrieveAllCustomers() {
	        List<Customer> customers = new ArrayList<>();
	        when(repository.findAll()).thenReturn(customers);
	        List<Customer> retrievedCustomers = customerService.retrieveAllCustomers();
	        assertEquals(customers, retrievedCustomers);
	    }

	    @Test
	    void testCreateCustomer() {
	        Customer newCustomer = new Customer();
	        when(repository.save(newCustomer)).thenReturn(newCustomer);
	        Customer createdCustomer = customerService.createCustomer(newCustomer);
	        assertNotNull(createdCustomer);
	        assertEquals(newCustomer, createdCustomer);
	    }

	    @Test
	    void testUpdateCustomerName() {
	        Long customerId = 1L;
	        Customer existingCustomer = new Customer();
	        existingCustomer.setId(customerId);
	        Customer updatedCustomer = new Customer();
	        updatedCustomer.setName("Updated Name");
	        when(repository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
	        when(repository.save(existingCustomer)).thenReturn(existingCustomer);
	        Customer updatedCustomerResult = customerService.updateCustomerName(customerId, updatedCustomer);
	        assertNotNull(updatedCustomerResult);
	        assertEquals(updatedCustomer.getName(), updatedCustomerResult.getName());
	        verify(repository).save(existingCustomer);
	    }
}
