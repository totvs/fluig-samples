package com.samplecomponent.service;

import java.util.List;

import javax.ejb.Remote;

import com.samplecomponent.entity.SampleApp;

@Remote
public interface SampleAppService {

	public static final String JNDI_NAME = "service/sample-app";
	public static final String JNDI_REMOTE_NAME = "java:global/fluig/store/" + JNDI_NAME;
	
	long create(SampleApp vo);
	
	SampleApp get(long id);
	
	void update(SampleApp vo);
	
	void delete(long id);
	
	List<SampleApp> find(String text, int limit, int offset);
}
