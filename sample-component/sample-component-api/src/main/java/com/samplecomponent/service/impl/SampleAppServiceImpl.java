package com.samplecomponent.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.samplecomponent.dao.SampleAppDAO;
import com.samplecomponent.entity.SampleApp;
import com.samplecomponent.service.SampleAppService;

@Remote(SampleAppService.class)
@Stateless(mappedName = SampleAppService.JNDI_NAME, name = SampleAppService.JNDI_NAME)
public class SampleAppServiceImpl implements SampleAppService{
	
	@EJB
	private SampleAppDAO dao;

	@Override
	public long create(SampleApp vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SampleApp get(long id) {
		return dao.find(id);
	}

	@Override
	public void update(SampleApp vo) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub		
	}

	@Override
	public List<SampleApp> find(String pattern, int limit, int offset) {		
		return null;
	}
}
