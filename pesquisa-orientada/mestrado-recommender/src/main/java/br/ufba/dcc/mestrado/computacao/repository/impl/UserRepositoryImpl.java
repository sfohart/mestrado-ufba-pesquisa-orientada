package br.ufba.dcc.mestrado.computacao.repository.impl;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.BaseRepositoryImpl;

@Repository(UserRepositoryImpl.BEAN_NAME)
public class UserRepositoryImpl extends BaseRepositoryImpl<Long, UserEntity>
		implements UserRepository {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8725171643807986718L;
	
	public static final String BEAN_NAME =  "userRepository";
	
	
	public UserRepositoryImpl() {
		super(UserEntity.class);
	}

	
	public UserEntity findBySocialLogin(String socialUsername) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());
		
		Root<UserEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<UserEntity> select = criteriaQuery.select(root);
		
		Predicate facebookPredicate = criteriaBuilder.equal(root.get("facebookAccount"), socialUsername);
		Predicate twitterPredicate = criteriaBuilder.equal(root.get("twitterAccount"), socialUsername);
		
		Predicate socialPredicate = criteriaBuilder.or(facebookPredicate, twitterPredicate);
		select.where(socialPredicate);
		
		TypedQuery<UserEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		UserEntity result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {
		} catch (NonUniqueResultException ex) {
		}

		return result;
	}
	
	@Override
	public UserEntity findByLogin(String login) {
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<UserEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<UserEntity> select = criteriaQuery.select(root);

		Predicate namePredicate = criteriaBuilder.equal(root.get("login"), login);
		select.where(namePredicate);

		TypedQuery<UserEntity> query = getEntityManager().createQuery(
				criteriaQuery);

		UserEntity result = null;

		try {
			result = query.getSingleResult();
		} catch (NoResultException ex) {

		} catch (NonUniqueResultException ex) {

		}

		return result;
	}

}
