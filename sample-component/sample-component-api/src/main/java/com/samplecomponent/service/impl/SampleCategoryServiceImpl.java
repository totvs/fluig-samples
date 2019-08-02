package com.samplecomponent.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.SecurityService;
import com.samplecomponent.dao.SampleCategoryDAO;
import com.samplecomponent.entity.SampleCategory;
import com.samplecomponent.service.SampleCategoryService;
import com.samplecomponent.vo.SampleCategoryVO;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

@Remote
@Stateless(name = SampleCategoryService.JNDI_NAME, mappedName = SampleCategoryService.JNDI_NAME)
public class SampleCategoryServiceImpl implements SampleCategoryService {

	@EJB
	SampleCategoryDAO dao;

	@EJB(lookup = SecurityService.JNDI_REMOTE_NAME)
	private SecurityService svcSecurity;

	@Override
	public long create(SampleCategoryVO vo) throws FDNCreateException{
		Optional<SampleCategory> optional = Optional.ofNullable(dao.create(SampleCategory.convert(vo)));
		return (optional.isPresent() ? optional.get().getId() : null);
	}

	@Override
	public SampleCategoryVO get(long id) {
		Optional<SampleCategory> s = Optional.ofNullable(dao.find(id));
		return (s.isPresent() ? convert(s.get()) : null);
	}

	@Override
	public void update(SampleCategoryVO vo) throws FDNUpdateException {
		dao.edit(SampleCategory.convert(vo));
	}

	@Override
	public void delete(long id) throws FDNRemoveException {
		Optional<SampleCategory> s = Optional.ofNullable(dao.find(id));
		if (!s.isPresent())
			throw new IllegalArgumentException("No Category found for ID: " + id);
		dao.remove(s.get());
	}

	@Override
	public List<SampleCategoryVO> find(String text, int limit, int offset) throws SDKException {
		Collection<SampleCategory> result = dao.findAll(svcSecurity.getCurrentTenantId(),
				((text == null) ? null : new HashMap<String, Object>() {
					{
						put("name", text);
					}
				}), limit, offset);
		if (result.isEmpty())
			return new ArrayList<SampleCategoryVO>();
		ArrayList<SampleCategoryVO> list = new ArrayList<SampleCategoryVO>();
		result.forEach(e -> list.add(convert(e)));
		return list;
	}

	@Override
	public SampleCategoryVO convert(SampleCategory entity) {
		return SampleCategoryVO.convert(entity);
	}

}
