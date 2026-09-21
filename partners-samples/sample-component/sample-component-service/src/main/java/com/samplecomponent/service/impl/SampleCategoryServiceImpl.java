package com.samplecomponent.service.impl;

import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.SecurityService;
import com.fluig.sdk.service.UserService;
import com.samplecomponent.dao.SampleCategoryDAO;
import com.samplecomponent.entity.SampleCategory;
import com.samplecomponent.service.SampleCategoryService;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNRuntimeException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

import javax.ejb.*;
import java.util.List;
import java.util.Optional;

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
	public long create(SampleCategory category) throws FDNCreateException {
		try {
			/**
			 * check permission if needed 
			 */
			normalizeAndValidateCategory(category);
			category.setTenantId(securityService.getCurrentTenantId());
			Optional<SampleCategory> categoryOptional = Optional.ofNullable(dao.create(category));
			if (!categoryOptional.isPresent() || categoryOptional.get().getId() == null) {
				throw new FDNCreateException("Failed to create category.");
			}
			return categoryOptional.get().getId();
		} catch (FDNCreateException | SDKException e) {
            throw new FDNCreateException(e.getMessage(), e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SampleCategory get(long id) {
		Optional<SampleCategory> sampleCategory = Optional.ofNullable(dao.find(id));
		return (sampleCategory.isPresent() ? sampleCategory.get() : null);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void update(SampleCategory sampleCategory) throws FDNUpdateException {
		normalizeAndValidateCategory(sampleCategory);
		SampleCategory existing = findCategoryOrThrow(sampleCategory.getId());
		sampleCategory.setTenantId(existing.getTenantId());
		dao.edit(sampleCategory);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void delete(long id) throws FDNRemoveException {
		SampleCategory existing = findCategoryOrThrow(id);
		dao.remove(existing);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SampleCategory> find(String text, int limit, int offset) throws SDKException {
		return dao.findCategories(securityService.getCurrentTenantId(), text, limit, offset);
	}

	/**
	 * Finds category by id or throws runtime exception if not found.
	 */
	private SampleCategory findCategoryOrThrow(long id) {
		Optional<SampleCategory> category = Optional.ofNullable(dao.find(id));
		if (!category.isPresent())
			throw new FDNRuntimeException("No Category found for ID: " + id);
		return category.get();
	}

	private void normalizeAndValidateCategory(SampleCategory category) {
		if (category == null) {
			throw new IllegalArgumentException("error.category.required");
		}

		String normalizedName = normalizeName(category.getName());
		category.setName(normalizedName);
		
		if (normalizedName.isEmpty()) {
			throw new IllegalArgumentException("error.category.name.required");
		}
		if (normalizedName.length() > 50) {
			throw new IllegalArgumentException("error.category.name.max_length");
		}
		if (containsControlCharacters(normalizedName)) {
			throw new IllegalArgumentException("error.category.name.invalid_chars");
		}
	}

	private String normalizeName(String name) {
		if (name == null) {
			return "";
		}
		return name.trim().replaceAll("\\s+", " ");
	}

	private boolean containsControlCharacters(String value) {
		for (int i = 0; i < value.length(); i++) {
			if (Character.isISOControl(value.charAt(i))) {
				return true;
			}
		}
		return false;
	}

}
