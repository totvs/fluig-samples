package com.samplecomponent.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.fluig.sdk.api.common.SDKException;
import com.samplecomponent.entity.SampleCategory;
import com.totvs.technology.foundation.common.AbstractDAO;

/**
 * Aqui a sugestão é herdar a classe abstrata AbstractDAO e passar a entidade SampleCategory como sendo o objeto genérico desse DAO.
 * Além disso, alguns serviços de CRUD já são herdados.
 */
@Stateless(name = "dao/SampleCategory", mappedName = "dao/SampleCategory")
public class SampleCategoryDAO extends AbstractDAO<SampleCategory> {

	/**
	 * Construtor para gerencia a entidade {@link SampleCategory}
	 */
	public SampleCategoryDAO() {
		super(SampleCategory.class);
	}

	private EntityManager em;

	@Override
	public EntityManager getEntityManager() {
		return this.em;
	}

	@Override
	// Obrigatório utilizar o DT correto: AppDS
	@PersistenceContext(unitName = "AppDS")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}		

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SampleCategory> findCategories(Long tenantId, String text, int limit, int offset) throws SDKException {
        try {
            TypedQuery<SampleCategory> q = getEntityManager().createNamedQuery(SampleCategory.FIND_BY_NAME, SampleCategory.class);
            q.setParameter("tenantId", tenantId);
            q.setParameter("name", "%"+text.toLowerCase()+"%");            
            q.setFirstResult(offset);
            q.setMaxResults(limit);
            
            return q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SDKException(e);
        }
    }

}
