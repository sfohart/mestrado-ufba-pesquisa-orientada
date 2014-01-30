package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.model.Preference;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntryEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.CriteriumPreferenceRepository;

/**
 * @author leandro.ferreira
 *
 */
@Repository(CriteriumPreferenceRepositoryImpl.BEAN_NAME)
public class CriteriumPreferenceRepositoryImpl extends BaseRepositoryImpl<Long, PreferenceEntity> implements CriteriumPreferenceRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7259236332352137978L;
	
	public static final String BEAN_NAME = "criteriumPreferenceRepository";
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public CriteriumPreferenceRepositoryImpl() {
		super(PreferenceEntity.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Long countAllByCriterium(Long criteriumId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> root = countQuery.from(PreferenceEntity.class);
		Join<PreferenceEntity, PreferenceEntryEntity> join = root.join("preferenceEntryList", JoinType.LEFT);
		
		countQuery.select(criteriaBuilder.countDistinct(root));
		
		Predicate predicate = criteriaBuilder.equal(join.get("criteriumId"), criteriumId);
		countQuery.where(predicate);
		
		Long countResult = entityManager.createQuery(countQuery).getSingleResult();
		
		return countResult;
	}
	
	public Double averagePreferenceByProjectAndCriterium(Long projectId, Long criteriumId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		
		Root<PreferenceEntity> p1 = criteriaQuery.from(getEntityClass());
		Join<PreferenceEntity, PreferenceEntryEntity> join = p1.join("preferenceEntryList", JoinType.LEFT);
				
		Path<Double> valuePath = join.get("value");
		
		CriteriaQuery<Double> select = criteriaQuery.select(criteriaBuilder.avg( valuePath ));
		
		//filtrando por projeto
		Predicate projectPredicate = criteriaBuilder.equal(p1.get("projectId"), projectId);
		
		//filtrando por critério de recomendação
		Predicate criteriumPredicate = criteriaBuilder.equal(join.get("criteriumId"), criteriumId);
		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
		Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		
		List<Predicate> subqueryPredicateList = new ArrayList<>();
		subqueryPredicateList.add(criteriaBuilder.equal(p1.get("userId"), p2.get("userId")));
		subqueryPredicateList.add(criteriaBuilder.equal(p1.get("projectId"), p2.get("projectId")));
		
		subquery.where(subqueryPredicateList.toArray(new Predicate[0]));
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(p1.get("registeredAt"), subquery);
		
		select.where(
				projectPredicate, 
				registeredAtPredicate, 
				criteriumPredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Preference> findAllByCriterium(Long criteriumID) {
		return findAllByCriterium(criteriumID, null, null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Preference> findAllByCriterium(Long criteriumID, Integer limit, Integer offset) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
		
		Root<PreferenceEntity> root = tupleQuery.from(PreferenceEntity.class);
		Join<PreferenceEntity, PreferenceEntryEntity> join = root.join("preferenceEntryList", JoinType.LEFT);
		
		Path<Long> accountIdPath = root.get("accountId");		
		Path<Long> projectIdPath = root.get("projectId");
		Path<Long> criteriumIdPath = join.get("criteriumId");
		Path<Float> valuePath = join.get("value");
		
		tupleQuery.multiselect(
				accountIdPath,
				projectIdPath,
				valuePath);
		
		Predicate predicate = criteriaBuilder.equal(criteriumIdPath, criteriumID);
		tupleQuery.where(predicate);
		
		TypedQuery<Tuple> tupleTypedQuery = entityManager.createQuery(tupleQuery);
		
		if (limit != null) {
			tupleTypedQuery.setMaxResults(limit);
		}
		
		if (offset != null) {
			tupleTypedQuery.setFirstResult(offset);
		}
		
		List<Tuple> preferenceList = tupleTypedQuery.getResultList();
		
		List<Preference> preferences = createPreferenceList(
				preferenceList,
				accountIdPath,
				projectIdPath,
				valuePath);
		
		return preferences;
	}

	private List<Preference> createPreferenceList(
			List<Tuple> preferenceList, 
			Path<Long> accountIdPath,
			Path<Long> projectIdPath, 
			Path<Float> valuePath) {
		
		List<Preference> PreferenceList = new ArrayList<>();
		
		if (preferenceList != null) {
			for (Tuple tuple : preferenceList) {
				assert tuple.get(0) == tuple.get(accountIdPath);
				assert tuple.get(1) == tuple.get(projectIdPath);
				assert tuple.get(2) == tuple.get(valuePath);
				
				Long userID = null;
				Long itemID = null;
				Float value = null;
				
				if (tuple.get(accountIdPath) != null) {
					userID = tuple.get(accountIdPath);
				}
				
				if (tuple.get(projectIdPath) != null) {
					itemID = tuple.get(projectIdPath);
				}
				
				
				if (tuple.get(valuePath) != null) {
					value = tuple.get(valuePath);
				}
				
				Preference preference = new GenericPreference(userID, itemID, value);
				PreferenceList.add(preference);
			}
			
		}		
		
		return PreferenceList;
	}

}
