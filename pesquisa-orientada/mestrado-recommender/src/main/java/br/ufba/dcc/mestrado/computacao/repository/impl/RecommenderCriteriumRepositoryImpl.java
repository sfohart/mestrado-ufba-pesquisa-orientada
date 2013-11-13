package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.recommender.criterium.RecommenderCriteriumEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RecommenderCriteriumRepository;

@Repository(RecommenderCriteriumRepositoryImpl.BEAN_NAME)
public class RecommenderCriteriumRepositoryImpl extends BaseRepositoryImpl<Long, RecommenderCriteriumEntity>
	implements RecommenderCriteriumRepository {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7582764504151627470L;
	
	public static final String BEAN_NAME =  "recommenderCriteriumRepository";

	public RecommenderCriteriumRepositoryImpl() {
		super(RecommenderCriteriumEntity.class);
	}

	@Override
	public List<RecommenderCriteriumEntity> findAllByAccount(Long accountId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<RecommenderCriteriumEntity> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		
		Root<RecommenderCriteriumEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<RecommenderCriteriumEntity> select = criteriaQuery.select(root);
		
		Predicate predicate = criteriaBuilder.equal(root.get("accountId"), accountId);
		select.where(predicate);
		
		TypedQuery<RecommenderCriteriumEntity> query = getEntityManager().createQuery(criteriaQuery);
				
		return query.getResultList();
	}
	
	
}
