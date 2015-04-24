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

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.sizefact.OpenHubSizeFactEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.SizeFactRepository;

@Repository(SizeFactRepositoryImpl.BEAN_NAME)
public class SizeFactRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubSizeFactEntity>
		implements SizeFactRepository {
	
	public static final String BEAN_NAME =  "sizeFactRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public SizeFactRepositoryImpl() {
		super(OpenHubSizeFactEntity.class);
	}
	

	@Override
	public Long countAllByProject(OpenHubProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(getEntityClass())));
		
		Root<OpenHubSizeFactEntity> root = criteriaQuery.from(getEntityClass());
		
		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<OpenHubSizeFactEntity> findByProject(OpenHubProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OpenHubSizeFactEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OpenHubSizeFactEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OpenHubSizeFactEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);

		TypedQuery<OpenHubSizeFactEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		List<OpenHubSizeFactEntity> result = null;

		try {
			result = query.getResultList();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

}
