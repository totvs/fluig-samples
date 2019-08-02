package com.samplecomponent.vo;

import java.io.Serializable;

import com.samplecomponent.entity.SampleCategory;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Objeto de request/response da API Rest, abstraindo o objeto da regra de negócio
 * Pode também ser chamado de DTO
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class SampleCategoryVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	private Long id;
	@EqualsAndHashCode.Include
	private Long tenantId;
	private String name;
	
	public static SampleCategoryVO convert(SampleCategory entity) {
		return SampleCategoryVO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.tenantId(entity.getTenantId())
				.build();		
	}
}
