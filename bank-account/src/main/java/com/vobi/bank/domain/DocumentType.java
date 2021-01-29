package com.vobi.bank.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zathura Code Generator Version 9.0 http://zathuracode.org/
 *         www.zathuracode.org
 * 
 */
@Entity
@Table(name = "document_type", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "doty_id", unique = true, nullable = false)
	@NotNull
	private Integer dotyId;

	@NotNull
	@NotEmpty
	@Size(max = 255)
	@Column(name = "enable", nullable = false)
	private String enable;
	@NotNull
	@NotEmpty
	@Size(max = 255)
	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "documentType")
	private List<Customer> customers = new ArrayList<>();

}