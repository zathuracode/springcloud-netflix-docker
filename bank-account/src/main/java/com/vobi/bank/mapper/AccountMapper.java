package com.vobi.bank.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.vobi.bank.domain.Account;
import com.vobi.bank.dto.AccountDTO;

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
public interface AccountMapper {
	@Mapping(source = "customer.custId", target = "custId_Customer")
	public AccountDTO accountToAccountDTO(Account account);

	@Mapping(source = "custId_Customer", target = "customer.custId")
	public Account accountDTOToAccount(AccountDTO accountDTO);

	public List<AccountDTO> listAccountToListAccountDTO(List<Account> accounts);

	public List<Account> listAccountDTOToListAccount(List<AccountDTO> accountDTOs);
}
