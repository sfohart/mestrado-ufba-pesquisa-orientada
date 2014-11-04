package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectDetailPageViewRepository;

@Repository(ProjectDetailPageViewRepositoryImpl.BEAN_NAME)
public class ProjectDetailPageViewRepositoryImpl 
		extends BaseRepositoryImpl<Long, ProjectDetailPageViewEntity>
		implements ProjectDetailPageViewRepository {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8685723992770230557L;
	
	
	public static final String BEAN_NAME =  "projectDetailPageViewRepository";

	public ProjectDetailPageViewRepositoryImpl() {
		super(ProjectDetailPageViewEntity.class);
	}
	
	

	@Override
	public List<ImmutablePair<UserEntity, OhLohProjectEntity>> findAllProjectDetailViews() {
		return findAllProjectDetailViews(null,null);
	}
	
	@Override
	public List<ImmutablePair<UserEntity, OhLohProjectEntity>> findAllProjectDetailViews(
			Integer startAt, 
			Integer offset) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
		
		Root<ProjectDetailPageViewEntity> root = tupleQuery.from(getEntityClass());
		
		Path<OhLohProjectEntity> projectPath = root.get("project");
		Path<UserEntity> userPath = root.get("user");
		
		tupleQuery.multiselect(
				userPath,
				projectPath
			);
		
		tupleQuery.distinct(true);
		
		Predicate predicate = criteriaBuilder.isNotNull(userPath);
		tupleQuery.where(predicate);
		
		TypedQuery<Tuple> tupleTypedQuery = getEntityManager().createQuery(tupleQuery);
		
		
		if (offset != null) {
			tupleTypedQuery.setMaxResults(offset);
		}
		
		if (startAt != null) {
			tupleTypedQuery.setFirstResult(startAt);
		}
		
		List<Tuple> tupleList = tupleTypedQuery.getResultList();
		
		List<ImmutablePair<UserEntity, OhLohProjectEntity>> pageViewList = null; 
		
		if (tupleList != null) {
			pageViewList = new ArrayList<>();
			
			for (Tuple tuple : tupleList) {
				ImmutablePair<UserEntity, OhLohProjectEntity> userItemPair = new ImmutablePair<UserEntity, OhLohProjectEntity>(
						tuple.get(0, UserEntity.class),
						tuple.get(1, OhLohProjectEntity.class) 
						);
				
				pageViewList.add(userItemPair);
			}
		}
		
		return pageViewList;
	}
	
	public List<OhLohProjectEntity> findAllProjectRecentlyViewed(
			UserEntity user,
			Integer startAt, 
			Integer offset) {
		
		List<OhLohProjectEntity> result = null;
		
		if (user != null) {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<OhLohProjectEntity> criteriaQuery = criteriaBuilder.createQuery(OhLohProjectEntity.class);
			
			Root<ProjectDetailPageViewEntity> root = criteriaQuery.from(getEntityClass());
			Join<ProjectDetailPageViewEntity, OhLohProjectEntity> projectJoin = root.join("project", JoinType.INNER);
			
			criteriaQuery.select(projectJoin);
						
			//Subquery pra selecionar apenas os projetos mais recentes
			
			Subquery<Timestamp> viewedAtSubquery = criteriaQuery.subquery(Timestamp.class);
			Root<ProjectDetailPageViewEntity> subqueryRoot = viewedAtSubquery.from(ProjectDetailPageViewEntity.class);
			Expression<Timestamp> greatestViewedAt = subqueryRoot.get("viewedAt");
			
			viewedAtSubquery.select(criteriaBuilder.greatest((greatestViewedAt)));
			
			List<Predicate> predicateSubqueryList = new ArrayList<Predicate>();
			predicateSubqueryList.add(criteriaBuilder.equal(root.get("projectId"), subqueryRoot.get("projectId")));
			if (user != null) {
				predicateSubqueryList.add(criteriaBuilder.equal(root.get("userId"), subqueryRoot.get("userId")));
			}
			
			
			viewedAtSubquery.where(
					predicateSubqueryList.toArray(new Predicate[0])
				);
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("viewedAt")));
			
			
			List<Predicate> predicateQueryList = new ArrayList<Predicate>();
			predicateQueryList.add(criteriaBuilder.equal(root.get("viewedAt"), viewedAtSubquery));
			
			if (user != null && user.getId() != null) {
				predicateQueryList.add(criteriaBuilder.equal(root.get("userId"), user.getId()));
			}
			
			
			criteriaQuery.where(predicateQueryList.toArray(new Predicate[0]));
			
			TypedQuery<OhLohProjectEntity> typedQuery = getEntityManager().createQuery(criteriaQuery);
			
			if (offset != null) {
				typedQuery.setMaxResults(offset);
			}
			
			if (startAt != null) {
				typedQuery.setFirstResult(startAt);
			}
			
			result = typedQuery.getResultList();
		}
		
			
		return result;	
	}

	@Override
	public List<ImmutablePair<OhLohProjectEntity, Long>> findAllProjectDetailViewsCount() {
		return findAllProjectDetailViewsCount(null,null);
	}

	@Override
	public List<ImmutablePair<OhLohProjectEntity, Long>> findAllProjectDetailViewsCount(
			Integer startAt, Integer offset) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();

		Root<ProjectDetailPageViewEntity> root = tupleQuery.from(ProjectDetailPageViewEntity.class);
		Join<ProjectDetailPageViewEntity, OhLohProjectEntity> projectJoin = root.join("project", JoinType.INNER);

		Expression<Long> pageViewCount = criteriaBuilder.count(root.get("id"));

		tupleQuery
			.multiselect(
					projectJoin, 
					pageViewCount
				)
			.groupBy(projectJoin.get("id"))
			.having(criteriaBuilder.gt(pageViewCount, 0))
			.orderBy(criteriaBuilder.desc(pageViewCount));

		tupleQuery.orderBy(
				criteriaBuilder.desc(pageViewCount)
				);
		
		TypedQuery<Tuple> tupleTypedQuery = getEntityManager().createQuery(tupleQuery);

		if (offset != null) {
			tupleTypedQuery.setMaxResults(offset);
		}

		if (startAt != null) {
			tupleTypedQuery.setFirstResult(startAt);
		}

		List<Tuple> tupleList = tupleTypedQuery.getResultList();
		
		List<ImmutablePair<OhLohProjectEntity, Long>> resultList = null;
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
	
	

}
