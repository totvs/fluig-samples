package com.samplecomponent.service;

import java.util.List;

import javax.ejb.Remote;

import com.samplecomponent.vo.SampleCategoryVO;

@Remote
public interface SampleCategoryService {

	public static final String JNDI_NAME = "service/sample-category";
	public static final String JNDI_REMOTE_NAME = "java:global/fluig/store/" + JNDI_NAME;
	
	long create(SampleCategoryVO vo);
	
	SampleCategoryVO get(long id);
	
	void update(SampleCategoryVO vo);
	
	void delete(long id);
	
	List<SampleCategoryVO> find(String text, int limit, int offset);
}
