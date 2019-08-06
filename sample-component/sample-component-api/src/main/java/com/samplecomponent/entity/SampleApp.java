package com.samplecomponent.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SCO_APPS",
	uniqueConstraints = @UniqueConstraint(columnNames = {"DEVELOPER", "TENANT_ID", "NAME"}, name = "scp_apps_pk"))
@NamedQueries({
		@NamedQuery(name = SampleApp.FIND_ALL, query = "SELECT sa FROM SampleApp sa WHERE sa.tenantId = :tenantId", hints = {
				@QueryHint(name = "parameters", value = "java.lang.Long tenantId") }),

		@NamedQuery(name = SampleApp.FIND_BY_DEVELOPER, query = "SELECT sa FROM SampleApp sa WHERE sa.tenantId = :tenantId AND sa.developer = :developer", hints = {
				@QueryHint(name = "parameters", value = "java.lang.Long tenantId, java.lang.String developer") }),

		@NamedQuery(name = SampleApp.FIND_BY_CATEGORY, query = "SELECT sa FROM SampleApp sa WHERE sa.tenantId = :tenantId AND sa.category = :category", hints = {
				@QueryHint(name = "parameters", value = "java.lang.Long tenantId, java.lang.String category") }),

		@NamedQuery(name = SampleApp.FIND_BY_NAME, query = "SELECT sa FROM SampleApp sa WHERE LOWER(sa.name) LIKE :name AND sa.tenantId = :tenantId", hints = {
				@QueryHint(name = "parameters", value = "java.lang.Long tenantId, java.lang.String name") }) })

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SampleApp implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "SampleApp.findAll";
	public static final String FIND_BY_NAME = "SampleApp.findByName";
	public static final String FIND_BY_CATEGORY = "SampleApp.findByCategory";
	public static final String FIND_BY_DEVELOPER = "SampleApp.findByDeveloper";
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@Id private Long id;
		
	@Column(name = "NAME", unique = true)
	@NotNull private String name;

	@Column(name = "DEVELOPER")	
	@NotNull private String developer;

	@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@NotNull private SampleCategory category;

	@Column(name = "TENANT_ID")
	@NotNull private Long tenantId;
}