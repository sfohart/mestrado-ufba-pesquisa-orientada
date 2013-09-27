package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntryEntity;
import br.ufba.dcc.mestrado.computacao.recommender.CriteriumPreference;
import br.ufba.dcc.mestrado.computacao.repository.base.CriteriumPreferenceRepository;

/**
 * @author leandro.ferreira
 *
 */
@Repository
public class CriteriumPreferenceRepositoryImpl implements CriteriumPreferenceRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7259236332352137978L;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional(readOnly = true)
	public Long countAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> root = countQuery.from(PreferenceEntity.class);
		Join<PreferenceEntity, PreferenceEntryEntity> join = root.join("preferenceEntryList", JoinType.LEFT);
		
		countQuery.select(criteriaBuilder.countDistinct(join));
		
		Long countResult = entityManager.createQuery(countQuery).getSingleResult();
		
		return countResult;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<CriteriumPreference> findAll() {
		return findAll(null,null);
	}
	
	
	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CriteriumPreference> findAll(Integer limit, Integer offset) {
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
				criteriumIdPath,
				valuePath);
		
		TypedQuery<Tuple> tupleTypedQuery = entityManager.createQuery(tupleQuery);
		
		if (limit != null) {
			tupleTypedQuery.setMaxResults(limit);
		}
		
		if (offset != null) {
			tupleTypedQuery.setFirstResult(offset);
		}
		
		List<Tuple> preferenceList = tupleTypedQuery.getResultList();
		
		List<CriteriumPreference> preferences = createCriteriumPreferenceList(
				preferenceList,
				accountIdPath,
				projectIdPath,
				criteriumIdPath,
				valuePath);
		
		return preferences;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long countAllByUser(Long userID) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> root = countQuery.from(PreferenceEntity.class);
		Join<PreferenceEntity, PreferenceEntryEntity> join = root.join("preferenceEntryList", JoinType.LEFT);
		
		countQuery.select(criteriaBuilder.countDistinct(root));
		
		Predicate predicate = criteriaBuilder.equal(join.get("accountId"), userID);
		countQuery.where(predicate);
		
		Long countResult = entityManager.createQuery(countQuery).getSingleResult();
		
		return countResult;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CriteriumPreference> findAllUser(Long userID) {
		return findAllUser(userID, null, null);
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CriteriumPreference> findAllUser(Long userID, Integer limit, Integer offset) {
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
				criteriumIdPath,
				valuePath);
		
		Predicate predicate = criteriaBuilder.equal(root.get("accountId"), userID);
		tupleQuery.where(predicate);
		
		TypedQuery<Tuple> tupleTypedQuery = entityManager.createQuery(tupleQuery);
		
		if (limit != null) {
			tupleTypedQuery.setMaxResults(limit);
		}
		
		if (offset != null) {
			tupleTypedQuery.setFirstResult(offset);
		}
		
		List<Tuple> preferenceList = tupleTypedQuery.getResultList();
		
		List<CriteriumPreference> preferences = createCriteriumPreferenceList(
				preferenceList,
				accountIdPath,
				projectIdPath,
				criteriumIdPath,
				valuePath);
		
		return preferences;
	}

	@Override
	@Transactional(readOnly = true)
	public Long countAllByItem(Long itemID) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> root = countQuery.from(PreferenceEntity.class);
		Join<PreferenceEntity, PreferenceEntryEntity> join = root.join("preferenceEntryList", JoinType.LEFT);
		
		countQuery.select(criteriaBuilder.countDistinct(root));
		
		Predicate predicate = criteriaBuilder.equal(join.get("projectId"), itemID);
		countQuery.where(predicate);
		
		Long countResult = entityManager.createQuery(countQuery).getSingleResult();
		
		return countResult;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CriteriumPreference> findAllByItem(Long itemID) {
		return findAllByItem(itemID, null, null);
	}
	
	/**
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public List<CriteriumPreference> findAllByItem(Long itemID, Integer limit, Integer offset) {
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
				criteriumIdPath,
				valuePath);
		
		Predicate predicate = criteriaBuilder.equal(root.get("projectId"), itemID);
		tupleQuery.where(predicate);
		
		TypedQuery<Tuple> tupleTypedQuery = entityManager.createQuery(tupleQuery);
		
		if (limit != null) {
			tupleTypedQuery.setMaxResults(limit);
		}
		
		if (offset != null) {
			tupleTypedQuery.setFirstResult(offset);
		}
		
		List<Tuple> preferenceList = tupleTypedQuery.getResultList();
		
		List<CriteriumPreference> preferences = createCriteriumPreferenceList(
				preferenceList,
				accountIdPath,
				projectIdPath,
				criteriumIdPath,
				valuePath);
		
		return preferences;
	}

	@Override
	@Transactional(readOnly = true)
	public Long countAllByCriterium(Long criteriumID) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> root = countQuery.from(PreferenceEntity.class);
		Join<PreferenceEntity, PreferenceEntryEntity> join = root.join("preferenceEntryList", JoinType.LEFT);
		
		countQuery.select(criteriaBuilder.countDistinct(root));
		
		Predicate predicate = criteriaBuilder.equal(join.get("criteriumId"), criteriumID);
		countQuery.where(predicate);
		
		Long countResult = entityManager.createQuery(countQuery).getSingleResult();
		
		return countResult;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CriteriumPreference> findAllByCriterium(Long criteriumID) {
		return findAllByCriterium(criteriumID, null, null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CriteriumPreference> findAllByCriterium(Long criteriumID, Integer limit, Integer offset) {
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
				criteriumIdPath,
				valuePath);
		
		Predicate predicate = criteriaBuilder.equal(join.get("criteriumId"), criteriumID);
		tupleQuery.where(predicate);
		
		TypedQuery<Tuple> tupleTypedQuery = entityManager.createQuery(tupleQuery);
		
		if (limit != null) {
			tupleTypedQuery.setMaxResults(limit);
		}
		
		if (offset != null) {
			tupleTypedQuery.setFirstResult(offset);
		}
		
		List<Tuple> preferenceList = tupleTypedQuery.getResultList();
		
		List<CriteriumPreference> preferences = createCriteriumPreferenceList(
				preferenceList,
				accountIdPath,
				projectIdPath,
				criteriumIdPath,
				valuePath);
		
		return preferences;
	}


	private List<CriteriumPreference> createCriteriumPreferenceList(
			List<Tuple> preferenceList, 
			Path<Long> accountIdPath,
			Path<Long> projectIdPath, 
			Path<Long> criteriumIdPath,
			Path<Float> valuePath) {
		
		List<CriteriumPreference> criteriumPreferenceList = new ArrayList<>();
		
		if (preferenceList != null) {
			for (Tuple tuple : preferenceList) {
				assert tuple.get(0) == tuple.get(accountIdPath);
				assert tuple.get(1) == tuple.get(projectIdPath);
				assert tuple.get(2) == tuple.get(criteriumIdPath);
				assert tuple.get(3) == tuple.get(valuePath);
				
				CriteriumPreference preference = new CriteriumPreference();
				
				if (tuple.get(accountIdPath) != null) {
					preference.setUserID(tuple.get(accountIdPath));
				}
				
				if (tuple.get(projectIdPath) != null) {
					preference.setItemID(tuple.get(projectIdPath));
				}
				
				if (tuple.get(criteriumIdPath) != null) {
					preference.setCriteriumID(tuple.get(criteriumIdPath));
				}
				
				if (tuple.get(valuePath) != null) {
					preference.setValue(tuple.get(valuePath));
				}
				
				criteriumPreferenceList.add(preference);
			}
			
		}		
		
		return criteriumPreferenceList;
	}

}
