package com.samplecomponent.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.fluig.sdk.api.common.SDKException;
import com.samplecomponent.entity.SampleApp;
import com.totvs.technology.foundation.common.AbstractDAO;

/**
 * Aqui a sugestão é herdar a classe abstrata AbstractDAO e passar a entidade
 * SampleApp como sendo o objeto genérico desse DAO. Além disso, alguns serviços
 * de CRUD já são herdados.
 */
@Stateless(name = "dao/SampleApp", mappedName = "dao/SampleApp")
public class SampleAppDAO extends AbstractDAO<SampleApp> {

	/**
	 * Construtor para gerencia a entidade {@link SampleApp}
	 */
	public SampleAppDAO() {
		super(SampleApp.class);
	}

	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return this.em;
	}

	@Override
	@PersistenceContext(unitName = "AppDS")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SampleApp> findApps(Long tenantId, String text, int limit, int offset) throws SDKException {
		try {
			TypedQuery<SampleApp> q = getEntityManager().createNamedQuery(SampleApp.FIND_BY_NAME_DEV,
					SampleApp.class);
			q.setParameter("tenantId", tenantId);
			q.setParameter("text", "%" + text.toLowerCase() + "%");
			q.setFirstResult(offset);
			q.setMaxResults(limit);

			return q.getResultList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new SDKException(e);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<SampleApp> findAppsByCategoryId(Long tenantId, Long categoryId, int limit, int offset) throws SDKException {
		try {
			TypedQuery<SampleApp> q = getEntityManager().createNamedQuery(SampleApp.FIND_BY_CATEGORY,
					SampleApp.class);
			q.setParameter("tenantId", tenantId);
			q.setParameter("categoryId", categoryId);
			q.setFirstResult(offset);
			q.setMaxResults(limit);

			return q.getResultList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new SDKException(e);
		}
	}

}
