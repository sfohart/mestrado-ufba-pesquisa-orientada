package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.PasswordChangeRequestEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.PasswordChangeRequestRepository;


@Repository(PasswordChangeRequestRepositoryImpl.BEAN_NAME)
public class PasswordChangeRequestRepositoryImpl 
		extends BaseRepositoryImpl<Long, PasswordChangeRequestEntity>
		implements PasswordChangeRequestRepository{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6618869198656250030L;
	
	public static final String BEAN_NAME =  "passwordChangeRequestRepositoryImpl";
		
	public PasswordChangeRequestRepositoryImpl(Class<PasswordChangeRequestEntity> entityClass) {
		super(entityClass);
	}

	@Override
	public PasswordChangeRequestEntity findByToken(String token) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<PasswordChangeRequestEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());
		
		Root<PasswordChangeRequestEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<PasswordChangeRequestEntity> select = criteriaQuery.select(root);
		
		Predicate tokenPredicate = criteriaBuilder.equal(root.get("token"), token);
								
		select.where(tokenPredicate);
		
		TypedQuery<PasswordChangeRequestEntity> query = getEntityManager().createQuery(criteriaQuery);

		PasswordChangeRequestEntity result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {
		} catch (NonUniqueResultException ex) {
		}

		return result;
	}
	
}
