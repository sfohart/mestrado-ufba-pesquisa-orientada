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

import br.ufba.dcc.mestrado.computacao.entities.ohloh.activityfact.OhLohActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohActivityFactRepository;

@Repository(OhLohActivityFactRepositoryImpl.BEAN_NAME)
public class OhLohActivityFactRepositoryImpl extends BaseRepositoryImpl<Long, OhLohActivityFactEntity>
		implements OhLohActivityFactRepository {
	
	public static final String BEAN_NAME =  "ohLohActivityFactRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohActivityFactRepositoryImpl() {
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
