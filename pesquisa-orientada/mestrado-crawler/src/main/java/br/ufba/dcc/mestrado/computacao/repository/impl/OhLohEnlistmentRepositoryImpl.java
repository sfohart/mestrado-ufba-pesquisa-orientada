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

import br.ufba.dcc.mestrado.computacao.entities.ohloh.enlistment.OhLohEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohEnlistmentRepository;

@Repository(OhLohEnlistmentRepositoryImpl.BEAN_NAME)
public class OhLohEnlistmentRepositoryImpl extends BaseRepositoryImpl<Long, OhLohEnlistmentEntity>
		implements OhLohEnlistmentRepository {
	
	public static final String BEAN_NAME =  "ohLohEnlistmentRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohEnlistmentRepositoryImpl() {
		super(OhLohEnlistmentEntity.class);
	}
	
	@Override
	public Long countAllByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(getEntityClass())));
		
		Root<OhLohEnlistmentEntity> root = criteriaQuery.from(getEntityClass());
		
		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<OhLohEnlistmentEntity> findByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OhLohEnlistmentEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OhLohEnlistmentEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OhLohEnlistmentEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);

		TypedQuery<OhLohEnlistmentEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		List<OhLohEnlistmentEntity> result = null;

		try {
			result = query.getResultList();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

}
