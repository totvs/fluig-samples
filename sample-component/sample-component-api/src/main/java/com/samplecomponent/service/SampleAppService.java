package com.samplecomponent.service;

import javax.ejb.Remote;

@Remote
public interface SampleAppService {

	public static final String JNDI_NAME = "service/sample-app";
	public static final String JNDI_REMOTE_NAME = "java:global/fluig/store/" + JNDI_NAME;

}
