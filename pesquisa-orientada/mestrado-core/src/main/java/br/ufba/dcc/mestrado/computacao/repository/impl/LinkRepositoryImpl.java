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

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.LinkRepository;

@Repository(LinkRepositoryImpl.BEAN_NAME)
public class LinkRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubLinkEntity>
		implements LinkRepository {
	
	public static final String BEAN_NAME =  "linkRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public LinkRepositoryImpl() {
		super(OpenHubLinkEntity.class);
	}
	
	@Override
	public Long countAllByProject(OpenHubProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<OpenHubLinkEntity> root = criteriaQuery.from(getEntityClass());
		criteriaQuery = criteriaQuery.select(criteriaBuilder.count(root));
		
		
		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		criteriaQuery = criteriaQuery.where(namePredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public List<OpenHubLinkEntity> findByProject(OpenHubProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OpenHubLinkEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OpenHubLinkEntity> root = criteriaQuery.from(getEntityClass());
		criteriaQuery = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
		criteriaQuery = criteriaQuery.where(namePredicate);

		TypedQuery<OpenHubLinkEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		List<OpenHubLinkEntity> result = null;

		try {
			result = query.getResultList();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

}
