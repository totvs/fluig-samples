package com.samplecomponent.entity;

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
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SCO_CATEGORY", indexes = {
		@Index(columnList = "TENANT_ID, NAME", name = "scp_category_idx", unique = true) })
@NamedQueries({
		@NamedQuery(name = SampleCategory.FIND_ALL, query = "SELECT sc FROM SampleCategory sc WHERE sc.tenantId = :tenantId", hints = {
				@QueryHint(name = "parameters", value = "java.lang.Long tenantId") }),
		@NamedQuery(name = SampleCategory.FIND_BY_NAME, query = "SELECT sc FROM SampleCategory sc WHERE LOWER(sc.name) LIKE :name AND sc.tenantId = :tenantId", hints = {
				@QueryHint(name = "parameters", value = "java.lang.Long tenantId, java.lang.String name") }) })

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SampleCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "SampleCategory.findAll";
	public static final String FIND_BY_NAME = "SampleCategory.findByName";
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@Id private Long id;
	
	@Column(name = "NAME", unique = true)	
	@NotNull private String name;
	
	@Column(name = "TENANT_ID")
	@NotNull private Long tenantId;
}