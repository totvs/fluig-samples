package com.samplecomponent.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Objeto de request/response da API Rest, abstraindo o objeto da regra de negócio.
 * Pode também ser chamado de DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SampleCategoryVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	private Long id;
	@EqualsAndHashCode.Include
	private Long tenantId;
	private String name;

	public SampleCategoryVO(String name) {
		this.name = name;
	}
}
