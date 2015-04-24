package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.TagRepository;

@Repository(TagRepositoryImpl.BEAN_NAME)
public class TagRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubTagEntity>
		implements TagRepository {
	
	public static final String BEAN_NAME =  "tagRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public TagRepositoryImpl() {
		super(OpenHubTagEntity.class);
	}

	@Override
	public List<OpenHubTagEntity> findTagListByName(String name) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OpenHubTagEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OpenHubTagEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OpenHubTagEntity> select = criteriaQuery.select(root);
		
		Path<String> namePath = root.<String>get("name");
		
		Predicate namePredicate = criteriaBuilder.like(namePath, name + "%");
		select.where(namePredicate);
		
		TypedQuery<OpenHubTagEntity> query = getEntityManager().createQuery(
				criteriaQuery);
		
		return query.getResultList();
	}
	
	@Override
	public OpenHubTagEntity findByName(String name) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OpenHubTagEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OpenHubTagEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OpenHubTagEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("name"), name);
		select.where(namePredicate);

		TypedQuery<OpenHubTagEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		OpenHubTagEntity result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}
}
