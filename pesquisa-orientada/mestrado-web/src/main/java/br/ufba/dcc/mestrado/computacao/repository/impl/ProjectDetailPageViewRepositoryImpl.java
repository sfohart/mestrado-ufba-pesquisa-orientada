package br.ufba.dcc.mestrado.computacao.repository.impl;

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
import javax.persistence.criteria.Root;

import org.hibernate.ejb.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
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
			.orderBy(new OrderImpl(pageViewCount, false));
		
		
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

}
