package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OverallPreferenceRepository;

@Repository(OverallPreferenceRepositoryImpl.BEAN_NAME)
public class OverallPreferenceRepositoryImpl  extends BaseRepositoryImpl<Long, PreferenceEntity>
		implements OverallPreferenceRepository {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1524233731959689000L;
	
	public static final String BEAN_NAME =  "overallPreferenceRepository";
	
	public OverallPreferenceRepositoryImpl() {
		super(PreferenceEntity.class);
	}
	
	@Override
	public Long countAllLastByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> p1 = criteriaQuery.from(getEntityClass());
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(p1));
				
		//filtrando por projeto
		Predicate namePredicate = criteriaBuilder.equal(p1.get("projectId"), project.getId());
		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
		Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		subquery.where(criteriaBuilder.equal(p1.get("userId"), p2.get("userId")));
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(p1.get("registeredAt"), subquery);
		
		select.where(namePredicate, registeredAtPredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllByProjectAndUser(
			OhLohProjectEntity project,
			UserEntity user) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> root = criteriaQuery.from(getEntityClass());
		
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(root));
		
		List<Predicate> predicateList = new ArrayList<>();
		
		if (user != null && user.getId() != null) {
			Predicate userPredicate = criteriaBuilder.equal(root.get("userId"), user.getId());
			predicateList.add(userPredicate);
		}
		
		if (project != null && project.getId() != null) {
			Predicate projectPredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
			predicateList.add(projectPredicate);
		}
				
		select.where(predicateList.toArray(new Predicate[0]));
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	public Double averagePreferenceByProject(OhLohProjectEntity project) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		
		Root<PreferenceEntity> p1 = criteriaQuery.from(getEntityClass());
		Path<Double> valuePath = p1.get("value");
		
		CriteriaQuery<Double> select = criteriaQuery.select(criteriaBuilder.avg( valuePath ));
		
		Predicate namePredicate = criteriaBuilder.equal(p1.get("projectId"), project.getId());
		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
		Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		subquery.where(criteriaBuilder.equal(p1.get("userId"), p2.get("userId")));
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(p1.get("registeredAt"), subquery);
		
		select.where(namePredicate, registeredAtPredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	
	public List<PreferenceEntity> findAllLastByProject(OhLohProjectEntity project) {
		return findAllLastByProject(project, null, null);
	}
	
	
	
	public List<PreferenceEntity> findAllLastByProject(OhLohProjectEntity project, Integer startAt, Integer offset) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<PreferenceEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<PreferenceEntity> p1 = criteriaQuery.from(getEntityClass());
		CriteriaQuery<PreferenceEntity> select = criteriaQuery.select(p1);
		
		p1.fetch("preferenceReview", JoinType.LEFT);
		p1.fetch("preferenceEntryList", JoinType.LEFT);
		p1.fetch("user", JoinType.LEFT);

		List<Predicate> predicateList = new ArrayList<>();
		
		Predicate projectPredicate = criteriaBuilder.equal(p1.get("projectId"), project.getId());
		predicateList.add(projectPredicate);
		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
		Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		subquery.where(criteriaBuilder.equal(p1.get("userId"), p2.get("userId")));
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(p1.get("registeredAt"), subquery);
		predicateList.add(registeredAtPredicate);
		
		//aplicando os filtros
		select.where(predicateList.toArray(new Predicate[0]));
		
		TypedQuery<PreferenceEntity> typedQuery = getEntityManager().createQuery(criteriaQuery);
		
		
		if (startAt != null) {
			typedQuery.setFirstResult(startAt);
		}
		
		if (offset != null) {
			typedQuery.setMaxResults(offset);
		}

		List<PreferenceEntity> preferenceList = typedQuery.getResultList();
		
		return preferenceList;
	}
	
	public List<PreferenceEntity> findAllByProjectAndUser(
			OhLohProjectEntity project,
			UserEntity user) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<PreferenceEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<PreferenceEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<PreferenceEntity> select = criteriaQuery.select(root);
		
		root.fetch("preferenceReview", JoinType.LEFT);
		root.fetch("preferenceEntryList", JoinType.LEFT);

		List<Predicate> predicateList = new ArrayList<>();
		
		if (user != null && user.getId() != null) {
			Predicate userPredicate = criteriaBuilder.equal(root.get("userId"), user.getId());
			predicateList.add(userPredicate);
		}
		
		if (project != null && project.getId() != null) {
			Predicate projectPredicate = criteriaBuilder.equal(root.get("projectId"), project.getId());
			predicateList.add(projectPredicate);
		}
		
		select.where(predicateList.toArray(new Predicate[0]));

		TypedQuery<PreferenceEntity> query = getEntityManager().createQuery(criteriaQuery);

		List<PreferenceEntity> preferenceList = query.getResultList();
		
		return preferenceList;		
	}
	
}
