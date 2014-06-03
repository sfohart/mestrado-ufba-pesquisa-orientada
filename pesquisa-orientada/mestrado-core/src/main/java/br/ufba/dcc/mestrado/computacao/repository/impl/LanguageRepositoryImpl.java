package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.language.OhLohLanguageEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.LanguageRepository;

@Repository(LanguageRepositoryImpl.BEAN_NAME)
public class LanguageRepositoryImpl extends BaseRepositoryImpl<Long, OhLohLanguageEntity>
		implements LanguageRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "languageRepository";

	public LanguageRepositoryImpl() {
		super(OhLohLanguageEntity.class);
	}

	@Override
	public OhLohLanguageEntity findByName(String name) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OhLohLanguageEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OhLohLanguageEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OhLohLanguageEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("name"), name);
		select.where(namePredicate);

		TypedQuery<OhLohLanguageEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		OhLohLanguageEntity result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}
}
