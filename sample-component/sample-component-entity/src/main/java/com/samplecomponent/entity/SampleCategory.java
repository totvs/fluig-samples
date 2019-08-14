package com.samplecomponent.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "SCO_CATEGORY", uniqueConstraints = @UniqueConstraint(columnNames = { "TENANT_ID", "NAME" }, name = "scp_category_pk"))
@NamedQueries({
		@NamedQuery(
				name = SampleCategory.FIND_BY_NAME, query = "SELECT sc FROM SampleCategory sc WHERE sc.tenantId = :tenantId AND LOWER(sc.name) LIKE :name ORDER by sc.id",
				hints = {@QueryHint(name = "parameters", value = "java.lang.Long tenantId")
		})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SampleCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String FIND_BY_NAME = "SampleCategory.findByName";

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@Id
	private Long id;

	@Column(name = "NAME", unique = true)
	@NotNull
	private String name;

	@Column(name = "TENANT_ID")
	@NotNull
	private Long tenantId;
}