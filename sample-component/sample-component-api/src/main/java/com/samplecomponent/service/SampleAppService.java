package com.samplecomponent.service;

import java.util.List;

import javax.ejb.Remote;

import com.samplecomponent.vo.SampleAppVO;

@Remote
public interface SampleAppService {

	public static final String JNDI_NAME = "service/sample-app";
	public static final String JNDI_REMOTE_NAME = "java:global/fluig/store/" + JNDI_NAME;
	
	long create(SampleAppVO vo);
	
	SampleAppVO get(long id);
	
	void update(SampleAppVO vo);
	
	void delete(long id);
	
	List<SampleAppVO> find(String text, int limit, int offset);
}
