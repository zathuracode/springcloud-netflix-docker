package com.vobi.bank.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vobi.bank.domain.Customer;
import com.vobi.bank.dto.CustomerDTO;
import com.vobi.bank.mapper.CustomerMapper;
import com.vobi.bank.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 */
@RestController
@RequestMapping("/api/v1/customer")
@CrossOrigin(origins = "*")
@Slf4j
public class CustomerRestController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerMapper customerMapper;

	@GetMapping(value = "/{custId}")
	public ResponseEntity<?> findById(@PathVariable("custId") Integer custId) throws Exception {
		log.debug("Request to findById() Customer");

		Customer customer = (customerService.findById(custId).isPresent() == true)
				? customerService.findById(custId).get()
				: null;

		return ResponseEntity.ok().body(customerMapper.customerToCustomerDTO(customer));
	}

	@GetMapping()
	public ResponseEntity<?> findAll() throws Exception {
		log.debug("Request to findAll() Customer");

		return ResponseEntity.ok().body(customerMapper.listCustomerToListCustomerDTO(customerService.findAll()));
	}

	@PostMapping()
	public ResponseEntity<?> save(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {
		log.debug("Request to save Customer: {}", customerDTO);

		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		customer = customerService.save(customer);

		return ResponseEntity.ok().body(customerMapper.customerToCustomerDTO(customer));
	}

	@PutMapping()
	public ResponseEntity<?> update(@Valid @RequestBody CustomerDTO customerDTO) throws Exception {
		log.debug("Request to update Customer: {}", customerDTO);

		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		customer = customerService.update(customer);

		return ResponseEntity.ok().body(customerMapper.customerToCustomerDTO(customer));
	}

	@DeleteMapping(value = "/{custId}")
	public ResponseEntity<?> delete(@PathVariable("custId") Integer custId) throws Exception {
		log.debug("Request to delete Customer");

		customerService.deleteById(custId);

		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/count")
	public ResponseEntity<?> count() {
		return ResponseEntity.ok().body(customerService.count());
	}
}
