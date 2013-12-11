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

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.PreferenceReviewRepository;

@Repository(PreferenceReviewRepositoryImpl.BEAN_NAME)
public class PreferenceReviewRepositoryImpl extends BaseRepositoryImpl<Long, PreferenceReviewEntity> 
		implements PreferenceReviewRepository {
	
	
	public static final String BEAN_NAME =  "preferenceReviewRepository";

	/**
	 * 
	 */
	private static final long serialVersionUID = 11118358426212872L;
	
	public PreferenceReviewRepositoryImpl() {
		super(PreferenceReviewEntity.class);
	}

	@Override
	public Long countAllLastReviewsByProject(OhLohProjectEntity project) {
		Long result = 0L;
		
		if (project != null && project.getId() != null) {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<PreferenceReviewEntity> root = criteriaQuery.from(getEntityClass());
			
			Join<PreferenceReviewEntity, PreferenceEntity> preferenceJoin = root.join("preference");
			
			criteriaQuery = criteriaQuery.select(criteriaBuilder.count(root));
			
			preferenceJoin.fetch("preferenceEntryList", JoinType.LEFT);
			preferenceJoin.fetch("user", JoinType.LEFT);
			
			List<Predicate> predicateList = new ArrayList<>();
			
			Predicate projectPredicate = criteriaBuilder.equal(preferenceJoin.get("projectId"), project.getId());
			predicateList.add(projectPredicate);
			
			/*
			 * Criando subquery para trazer os últimos registros de cada usuario/projeto
			 */
			Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
			
			Root<PreferenceEntity> preferenceRoot = subquery.from(PreferenceEntity.class);
			Expression<Timestamp> greatestRegisteredAt = preferenceRoot.get("registeredAt");
			
			subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
			subquery.where(criteriaBuilder.equal(preferenceJoin.get("userId"), preferenceRoot.get("userId")));
			
			Predicate registeredAtPredicate = criteriaBuilder.equal(preferenceJoin.get("registeredAt"), subquery);
			predicateList.add(registeredAtPredicate);
			
			//aplicando os filtros
			criteriaQuery = criteriaQuery.where(predicateList.toArray(new Predicate[0]));
		}
		
		return result;
	}

	@Override
	public List<PreferenceEntity> findAllLastReviewsByProject(OhLohProjectEntity project) {
		return findAllLastReviewsByProject(project, null, null);
	}

	@Override
	public List<PreferenceEntity> findAllLastReviewsByProject(OhLohProjectEntity project, Integer startAt, Integer offset) {
		List<PreferenceEntity> preferenceList = null;
		
		if (project != null && project.getId() != null) {
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			
			CriteriaQuery<PreferenceEntity> criteriaQuery = criteriaBuilder.createQuery(PreferenceEntity.class);
			Root<PreferenceReviewEntity> root = criteriaQuery.from(getEntityClass());
			
			Join<PreferenceReviewEntity, PreferenceEntity> preferenceJoin = root.join("preference");
			
			criteriaQuery.select(preferenceJoin);
			
			preferenceJoin.fetch("preferenceEntryList", JoinType.LEFT);
			preferenceJoin.fetch("user", JoinType.LEFT);
			
			List<Predicate> predicateList = new ArrayList<>();
			
			Predicate projectPredicate = criteriaBuilder.equal(preferenceJoin.get("projectId"), project.getId());
			predicateList.add(projectPredicate);
			
			/*
			 * Criando subquery para trazer os últimos registros de cada usuario/projeto
			 */
			Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
			
			Root<PreferenceEntity> preferenceRoot = subquery.from(PreferenceEntity.class);
			Expression<Timestamp> greatestRegisteredAt = preferenceRoot.get("registeredAt");
			
			subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
			subquery.where(criteriaBuilder.equal(preferenceJoin.get("userId"), preferenceRoot.get("userId")));
			
			Predicate registeredAtPredicate = criteriaBuilder.equal(preferenceJoin.get("registeredAt"), subquery);
			predicateList.add(registeredAtPredicate);
			
			//aplicando os filtros
			criteriaQuery = criteriaQuery.where(predicateList.toArray(new Predicate[0]));
			
			TypedQuery<PreferenceEntity> typedQuery = getEntityManager().createQuery(criteriaQuery);
			
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

	@Override
	public PreferenceReviewEntity findMostHelpfulFavorableReview(OhLohProjectEntity project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreferenceReviewEntity findMostHelpfulCriticalReview(OhLohProjectEntity project) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
