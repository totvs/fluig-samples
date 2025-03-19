package com.talent.service;

import javax.ejb.Remote;

@Remote
public interface TalentAiService {

	public static final String JNDI_NAME = "service/talent-ai";
	public static final String JNDI_REMOTE_NAME = "java:global/fluig/" + JNDI_NAME;
	
	String get(long id);
	
}
