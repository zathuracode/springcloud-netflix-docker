package com.vobi.bank.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vobi.bank.domain.Account;
import com.vobi.bank.domain.Customer;
import com.vobi.bank.domain.RegisteredAccount;
import com.vobi.bank.exception.ZMessManager;
import com.vobi.bank.repository.CustomerRepository;
import com.vobi.bank.utility.Utilities;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */

@Scope("singleton")
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private Validator validator;

	@Override
	public void validate(Customer customer) throws ConstraintViolationException {

		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return customerRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> findAll() {
		log.debug("finding all Customer instances");
		return customerRepository.findAll();
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Customer save(Customer entity) throws Exception {
		log.debug("saving Customer instance");

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Customer");
		}

		validate(entity);

		if (customerRepository.existsById(entity.getCustId())) {
			throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
		}

		return customerRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Customer entity) throws Exception {
		log.debug("deleting Customer instance");

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Customer");
		}

		if (entity.getCustId() == null) {
			throw new ZMessManager().new EmptyFieldException("custId");
		}

		if (customerRepository.existsById(entity.getCustId()) == false) {
			throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
		}

		findById(entity.getCustId()).ifPresent(entidad -> {
			List<Account> accounts = entidad.getAccounts();
			if (Utilities.validationsList(accounts) == true) {
				throw new ZMessManager().new DeletingException("accounts");
			}
			List<RegisteredAccount> registeredAccounts = entidad.getRegisteredAccounts();
			if (Utilities.validationsList(registeredAccounts) == true) {
				throw new ZMessManager().new DeletingException("registeredAccounts");
			}
		});

		customerRepository.deleteById(entity.getCustId());
		log.debug("delete Customer successful");

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteById(Integer id) throws Exception {
		log.debug("deleting Customer instance");
		if (id == null) {
			throw new ZMessManager().new EmptyFieldException("custId");
		}
		if (customerRepository.existsById(id)) {
			delete(customerRepository.findById(id).get());
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Customer update(Customer entity) throws Exception {

		log.debug("updating Customer instance");

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Customer");
		}

		validate(entity);

		if (customerRepository.existsById(entity.getCustId()) == false) {
			throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
		}

		return customerRepository.save(entity);

	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Customer> findById(Integer custId) {
		log.debug("getting Customer instance");
		return customerRepository.findById(custId);
	}

}
