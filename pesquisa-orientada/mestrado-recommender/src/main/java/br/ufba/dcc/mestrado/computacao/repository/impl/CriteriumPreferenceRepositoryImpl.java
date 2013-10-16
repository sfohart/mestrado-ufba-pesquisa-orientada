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

import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.model.Preference;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntryEntity;
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
