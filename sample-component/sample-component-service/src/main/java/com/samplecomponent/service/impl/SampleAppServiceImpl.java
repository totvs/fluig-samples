package com.samplecomponent.service.impl;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.SecurityService;
import com.fluig.sdk.service.UserService;
import com.fluig.sdk.tenant.AdminUserVO;
import com.samplecomponent.dao.SampleAppDAO;
import com.samplecomponent.entity.SampleApp;
import com.samplecomponent.entity.SampleCategory;
import com.samplecomponent.service.SampleAppService;
import com.samplecomponent.service.SampleCategoryService;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNRuntimeException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;

@Remote(SampleAppService.class)
@Stateless(mappedName = SampleAppService.JNDI_NAME, name = SampleAppService.JNDI_NAME)
public class SampleAppServiceImpl implements SampleAppService {

	@EJB
	private SampleAppDAO dao;

	@EJB
	private SampleCategoryService categoryService;

	@EJB(lookup = SecurityService.JNDI_REMOTE_NAME)
	private SecurityService securityService;

	@EJB(lookup = UserService.JNDI_REMOTE_NAME)
	private UserService userService;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(SampleApp app) throws FDNCreateException {
		try {
			/**
			 * Check permission if needed
			 * DO SOMETHING LIKE THIS
			 */
			if (!isUserLoggedAdmin())
				throw new FDNCreateException("Only admin can create this resource");
			
			SampleCategory category = categoryService.get(app.getCategoryId());
			if(category == null)
				throw new FDNCreateException("No Category found for id: " + app.getCategoryId());
			
			app.setCategory(category);
			app.setTenantId(category.getTenantId());
			Optional<SampleApp> optional = null;
			optional = Optional.ofNullable(dao.create(app));
			return optional.isPresent() ? optional.get().getId() : null;
		} catch (FDNCreateException e) {
            throw new FDNCreateException(e.getMessage(), e);
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SampleApp get(long id) {
		Optional<SampleApp> s = Optional.ofNullable(dao.find(id));
		return s.isPresent() ? s.get() : null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void update(SampleApp app) throws FDNUpdateException {
		/**
		 * Check permission if needed
		 */
		Optional<SampleApp> s = Optional.ofNullable(dao.find(app.getId()));
		if (!s.isPresent())
			throw new FDNUpdateException("No App found for ID: " + app.getId());

		if (s.get().getCategory().getId().equals(app.getCategoryId())) {
			app.setCategory(s.get().getCategory());
		} else {
			SampleCategory category = categoryService.get(app.getCategoryId());
			if (category == null)
				throw new FDNUpdateException("No Category found for id: " + app.getCategoryId());
			app.setCategory(category);
		}
		app.setTenantId(s.get().getTenantId());
		dao.edit(app);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delete(long id) throws FDNRemoveException {
		/**
		 * Check permission if needed
		 */
		Optional<SampleApp> s = Optional.ofNullable(dao.find(id));
		if (!s.isPresent())
			throw new FDNRemoveException("No App found for ID: " + id);
		dao.remove(s.get());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SampleApp> find(String text, int limit, int offset) throws SDKException{
		try {
			return dao.findApps(securityService.getCurrentTenantId(), text, limit, offset);
		} catch (FDNRuntimeException | SDKException e) {
			throw new FDNRuntimeException(e.getMessage(), e.getCause());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SampleApp> findByCategory(Long categoryId, int limit, int offset) throws SDKException {
		try {
			return dao.findAppsByCategoryId(securityService.getCurrentTenantId(), categoryId, limit, offset);
		} catch (FDNRuntimeException | SDKException e) {
			throw new FDNRuntimeException(e.getMessage(), e.getCause());
		}
	}

	private boolean isUserLoggedAdmin() {
		try {
			String login = userService.getCurrent().getLogin();
			List<AdminUserVO> list = securityService.listTenantAdmins(securityService.getCurrentTenantId());
			for (AdminUserVO admin : list) {
				if (admin.getLogin().equals(login))
					return true;
			}
			return false;
		} catch (SDKException e) {
			throw new RuntimeException("Can't request tenant admin list");
		}
	}
}
