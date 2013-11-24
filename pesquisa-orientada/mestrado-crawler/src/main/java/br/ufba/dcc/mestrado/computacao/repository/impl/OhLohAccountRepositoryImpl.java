package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.RoleEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAccountRepository;

@Repository(OhLohAccountRepositoryImpl.BEAN_NAME)
public class OhLohAccountRepositoryImpl extends BaseRepositoryImpl<Long, OhLohAccountEntity>
		implements OhLohAccountRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohAccountRepository";

	public OhLohAccountRepositoryImpl() {
		super(OhLohAccountEntity.class);
	}
	
	@Override
	public OhLohAccountEntity findByLogin(String login) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<OhLohAccountEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<OhLohAccountEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<OhLohAccountEntity> select = criteriaQuery.select(root);
		
		Fetch<OhLohAccountEntity, RoleEntity> roleListFetch = root.fetch("roleList", JoinType.INNER);

		Predicate namePredicate = criteriaBuilder.equal(root.get("login"), login);
		select.where(namePredicate);

		TypedQuery<OhLohAccountEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		OhLohAccountEntity result = null;

		try {
			result = query.getSingleResult();
			
			if (result.getRoleList() != null) {
				result.getRoleList().size();
			}
			
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

	
}
