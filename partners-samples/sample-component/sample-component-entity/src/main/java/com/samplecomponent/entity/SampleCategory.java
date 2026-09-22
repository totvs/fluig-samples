package com.samplecomponent.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SCO_CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = {"CAT_TENANT_ID", "CAT_NAME"}, name = "scp_category_pk"))
@NamedQueries({
		@NamedQuery(
				name = SampleCategory.FIND_BY_NAME,
				query = "SELECT sc FROM SampleCategory sc WHERE sc.tenantId = :tenantId AND LOWER(sc.name) LIKE :name ORDER by sc.id",
				hints = {@QueryHint(name = "parameters", value = "java.lang.Long tenantId")})
})
public class SampleCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String FIND_BY_NAME = "SampleCategory.findByName";

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "sco_category_seq")
	@TableGenerator(name = "sco_category_seq", table = "SCO_ID_GEN", pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VALUE", pkColumnValue = "SCO_CATEGORY_SEQ", allocationSize = 1)
	@Column(name = "CAT_ID")
	private Long id;

	@Column(name = "CAT_NAME", length = 50)
	@NotBlank
	@Size(max = 50)
	@NotNull
	private String name;

	@Column(name = "CAT_TENANT_ID")
	@NotNull
	private Long tenantId;

	public SampleCategory() {
	}

	public SampleCategory(Long id, String name, Long tenantId) {
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

	@Override
	public String toString() {
		return "SampleCategory{" +
				"id=" + id +
				", name='" + name + '\'' +
				", tenantId=" + tenantId +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SampleCategory that = (SampleCategory) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(name, that.name) &&
				Objects.equals(tenantId, that.tenantId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, tenantId);
	}
}
