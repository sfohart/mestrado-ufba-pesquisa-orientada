package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.account.OpenHubAccountEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.AccountRepository;

@Repository(AccountRepositoryImpl.BEAN_NAME)
public class AccountRepositoryImpl extends BaseRepositoryImpl<Long, OpenHubAccountEntity>
		implements AccountRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "accountRepository";

	public AccountRepositoryImpl() {
		super(OpenHubAccountEntity.class);
	}
	
	@Override
	public OpenHubAccountEntity findByLogin(String login) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OpenHubAccountEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OpenHubAccountEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OpenHubAccountEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("login"), login);
		select.where(namePredicate);

		TypedQuery<OpenHubAccountEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		OpenHubAccountEntity result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

	
}
