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

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.activityfact.OhLohActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ActivityFactRepository;

@Repository(ActivityFactRepositoryImpl.BEAN_NAME)
public class ActivityFactRepositoryImpl extends BaseRepositoryImpl<Long, OhLohActivityFactEntity>
		implements ActivityFactRepository {
	
	public static final String BEAN_NAME =  "activityFactRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public ActivityFactRepositoryImpl() {
		super(OhLohActivityFactEntity.class);
	}
	

	@Override
	public Long countAllByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(getEntityClass())));
		
		Root<OhLohActivityFactEntity> root = criteriaQuery.from(getEntityClass());
		
		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<OhLohActivityFactEntity> findByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OhLohActivityFactEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OhLohActivityFactEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OhLohActivityFactEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);

		TypedQuery<OhLohActivityFactEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		List<OhLohActivityFactEntity> result = null;

		try {
			result = query.getResultList();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

}
