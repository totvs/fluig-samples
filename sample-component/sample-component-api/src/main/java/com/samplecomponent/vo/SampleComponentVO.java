package com.samplecomponent.vo;

import java.io.Serializable;

/**
 * Objeto de request/response da API, abstraindo o objeto da regra de negócio
 * Pode também ser chamado de DTO
 */
public class SampleComponentVO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;    
    

    public SampleComponentVO() {		
	}

	public SampleComponentVO(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
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

	@Override
	public String toString() {
		return "SampleComponentVO [id=" + id + ", name=" + name + "]";
	}

}
