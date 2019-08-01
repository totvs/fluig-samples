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

@Entity
@Table(name = "SCO_APPS", indexes = {
		@Index(columnList = "TENANT_ID, NAME, DEVELOPER", name = "scp_apps_idx", unique = true)})
@NamedQueries({
	@NamedQuery(name = SampleApp.FIND_ALL, query = "SELECT sa FROM SampleApp sa WHERE sa.tenantId = :tenantId", hints = {
			@QueryHint(name = "parameters", value = "java.lang.Long tenantId")}),

	@NamedQuery(name = SampleApp.FIND_BY_DEVELOPER, query = "SELECT sa FROM SampleApp sa WHERE sa.tenantId = :tenantId AND sa.developer = :developer", hints = {
			@QueryHint(name = "parameters", value = "java.lang.Long tenantId, java.lang.String developer")}),

	@NamedQuery(name = SampleApp.FIND_BY_CATEGORY, query = "SELECT sa FROM SampleApp sa WHERE sa.tenantId = :tenantId AND sa.category = :category", hints = {
			@QueryHint(name = "parameters", value = "java.lang.Long tenantId, java.lang.String category")}),

	@NamedQuery(name = SampleApp.FIND_BY_NAME, query = "SELECT sa FROM SampleApp sa WHERE LOWER(sa.name) LIKE :name AND sa.tenantId = :tenantId", hints = {
			@QueryHint(name = "parameters", value = "java.lang.Long tenantId, java.lang.String name")})})
public class SampleApp implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "SampleApp.findAll";
	public static final String FIND_BY_NAME = "SampleApp.findByName";
	public static final String FIND_BY_CATEGORY = "SampleApp.findByCategory";
	public static final String FIND_BY_DEVELOPER = "SampleApp.findByDeveloper";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", nullable = false, unique = true)
	private String name;

	@Column(name = "DEVELOPER", nullable = false)
	private String developer;

	@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID", nullable = false)
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private SampleCategory category;

	@Column(name = "TENANT_ID", nullable = false)
	private Long tenantId;

	public SampleApp() {
		super();
	}

	public SampleApp(Long id, String name, String developer,
			SampleCategory category, Long tenantId) {
		this.id = id;
		this.name = name;
		this.developer = developer;
		this.category = category;
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

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public SampleCategory getCategory() {
		return category;
	}

	public void setCategory(SampleCategory category) {
		this.category = category;
	}

	public Long getTenantId() {
		return tenantId;
	}

	public void setTenantId(Long tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String toString() {
		return "SampleApp [id=" + id + ", name=" + name + ", developer="
				+ developer + ", category=" + category + ", tenantId="
				+ tenantId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result
				+ ((developer == null) ? 0 : developer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((tenantId == null) ? 0 : tenantId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SampleApp other = (SampleApp) obj;
		if (category == null) {
			if (other.category != null) {
				return false;
			}
		} else if (!category.equals(other.category)) {
			return false;
		}
		if (developer == null) {
			if (other.developer != null) {
				return false;
			}
		} else if (!developer.equals(other.developer)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (tenantId == null) {
			if (other.tenantId != null) {
				return false;
			}
		} else if (!tenantId.equals(other.tenantId)) {
			return false;
		}
		return true;
	}

}
