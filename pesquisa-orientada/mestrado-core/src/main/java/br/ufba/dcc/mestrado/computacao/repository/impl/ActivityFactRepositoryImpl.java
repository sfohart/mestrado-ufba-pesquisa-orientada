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

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.activityfact.OpenHubActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ActivityFactRepository;

@Repository(ActivityFactRepositoryImpl.BEAN_NAME)
public class ActivityFactRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubActivityFactEntity>
		implements ActivityFactRepository {
	
	public static final String BEAN_NAME =  "activityFactRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public ActivityFactRepositoryImpl() {
		super(OpenHubActivityFactEntity.class);
	}
	

	@Override
	public Long countAllByProject(OpenHubProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(getEntityClass())));
		
		Root<OpenHubActivityFactEntity> root = criteriaQuery.from(getEntityClass());
		
		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<OpenHubActivityFactEntity> findByProject(OpenHubProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OpenHubActivityFactEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OpenHubActivityFactEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OpenHubActivityFactEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		select.where(namePredicate);

		TypedQuery<OpenHubActivityFactEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		List<OpenHubActivityFactEntity> result = null;

		try {
			result = query.getResultList();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

}
