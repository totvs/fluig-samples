package com.samplecomponent.store;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

@Entity
@Table(name = "SCO_CATEGORY", indexes = {
		@Index(columnList = "TENANT_ID, NAME", name = "scp_category_idx", unique = true)})
@NamedQueries({
	@NamedQuery(name = SampleCategory.FIND_ALL, query = "SELECT sc FROM SampleCategory sc WHERE sc.tenantId = :tenantId", hints = {
			@QueryHint(name = "parameters", value = "java.lang.Long tenantId")}),
	@NamedQuery(name = SampleCategory.FIND_BY_NAME, query = "SELECT sc FROM SampleCategory sc WHERE LOWER(sc.name) LIKE %:name% AND sc.tenantId = :tenantId)", hints = {
			@QueryHint(name = "parameters", value = "java.lang.Long tenantId, java.lang.String name")})})
public class SampleCategory implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "SampleCategory.findAll";
	public static final String FIND_BY_NAME = "SampleCategory.findByName";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	@Column(name = "TENANT_ID", nullable = false)
	private Long tenantId;

	public SampleCategory() {
		super();
	}

	public SampleCategory(Long id, String name, Long tenantId) {
		super();
		this.id = id;
		this.name = name;
		this.tenantId = tenantId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

}
