package com.vobi.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vobi.bank.domain.Customer;
import com.vobi.bank.dto.CustomerDTO;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 *
 *         Mapper Build with MapStruct https://mapstruct.org/ MapStruct is a
 *         code generator that greatly simplifies the implementation of mappings
 *         between Java bean type based on a convention over configuration
 *         approach.
 */
@Mapper
public interface CustomerMapper {
	@Mapping(source = "documentType.dotyId", target = "dotyId_DocumentType")
	public CustomerDTO customerToCustomerDTO(Customer customer);

	@Mapping(source = "dotyId_DocumentType", target = "documentType.dotyId")
	public Customer customerDTOToCustomer(CustomerDTO customerDTO);

	public List<CustomerDTO> listCustomerToListCustomerDTO(List<Customer> customers);

	public List<Customer> listCustomerDTOToListCustomer(List<CustomerDTO> customerDTOs);
}
