package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OverallRatingRepository;

@Repository(OverallRatingRepositoryImpl.BEAN_NAME)
public class OverallRatingRepositoryImpl
		extends AbstractRatingRepositoryImpl
		implements OverallRatingRepository {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8847818433424402589L;
	
	public static final String BEAN_NAME = "overallPreferenceRepository";

	@Override
	public Map<ImmutablePair<Long, Long>, Double> findAllLastOverallPreferenceValue() {		
		return findAllLastOverallPreferenceValueByItem(null);
	}

	@Override
	public Map<ImmutablePair<Long, Long>, Double> findAllLastOverallPreferenceValueByItem(
			Long itemId) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
		
		Root<PreferenceEntity> root = tupleQuery.from(getEntityClass());
		
		//campos da preferência geral
		Path<Long> accountIdPath = root.get("userId");		
		Path<Long> projectIdPath = root.get("projectId");
		Path<Double> valuePath = root.get("value");
		
		tupleQuery = tupleQuery.multiselect(
				accountIdPath,
				projectIdPath,
				valuePath);
		
		List<Predicate> predicateList = new ArrayList<>();
		
		//filtrando por item
		if (itemId != null) {
			Predicate itemPredicate = criteriaBuilder.equal(projectIdPath, itemId);
			predicateList.add(itemPredicate);
		}
		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = tupleQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> preferenceRoot = subquery.from(PreferenceEntity.class);
		Expression<Timestamp> greatestRegisteredAt = preferenceRoot.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		subquery.where(
				criteriaBuilder.equal(root.get("projectId"), preferenceRoot.get("projectId")),
				criteriaBuilder.equal(root.get("userId"), preferenceRoot.get("userId"))
			);
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(root.get("registeredAt"), subquery);
		predicateList.add(registeredAtPredicate);
		
		//aplicando os filtros
		tupleQuery = tupleQuery.where(predicateList.toArray(new Predicate[0]));
		
		TypedQuery<Tuple> tupleTypedQuery = getEntityManager().createQuery(tupleQuery);
		List<Tuple> tupleList = tupleTypedQuery.getResultList();
		
		Map<ImmutablePair<Long, Long>, Double> result = createImmutablePairMap(tupleList);
		
		return result;
		
	}

	
	@Override
	public List<PreferenceEntity> findAllLastPreferenceByProject(
			Long projectId,
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		return findAllLastPreference(null, projectId, startAt, offset, orderByRegisteredAt, orderByReviewRanking);
	}
	
	@Override
	public List<PreferenceEntity> findAllLastPreferenceByUser(
			Long userId,
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		return findAllLastPreference(userId, null, startAt, offset, orderByRegisteredAt, orderByReviewRanking);
	}
	
	private List<PreferenceEntity> findAllLastPreference(
			Long userId,
			Long projectId,
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		
		List<PreferenceEntity> preferenceList = null;
		
		if (userId != null || projectId != null) {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<PreferenceEntity> preferenceQuery = criteriaBuilder.createQuery(getEntityClass());
			
			Root<PreferenceEntity> root = preferenceQuery.from(getEntityClass());
			root.fetch("preferenceEntryList", JoinType.INNER);
			root.fetch("preferenceReview", JoinType.LEFT);			
			
			preferenceQuery = preferenceQuery;	
			preferenceQuery.distinct(true);
			
			List<Predicate> predicateList = new ArrayList<Predicate>();
			
			if (userId != null) {
				Predicate userPredicate = criteriaBuilder.equal(root.get("userId"), userId);
				predicateList.add(userPredicate);
			}
			
			if (projectId != null) {
				Predicate projectPredicate = criteriaBuilder.equal(root.get("projectId"), projectId);
				predicateList.add(projectPredicate);
			}
			
			/*
			 * Criando subquery para trazer os últimos registros de cada usuario/projeto
			 */
			Subquery<Timestamp> subquery = preferenceQuery.subquery(Timestamp.class);
			
			Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
			Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
			
			subquery = subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
			
			List<Predicate> subqueryPredicateList = new ArrayList<>();
			subqueryPredicateList.add(criteriaBuilder.equal(root.get("userId"), p2.get("userId")));
			subqueryPredicateList.add(criteriaBuilder.equal(root.get("projectId"), p2.get("projectId")));
			
			subquery = subquery.where(subqueryPredicateList.toArray(new Predicate[0]));
			
			Predicate registeredAtPredicate = criteriaBuilder.equal(root.get("registeredAt"), subquery);
			predicateList.add(registeredAtPredicate);
			
			
			//aplicando filtros
			preferenceQuery = preferenceQuery.where(predicateList.toArray(new Predicate[0]));
			
			
			TypedQuery<PreferenceEntity> typedQuery = getEntityManager().createQuery(preferenceQuery);
			
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
	public Long countAllLast() {
		return countAllLastPreferenceByProject(null);
	}
	
	@Override
	public Long  countAllLastPreferenceByProject(Long projectId) {
		//obtendo quantidade de registros
		Long countResult = countAllLast(projectId, null);
		
		return countResult;
	}
	
	@Override
	public Long  countAllLastPreferenceByUser(Long userId) {
		//obtendo quantidade de registros
		Long countResult = countAllLast(userId, null);
		
		return countResult;
	}
	
	private Long countAllLast(
			Long projectId,
			Long userId) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> root = countQuery.from(getEntityClass());
		
		countQuery.select(criteriaBuilder.countDistinct(root));
		
		List<Predicate> predicateList = new ArrayList<>();
		
		if (projectId != null) {
			Predicate projectPredicate = criteriaBuilder.equal(root.get("projectId"), projectId);
			predicateList.add(projectPredicate);
		} else if (userId != null) {
			Predicate userPredicate = criteriaBuilder.equal(root.get("userId"), userId);
			predicateList.add(userPredicate);
		}

		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = countQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
		Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		
		List<Predicate> subqueryPredicateList = new ArrayList<>();
		subqueryPredicateList.add(criteriaBuilder.equal(root.get("userId"), p2.get("userId")));
		subqueryPredicateList.add(criteriaBuilder.equal(root.get("projectId"), p2.get("projectId")));
		
		subquery.where(subqueryPredicateList.toArray(new Predicate[0]));
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(root.get("registeredAt"), subquery);
		predicateList.add(registeredAtPredicate);
		
		//aplicando filtros
		countQuery.where(predicateList.toArray(new Predicate[0]));
		
		//obtendo quantidade de registros
		Long countResult = getEntityManager().createQuery(countQuery).getSingleResult();
		
		return countResult;
		
	}

	@Override
	public Double averageRatingByItem(Long itemId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		
		Root<PreferenceEntity> root = criteriaQuery.from(getEntityClass());
				
		CriteriaQuery<Double> select = criteriaQuery.select(criteriaBuilder.avg( root.<Double>get("value") ));
		
		List<Predicate> predicateList = new ArrayList<>();
		
		//filtrando por projeto
		Predicate projectPredicate = criteriaBuilder.equal(root.get("projectId"), itemId);
		predicateList.add(projectPredicate);
		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
		Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		
		List<Predicate> subqueryPredicateList = new ArrayList<>();
		subqueryPredicateList.add(criteriaBuilder.equal(root.get("userId"), p2.get("userId")));
		subqueryPredicateList.add(criteriaBuilder.equal(root.get("projectId"), p2.get("projectId")));
		
		subquery.where(subqueryPredicateList.toArray(new Predicate[0]));
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(root.get("registeredAt"), subquery);
		predicateList.add(registeredAtPredicate);
		
		
		//aplicando filtros
		select.where(predicateList.toArray(new Predicate[0]));
		
		//obtendo média dos valores
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}

	@Override
	public List<ImmutablePair<OhLohProjectEntity, Long>> findRatingCountByProject(
			Integer startAt, 
			Integer offset) {
		
		
		List<ImmutablePair<OhLohProjectEntity, Long>> resultList = null;
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
		
		Root<PreferenceEntity> root = tupleQuery.from(PreferenceEntity.class);
		Join<PreferenceEntity, OhLohProjectEntity> projectJoin = root.join("project", JoinType.INNER);
		
		
		Expression<Long> ratingCount = criteriaBuilder.count(root);
		
		tupleQuery = tupleQuery
			.multiselect(projectJoin, ratingCount)
			.groupBy(
					projectJoin.get("id")
				)
			.having(criteriaBuilder.gt(ratingCount, 0))
			.orderBy(criteriaBuilder.desc(ratingCount));
		
		
		//pegando apenas as opiniões mais atuais
		Subquery<Timestamp> subquery = tupleQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> preferenceRoot = subquery.from(PreferenceEntity.class);
		Expression<Timestamp> greatestRegisteredAt = preferenceRoot.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		subquery.where(
				criteriaBuilder.equal(root.get("projectId"), preferenceRoot.get("projectId")),
				criteriaBuilder.equal(root.get("userId"), preferenceRoot.get("userId"))
			);
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(root.get("registeredAt"), subquery);
		tupleQuery = tupleQuery.where(registeredAtPredicate);
		
		//ordenando pelos que possuem mais reviews
		tupleQuery = tupleQuery.orderBy(criteriaBuilder.desc(ratingCount));
				
		TypedQuery<Tuple> tupleTypedQuery = getEntityManager().createQuery(tupleQuery);
		
		if (offset != null) {
			tupleTypedQuery.setMaxResults(offset);
		}
		
		if (startAt != null) {
			tupleTypedQuery.setFirstResult(startAt);
		}
		
		List<Tuple> tupleList = tupleTypedQuery.getResultList();
		
		if (tupleList != null && ! tupleList.isEmpty()) {
			resultList = new ArrayList<>();
			
			for (Tuple tuple : tupleList) {
				ImmutablePair<OhLohProjectEntity, Long> pair = new ImmutablePair<OhLohProjectEntity, Long>(
						tuple.get(0, OhLohProjectEntity.class), 
						tuple.get(1, Long.class));
				
				resultList.add(pair);
			}
		}
		
		return resultList;
	}

	@Override
	public PreferenceEntity findLastOverallPreferenceByUserAndItem(
			Long userId,
			Long itemId) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<PreferenceEntity> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());

		Root<PreferenceEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<PreferenceEntity> select = criteriaQuery.select(root);

		root.join("preferenceReview", JoinType.LEFT);
		root.join("preferenceEntryList", JoinType.LEFT);

		List<Predicate> predicateList = new ArrayList<>();

		if (userId != null) {
			Predicate userPredicate = criteriaBuilder.equal(root.get("userId"), userId);
			predicateList.add(userPredicate);
		}

		if (itemId != null) {
			Predicate projectPredicate = criteriaBuilder.equal(root.get("projectId"), itemId);
			predicateList.add(projectPredicate);
		}


		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);

		Root<PreferenceEntity> preferenceRoot = subquery.from(PreferenceEntity.class);
		Expression<Timestamp> greatestRegisteredAt = preferenceRoot.get("registeredAt");

		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		subquery.where(
				criteriaBuilder.equal(root.get("projectId"), preferenceRoot.get("projectId")),
				criteriaBuilder.equal(root.get("userId"), preferenceRoot.get("userId"))
			);

		Predicate registeredAtPredicate = criteriaBuilder.equal(root.get("registeredAt"), subquery);
		predicateList.add(registeredAtPredicate);

		//aplicando filtros
		select.where(predicateList.toArray(new Predicate[0]));

		TypedQuery<PreferenceEntity> query = getEntityManager().createQuery(criteriaQuery);

		PreferenceEntity preference = null;

		try {
			preference = query.getSingleResult();
		} catch (NoResultException | NonUniqueResultException ex) {
		}

		return preference;	
	}

}
