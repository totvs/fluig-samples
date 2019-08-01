package com.samplecomponent.service.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.samplecomponent.service.SampleAppService;

@Remote
@Stateless(mappedName = SampleAppService.JNDI_NAME, name = SampleAppService.JNDI_NAME)
public class SampleAppServiceImpl implements SampleAppService{

	public SampleAppServiceImpl() {
	}

}
