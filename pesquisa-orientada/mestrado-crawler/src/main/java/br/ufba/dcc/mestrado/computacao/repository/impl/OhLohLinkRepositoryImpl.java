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

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLinkRepository;

@Repository(OhLohLinkRepositoryImpl.BEAN_NAME)
public class OhLohLinkRepositoryImpl extends BaseRepositoryImpl<Long, OhLohLinkEntity>
		implements OhLohLinkRepository {
	
	public static final String BEAN_NAME =  "ohLohLinkRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohLinkRepositoryImpl() {
		super(OhLohLinkEntity.class);
	}
	
	@Override
	public Long countAllByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<OhLohLinkEntity> root = criteriaQuery.from(getEntityClass());
		criteriaQuery = criteriaQuery.select(criteriaBuilder.count(root));
		
		
		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		criteriaQuery = criteriaQuery.where(namePredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<OhLohLinkEntity> findByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OhLohLinkEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OhLohLinkEntity> root = criteriaQuery.from(getEntityClass());
		criteriaQuery = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		criteriaQuery = criteriaQuery.where(namePredicate);

		TypedQuery<OhLohLinkEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		List<OhLohLinkEntity> result = null;

		try {
			result = query.getResultList();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

}
