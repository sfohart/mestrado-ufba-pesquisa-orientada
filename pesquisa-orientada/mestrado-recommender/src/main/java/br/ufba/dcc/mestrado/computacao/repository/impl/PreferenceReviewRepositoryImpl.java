package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.PreferenceReviewRepository;

@Repository(PreferenceReviewRepositoryImpl.BEAN_NAME)
public class PreferenceReviewRepositoryImpl 
		extends BaseRepositoryImpl<Long, PreferenceReviewEntity>
		implements PreferenceReviewRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6646271191081639098L;
	
	public static final String BEAN_NAME = "preferenceReviewRepository";
	
	public PreferenceReviewRepositoryImpl() {
		super(PreferenceReviewEntity.class);
	}
	
	@Override
	public Long countAllLastReviewsByProject(Long projectId) {
		Long result = 0L;
		
		if (projectId != null) {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<PreferenceReviewEntity> root = criteriaQuery.from(PreferenceReviewEntity.class);
			
			Join<PreferenceReviewEntity, PreferenceEntity> preferenceJoin = root.join("preference");
			
			criteriaQuery = criteriaQuery.select(criteriaBuilder.count(root));
			
			
			List<Predicate> predicateList = new ArrayList<>();
			
			Predicate projectPredicate = criteriaBuilder.equal(preferenceJoin.get("projectId"), projectId);
			predicateList.add(projectPredicate);
			
			/*
			 * Criando subquery para trazer os últimos registros de cada usuario/projeto
			 */
			Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
			
			Root<PreferenceEntity> preferenceRoot = subquery.from(PreferenceEntity.class);
			Expression<Timestamp> greatestRegisteredAt = preferenceRoot.get("registeredAt");
			
			subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
			subquery.where(
					criteriaBuilder.equal(preferenceJoin.get("projectId"), preferenceRoot.get("projectId")),
					criteriaBuilder.equal(preferenceJoin.get("userId"), preferenceRoot.get("userId"))
				);
			
			Predicate registeredAtPredicate = criteriaBuilder.equal(preferenceJoin.get("registeredAt"), subquery);
			predicateList.add(registeredAtPredicate);
			
			//aplicando os filtros
			criteriaQuery = criteriaQuery.where(predicateList.toArray(new Predicate[0]));
			
			TypedQuery<Long> typedQuery = getEntityManager().createQuery(criteriaQuery);
			
			result = typedQuery.getSingleResult();
		}
		
		return result;
	}
	
	
	@Override
	public Long countAllLastReviewsByUser(Long userId) {
		Long result = 0L;
		
		if (userId != null) {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<PreferenceReviewEntity> root = criteriaQuery.from(PreferenceReviewEntity.class);
			
			Join<PreferenceReviewEntity, PreferenceEntity> preferenceJoin = root.join("preference");
			
			criteriaQuery = criteriaQuery.select(criteriaBuilder.count(root));
			
			
			List<Predicate> predicateList = new ArrayList<>();
			
			Predicate projectPredicate = criteriaBuilder.equal(preferenceJoin.get("userId"), userId);
			predicateList.add(projectPredicate);
			
			/*
			 * Criando subquery para trazer os últimos registros de cada usuario/projeto
			 */
			Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
			
			Root<PreferenceEntity> preferenceRoot = subquery.from(PreferenceEntity.class);
			Expression<Timestamp> greatestRegisteredAt = preferenceRoot.get("registeredAt");
			
			subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
			subquery.where(
					criteriaBuilder.equal(preferenceJoin.get("projectId"), preferenceRoot.get("projectId")),
					criteriaBuilder.equal(preferenceJoin.get("userId"), preferenceRoot.get("userId"))
				);
			
			Predicate registeredAtPredicate = criteriaBuilder.equal(preferenceJoin.get("registeredAt"), subquery);
			predicateList.add(registeredAtPredicate);
			
			//aplicando os filtros
			criteriaQuery = criteriaQuery.where(predicateList.toArray(new Predicate[0]));
			
			
			TypedQuery<Long> typedQuery = getEntityManager().createQuery(criteriaQuery);
			
			result = typedQuery.getSingleResult();
		}
		
		return result;
	}
	
	@Override
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(Long projectId) {		
		return findAllLastReviewsByProject(projectId, null, null);
	}
	
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset) {
		return findAllLastReviewsByProject(projectId, null, null, true, true);
	}
	
	@Override
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		
		List<PreferenceReviewEntity> preferenceList = findAllLastReviews(null, projectId, startAt, offset, orderByRegisteredAt, orderByReviewRanking);
		
		return preferenceList;
	}
	
	
	public List<PreferenceReviewEntity> findAllLastReviewsByUser(
			Long userId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		
		List<PreferenceReviewEntity> preferenceList = findAllLastReviews(userId, null, startAt, offset, orderByRegisteredAt, orderByReviewRanking);
		
		return preferenceList;
	}
	
	private List<PreferenceReviewEntity> findAllLastReviews(
			Long userId, 
			Long projectId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) { 
		
		
		
		List<PreferenceReviewEntity> preferenceList = null;
		
		if (userId != null || projectId != null) {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			
			CriteriaQuery<PreferenceReviewEntity> reviewQuery = criteriaBuilder.createQuery(getEntityClass());
			
			Root<PreferenceReviewEntity> root = reviewQuery.from(getEntityClass());			
			Join<PreferenceReviewEntity, PreferenceEntity> preferenceJoin = root.join("preference", JoinType.INNER);
						
			reviewQuery = reviewQuery.select(root);
						
			List<Predicate> predicateList = new ArrayList<>();
			
			if (userId != null) {
				Predicate userPredicate = criteriaBuilder.equal(preferenceJoin.get("userId"), userId);
				predicateList.add(userPredicate);
			}
			
			if (projectId != null) {
				Predicate projectPredicate = criteriaBuilder.equal(preferenceJoin.get("projectId"), projectId);
				predicateList.add(projectPredicate);
			}
			
			/*
			 * Criando subquery para trazer os últimos registros de cada usuario/projeto
			 */
			Subquery<Timestamp> subquery = reviewQuery.subquery(Timestamp.class);
			
			Root<PreferenceEntity> p2 = subquery.from(PreferenceEntity.class);
			Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
			
			subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
			subquery.where(
					criteriaBuilder.equal(preferenceJoin.get("projectId"), p2.get("projectId")),
					criteriaBuilder.equal(preferenceJoin.get("userId"), p2.get("userId"))
				);
			
			Predicate registeredAtPredicate = criteriaBuilder.equal(preferenceJoin.get("registeredAt"), subquery);
			predicateList.add(registeredAtPredicate);
			
			//aplicando os filtros
			reviewQuery = reviewQuery.where(predicateList.toArray(new Predicate[0]));
			
			if (orderByRegisteredAt && orderByReviewRanking) {
				reviewQuery.orderBy(
						criteriaBuilder.desc(preferenceJoin.get("registeredAt")),
						criteriaBuilder.desc(root.get("reviewRanking"))
						);
			} else if (orderByRegisteredAt) {
				reviewQuery.orderBy(
						criteriaBuilder.desc(preferenceJoin.get("registeredAt"))
						);
			} else if (orderByReviewRanking) {
				reviewQuery.orderBy(
						criteriaBuilder.desc(root.get("reviewRanking"))
						);
			}
			
			TypedQuery<PreferenceReviewEntity> typedQuery = getEntityManager().createQuery(reviewQuery);
						
			if (startAt != null) {
				typedQuery.setFirstResult(startAt);
			}
			
			if (offset != null) {
				typedQuery.setMaxResults(offset);
			}
		
			preferenceList = typedQuery.getResultList();
		}
		
		return preferenceList;
		
		
	}

}
