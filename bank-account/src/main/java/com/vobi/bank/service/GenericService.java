package com.vobi.bank.service;

import java.util.List;
import java.util.Optional;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */
public interface GenericService<T, ID> {

	public List<T> findAll();

	public Optional<T> findById(ID id);

	public T save(T entity) throws Exception;

	public T update(T entity) throws Exception;

	public void delete(T entity) throws Exception;

	public void deleteById(ID id) throws Exception;

	public void validate(T entity) throws Exception;

	public Long count();

}
