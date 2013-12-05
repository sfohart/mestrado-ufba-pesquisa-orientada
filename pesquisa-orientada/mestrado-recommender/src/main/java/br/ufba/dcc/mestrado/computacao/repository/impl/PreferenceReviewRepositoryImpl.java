package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(
			OhLohProjectEntity project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PreferenceReviewEntity> findAllLastReviewsByProject(
			OhLohProjectEntity project, Integer startAt, Integer offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreferenceReviewEntity findMostHelpfulFavorableReview(OhLohProjectEntity project) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<PreferenceReviewEntity> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		
		Root<PreferenceReviewEntity> p1 = criteriaQuery.from(getEntityClass());
		

		Join<PreferenceReviewEntity, PreferenceEntity> j1 = p1.join("preference");
		Join<PreferenceReviewEntity, PreferenceEntity> j2 = p1.join("preference");
		
		
		TypedQuery<PreferenceReviewEntity> typedQuery = getEntityManager().createQuery(criteriaQuery);
		
		PreferenceReviewEntity result = null;
		
		try {
			result = typedQuery.getSingleResult();
		} catch (NoResultException ex) {
		} catch (NonUniqueResultException ex) {
		}
		
		return result;
	}

	@Override
	public PreferenceReviewEntity findMostHelpfulCriticalReview(OhLohProjectEntity project) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
