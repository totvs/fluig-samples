package com.samplecomponent.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.samplecomponent.entity.SampleApp;
import com.totvs.technology.foundation.common.AbstractDAO;

/**
 * Aqui é necessário herdar a classe abstrata AbstractDAO
 * e passar a entidade SampleApp como sendo o objeto genérico desse DAO.
 * Além disso, alguns serviços de CRUD já são herdados.
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

}
