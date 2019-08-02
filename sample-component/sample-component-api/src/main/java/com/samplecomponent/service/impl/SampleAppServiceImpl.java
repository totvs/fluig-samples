package com.samplecomponent.service.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.samplecomponent.service.SampleAppService;
import com.samplecomponent.vo.SampleAppVO;

@Remote(SampleAppService.class)
@Stateless(mappedName = SampleAppService.JNDI_NAME, name = SampleAppService.JNDI_NAME)
public class SampleAppServiceImpl implements SampleAppService{

	@Override
	public long create(SampleAppVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SampleAppVO get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(SampleAppVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SampleAppVO> find(String pattern, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}
}
