package com.samplecomponent.service.impl;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.samplecomponent.service.SampleCategoryService;
import com.samplecomponent.vo.SampleCategoryVO;

@Remote
@Stateless(name = SampleCategoryService.JNDI_NAME, mappedName = SampleCategoryService.JNDI_NAME)
public class SampleCategoryServiceImpl implements SampleCategoryService{

	@Override
	public long create(SampleCategoryVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SampleCategoryVO get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(SampleCategoryVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SampleCategoryVO> find(String text, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

}
