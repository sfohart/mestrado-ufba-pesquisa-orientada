package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.UserRecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRecommenderCriteriumRepository;

@Repository(UserRecommenderCriteriumRepositoryImpl.BEAN_NAME)
public class UserRecommenderCriteriumRepositoryImpl extends BaseRepositoryImpl<Long, UserRecommenderCriteriumEntity>
	implements UserRecommenderCriteriumRepository {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7582764504151627470L;
	
	public static final String BEAN_NAME =  "userRecommenderCriteriumRepository";

	public UserRecommenderCriteriumRepositoryImpl() {
		super(UserRecommenderCriteriumEntity.class);
	}

	@Override
	public List<UserRecommenderCriteriumEntity> findAllByUser(Long userId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<UserRecommenderCriteriumEntity> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		
		Root<UserRecommenderCriteriumEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<UserRecommenderCriteriumEntity> select = criteriaQuery.select(root);
		
		Predicate predicate = criteriaBuilder.equal(root.get("userId"), userId);
		select.where(predicate);
		
		TypedQuery<UserRecommenderCriteriumEntity> query = getEntityManager().createQuery(criteriaQuery);
				
		return query.getResultList();
	}
	
	
}
