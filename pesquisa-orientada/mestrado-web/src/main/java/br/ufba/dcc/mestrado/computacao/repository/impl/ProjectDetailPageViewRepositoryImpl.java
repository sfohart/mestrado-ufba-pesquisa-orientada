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

import org.apache.commons.lang3.StringUtils;
import org.apache.mahout.cf.taste.impl.model.BooleanPreference;
import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewEntity;
import br.ufba.dcc.mestrado.computacao.entities.web.pageview.ProjectDetailPageViewInfo;
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
	
	
	public List<OhLohProjectEntity> findAllProjectRecentlyViewed(
			UserEntity user,
			String ipAddress,
			Integer startAt, 
			Integer offset) {
		
		List<OhLohProjectEntity> result = null;
		
		if (user != null || ! StringUtils.isEmpty(ipAddress)) {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<OhLohProjectEntity> criteriaQuery = criteriaBuilder.createQuery(OhLohProjectEntity.class);
			
			Root<ProjectDetailPageViewEntity> root = criteriaQuery.from(getEntityClass());
			
			Join<ProjectDetailPageViewEntity, OhLohProjectEntity> projectJoin = root.join("project", JoinType.INNER);
			Join<ProjectDetailPageViewEntity, UserEntity> userJoin = root.join("user", JoinType.LEFT);
			
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
			
			if (! StringUtils.isEmpty(ipAddress)) {
				predicateSubqueryList.add(criteriaBuilder.equal(root.get("ipAddress"), subqueryRoot.get("ipAddress")));
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
			
			if (! StringUtils.isEmpty(ipAddress)) {
				predicateQueryList.add(criteriaBuilder.equal(root.get("ipAddress"), ipAddress));
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
	public List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo() {
		return findAllProjectDetailPageViewInfo(null, null);
	}
	
	@Override
	public List<ProjectDetailPageViewInfo> findAllProjectDetailPageViewInfo(
			Integer startAt, 
			Integer offset) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
		
		Root<ProjectDetailPageViewEntity> root = tupleQuery.from(ProjectDetailPageViewEntity.class);
		Join<PreferenceEntity, OhLohProjectEntity> projectJoin = root.join("project", JoinType.INNER);
		
		Expression<Long> pageViewCount = criteriaBuilder.count(root.get("id"));
		
		tupleQuery
			.multiselect(projectJoin, pageViewCount)
			.groupBy(
					projectJoin.get("id")
				)
			.having(criteriaBuilder.gt(pageViewCount, 0))
			.orderBy(criteriaBuilder.desc(pageViewCount));
		
		TypedQuery<Tuple> tupleTypedQuery = getEntityManager().createQuery(tupleQuery);
		
		if (offset != null) {
			tupleTypedQuery.setMaxResults(offset);
		}
		
		if (startAt != null) {
			tupleTypedQuery.setFirstResult(startAt);
		}
		
		List<Tuple> tupleList = tupleTypedQuery.getResultList();
		List<ProjectDetailPageViewInfo> resultList = null;
		
		if (tupleList != null && ! tupleList.isEmpty()) {
			resultList = new ArrayList<>();
			
			for (Tuple tuple : tupleList) {
				assert tuple.get(0) == tuple.get(projectJoin);
				assert tuple.get(1) == tuple.get(pageViewCount);
				
				
				ProjectDetailPageViewInfo info = new ProjectDetailPageViewInfo();
				
				OhLohProjectEntity projectInfo = tuple.get(projectJoin);
				Long pageViewCountInfo = tuple.get(pageViewCount);
				
				info.setProject(projectInfo);
				info.setPageViewCount(pageViewCountInfo);
				
				resultList.add(info);
			}
		}
		
		return resultList;
	}
	
	public List<BooleanPreference> findAllPageViewDataWithUsers() {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
		
		Root<ProjectDetailPageViewEntity> root = tupleQuery.from(getEntityClass());
		
		Path<Long> projectIdPath = root.get("projectId");
		Path<Long> userIdPath = root.get("userId");
		
		tupleQuery.multiselect(
				projectIdPath,
				userIdPath
			);
		
		tupleQuery.distinct(true);
		
		Predicate predicate = criteriaBuilder.isNotNull(userIdPath);
		tupleQuery.where(predicate);
		
		TypedQuery<Tuple> tupleTypedQuery = getEntityManager().createQuery(tupleQuery);
		List<Tuple> tupleList = tupleTypedQuery.getResultList();
		
		List<BooleanPreference> preferenceList = null;
		
		if (tupleList != null) {
			preferenceList = new ArrayList<>();
			
			for (Tuple tuple : tupleList) {
				assert tuple.get(0) == tuple.get(projectIdPath);
				assert tuple.get(1) == tuple.get(userIdPath);
				
				Long userID = tuple.get(userIdPath);
				Long itemID = tuple.get(projectIdPath);
				
				BooleanPreference preference = new BooleanPreference(userID, itemID);
				preferenceList.add(preference);
			}
		}
		
		return preferenceList;
	}

}
