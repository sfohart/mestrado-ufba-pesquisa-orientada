package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohTagRepository;

@Repository(OhLohTagRepositoryImpl.BEAN_NAME)
public class OhLohTagRepositoryImpl extends BaseRepositoryImpl<Long, OhLohTagEntity>
		implements OhLohTagRepository {
	
	public static final String BEAN_NAME =  "ohLohTagRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohTagRepositoryImpl() {
		super(OhLohTagEntity.class);
	}

	@Override
	public OhLohTagEntity findByName(String name) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OhLohTagEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OhLohTagEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OhLohTagEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("name"), name);
		select.where(namePredicate);

		TypedQuery<OhLohTagEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		OhLohTagEntity result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}
}
