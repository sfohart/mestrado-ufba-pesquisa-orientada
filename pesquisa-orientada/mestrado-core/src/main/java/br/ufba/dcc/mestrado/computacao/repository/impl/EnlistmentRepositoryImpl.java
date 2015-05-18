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

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.enlistment.OpenHubEnlistmentEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.EnlistmentRepository;

@Repository(EnlistmentRepositoryImpl.BEAN_NAME)
public class EnlistmentRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubEnlistmentEntity>
		implements EnlistmentRepository {
	
	public static final String BEAN_NAME =  "enlistmentRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public EnlistmentRepositoryImpl() {
		super(OpenHubEnlistmentEntity.class);
	}
	
	@Override
	public Long countAllByProject(OpenHubProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<OpenHubEnlistmentEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(root));
		
		
		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<OpenHubEnlistmentEntity> findByProject(OpenHubProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OpenHubEnlistmentEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OpenHubEnlistmentEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OpenHubEnlistmentEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);

		TypedQuery<OpenHubEnlistmentEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		List<OpenHubEnlistmentEntity> result = null;

		try {
			result = query.getResultList();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

}
