
package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntryEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.RatingByCriteriumRepository;

@Repository(RatingByCriteriumRepositoryImpl.BEAN_NAME)
public class RatingByCriteriumRepositoryImpl
		extends AbstractRatingRepositoryImpl
		implements RatingByCriteriumRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3849857783153216318L;
	
	public static final String BEAN_NAME = "preferenceByCriteriumRepository";
	
	
	public RatingByCriteriumRepositoryImpl() {
	}
	
	@Override
	public Map<ImmutablePair<Long, Long>, Double> findAllLastPreferenceByCriterium(
			Long criteriumId) {
		return findAllLastPreferenceByCriteriumAndItem(criteriumId, null);
	}

	@Override
	public Map<ImmutablePair<Long, Long>, Double> findAllLastPreferenceByCriteriumAndItem(
			Long criteriumId, 
			Long itemId) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
		
		Root<PreferenceEntity> root = tupleQuery.from(getEntityClass());
		Join<PreferenceEntity, PreferenceEntryEntity> join = root.join("preferenceEntryList", JoinType.LEFT);
		
		//campos da preferência geral
		Path<Long> accountIdPath = root.get("userId");		
		Path<Long> projectIdPath = root.get("projectId");
		
		//campos de cada critério
		Path<Long> criteriumIdPath = join.get("criteriumId");
		Path<Float> valuePath = join.get("value");
		
		tupleQuery = tupleQuery.multiselect(
				accountIdPath,
				projectIdPath,
				valuePath);
		
		List<Predicate> predicateList = new ArrayList<>();
		
		//filtrando por critério
		Predicate criteriumPredicate = criteriaBuilder.equal(criteriumIdPath, criteriumId);
		predicateList.add(criteriumPredicate);
		
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
	public Long countAllLastByCriterium(Long criteriumId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> root = countQuery.from(getEntityClass());
		Join<PreferenceEntity, PreferenceEntryEntity> join = root.join("preferenceEntryList", JoinType.LEFT);
		
		countQuery.select(criteriaBuilder.countDistinct(root));
		
		List<Predicate> predicateList = new ArrayList<>();
		
		//filtrando pelo critério
		Predicate criteriumPredicate = criteriaBuilder.equal(join.get("criteriumId"), criteriumId);
		predicateList.add(criteriumPredicate);
		
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
	public Double averagePreferenceByItemAndCriterium(
			Long itemId,
			Long criteriumId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		
		Root<PreferenceEntity> root = criteriaQuery.from(getEntityClass());
		Join<PreferenceEntity, PreferenceEntryEntity> join = root.join("preferenceEntryList", JoinType.LEFT);
				
		Path<Float> valuePath = join.get("value");
		
		CriteriaQuery<Double> select = criteriaQuery.select(criteriaBuilder.avg( valuePath ));
		
		List<Predicate> predicateList = new ArrayList<>();
		
		//filtrando por projeto
		Predicate projectPredicate = criteriaBuilder.equal(root.get("projectId"), itemId);
		predicateList.add(projectPredicate);
		
		//filtrando por critério de recomendação
		Predicate criteriumPredicate = criteriaBuilder.equal(join.get("criteriumId"), criteriumId);
		predicateList.add(criteriumPredicate);
		
		//puxando apenas as notas válidas
		Predicate availablePredicate = criteriaBuilder.or(
				criteriaBuilder.isNull(join.get("notAvailable")),
				criteriaBuilder.equal(join.get("notAvailable"), Boolean.FALSE));
		predicateList.add(availablePredicate);
		
		
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

}

