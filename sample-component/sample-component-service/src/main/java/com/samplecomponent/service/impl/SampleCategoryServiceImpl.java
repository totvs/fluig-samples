package com.samplecomponent.service.impl;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.NotFoundException;

import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.SecurityService;
import com.fluig.sdk.service.UserService;
import com.samplecomponent.dao.SampleCategoryDAO;
import com.samplecomponent.entity.SampleCategory;
import com.samplecomponent.service.SampleCategoryService;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

@Remote
@Stateless(name = SampleCategoryService.JNDI_NAME, mappedName = SampleCategoryService.JNDI_NAME)
public class SampleCategoryServiceImpl implements SampleCategoryService {

	@EJB
	private SampleCategoryDAO dao;

	@EJB(lookup = SecurityService.JNDI_REMOTE_NAME)
	private SecurityService securityService;
	
	@EJB(lookup = UserService.JNDI_REMOTE_NAME)
	private UserService userService;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(SampleCategory cat) throws FDNCreateException, SDKException {
		/**
		 * check permission if needed
		 *
		 * DO SOMETHING
		 */
		cat.setTenantId(securityService.getCurrentTenantId());
		Optional<SampleCategory> optional = Optional.ofNullable(dao.create(cat));
		return (optional.isPresent() ? optional.get().getId() : null);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SampleCategory get(long id) {
		Optional<SampleCategory> s = Optional.ofNullable(dao.find(id));
		return (s.isPresent() ? s.get() : null);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void update(SampleCategory cat) throws FDNUpdateException, SDKException {
		/**
		 * check permission if needed
		 *
		 * DO SOMETHING
		 */
		Optional<SampleCategory> s = Optional.ofNullable(dao.find(cat.getId()));
		if (!s.isPresent())
			throw new NotFoundException("No Category found for ID: " + cat.getId());
		cat.setTenantId(s.get().getTenantId());
		dao.edit(cat);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void delete(long id) throws FDNRemoveException {
		/**
		 * check permission if needed
		 *
		 * DO SOMETHING
		 */		
		Optional<SampleCategory> s = Optional.ofNullable(dao.find(id));
		if (!s.isPresent())
			throw new NotFoundException("No Category found for ID: " + id);
		dao.remove(s.get());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SampleCategory> find(String text, int limit, int offset) throws SDKException {
		return dao.findCategories(securityService.getCurrentTenantId(), text, limit, offset);
	}

}
