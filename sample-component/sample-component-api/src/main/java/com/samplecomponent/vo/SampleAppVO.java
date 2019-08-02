package com.samplecomponent.vo;

import java.io.Serializable;

import com.samplecomponent.entity.SampleCategory;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Objeto de request/response da API Rest, abstraindo o objeto da regra de negócio
 * Pode também ser chamado de DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SampleAppVO implements Serializable {
	
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long id;
    private String name;	
	private String developer;	
	private SampleCategory category;
	private Long tenantId;

}
