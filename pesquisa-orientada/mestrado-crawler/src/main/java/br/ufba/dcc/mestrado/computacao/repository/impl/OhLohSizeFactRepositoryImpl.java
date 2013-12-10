package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.sizefact.OhLohSizeFactEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohSizeFactRepository;

@Repository(OhLohSizeFactRepositoryImpl.BEAN_NAME)
public class OhLohSizeFactRepositoryImpl extends BaseRepositoryImpl<Long, OhLohSizeFactEntity>
		implements OhLohSizeFactRepository {
	
	public static final String BEAN_NAME =  "ohLohSizeFactRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohSizeFactRepositoryImpl() {
		super(OhLohSizeFactEntity.class);
	}
	

	@Override
	public Long countAllByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(getEntityClass())));
		
		Root<OhLohSizeFactEntity> root = criteriaQuery.from(getEntityClass());
		
		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<OhLohSizeFactEntity> findByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OhLohSizeFactEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OhLohSizeFactEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OhLohSizeFactEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);

		TypedQuery<OhLohSizeFactEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		List<OhLohSizeFactEntity> result = null;

		try {
			result = query.getResultList();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

}
