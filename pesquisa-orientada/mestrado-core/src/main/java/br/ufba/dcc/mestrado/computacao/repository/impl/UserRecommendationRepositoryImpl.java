package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.RecommendationTypeEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.recommendation.UserRecommendationEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRecommendationRepository;

@Repository(UserRecommendationRepositoryImpl.BEAN_NAME)
public class UserRecommendationRepositoryImpl 
		extends BaseRepositoryImpl<Long, UserRecommendationEntity>
		implements UserRecommendationRepository {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5717605329155211879L;
	
	public static final String BEAN_NAME =  "userRecommendationRepository";
	
	public UserRecommendationRepositoryImpl() {
		super(UserRecommendationEntity.class);
	}
	
	
	public UserRecommendationEntity findLastUserRecommendation(UserEntity user, RecommendationTypeEnum recommendationType) {
		UserRecommendationEntity userRecommendation = null;
		
		if (user != null) {
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			
			CriteriaQuery<UserRecommendationEntity> recommendationQuery = criteriaBuilder.createQuery(getEntityClass());
			
			Root<UserRecommendationEntity> root = recommendationQuery.from(getEntityClass());
			root.fetch("recommendedProjects", JoinType.INNER);
			root.fetch("user", JoinType.INNER);
			
			CriteriaQuery<UserRecommendationEntity> recommendationSelect = recommendationQuery.select(root);
			
			List<Predicate> predicateList = new ArrayList<Predicate>();
			
			if (user.getId() != null) {
				Predicate userPredicate = criteriaBuilder.equal(root.get("userId"), user.getId());
				predicateList.add(userPredicate);
			}
			
			if (recommendationType != null) {
				Predicate recommendationTypePredicate = criteriaBuilder.equal(root.get("recommendationType"), recommendationType);
				predicateList.add(recommendationTypePredicate);
			}
			
			/*
			 * Criando subquery para trazer os �ltimos registros de cada usuario/projeto
			 */
			Subquery<Timestamp> subquery = recommendationSelect.subquery(Timestamp.class);
			
			Root<UserRecommendationEntity> p2 = subquery.from(getEntityClass());
			Expression<Timestamp> greatestRegisteredAt = p2.get("recommendationDate");
			
			subquery = subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
			
			List<Predicate> subqueryPredicateList = new ArrayList<>();
			subqueryPredicateList.add(criteriaBuilder.equal(root.get("userId"), p2.get("userId")));
			subqueryPredicateList.add(criteriaBuilder.equal(root.get("recommendationType"), p2.get("recommendationType")));
			
			subquery = subquery.where(subqueryPredicateList.toArray(new Predicate[0]));
			
			Predicate registeredAtPredicate = criteriaBuilder.equal(root.get("recommendationDate"), subquery);
			predicateList.add(registeredAtPredicate);
			
			recommendationSelect = recommendationSelect.where(predicateList.toArray(new Predicate[0]));
			
			TypedQuery<UserRecommendationEntity> typedQuery = getEntityManager().createQuery(recommendationSelect);
			
			try {
				userRecommendation = typedQuery.getSingleResult();
			} catch (NoResultException | NonUniqueResultException ex) {
			}
			
		}
		
		return userRecommendation;
	}

}
