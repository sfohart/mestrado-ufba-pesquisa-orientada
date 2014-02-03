package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.ProjectPreferenceInfo;
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
	public Long countAllLast() {
		return countAllLastByProject(null);
	}
	
	@Override
	public Long countAllLastByProject(Long projectId) {
		return countAllLastByProject(projectId, false);
	}
	
	@Override
	public Long countAllLastReviewsByProject(Long projectId) {
		Long result = 0L;
		
		if (projectId != null) {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<PreferenceReviewEntity> root = criteriaQuery.from(PreferenceReviewEntity.class);
			
			Join<PreferenceReviewEntity, PreferenceEntity> preferenceJoin = root.join("preference");
			
			criteriaQuery = criteriaQuery.select(criteriaBuilder.count(root));
			
			
			List<Predicate> predicateList = new ArrayList<>();
			
			Predicate projectPredicate = criteriaBuilder.equal(preferenceJoin.get("projectId"), projectId);
			predicateList.add(projectPredicate);
			
			/*
			 * Criando subquery para trazer os últimos registros de cada usuario/projeto
			 */
			Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
			
			Root<PreferenceEntity> preferenceRoot = subquery.from(PreferenceEntity.class);
			Expression<Timestamp> greatestRegisteredAt = preferenceRoot.get("registeredAt");
			
			subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
			subquery.where(
					criteriaBuilder.equal(preferenceJoin.get("projectId"), preferenceRoot.get("projectId")),
					criteriaBuilder.equal(preferenceJoin.get("userId"), preferenceRoot.get("userId"))
				);
			
			Predicate registeredAtPredicate = criteriaBuilder.equal(preferenceJoin.get("registeredAt"), subquery);
			predicateList.add(registeredAtPredicate);
			
			//aplicando os filtros
			criteriaQuery = criteriaQuery.where(predicateList.toArray(new Predicate[0]));
			
			TypedQuery<Long> typedQuery = getEntityManager().createQuery(criteriaQuery);
			
			result = typedQuery.getSingleResult();
		}
		
		return result;
	}
	
	
	@Override
	public Long countAllLastReviewsByUser(Long userId) {
		Long result = 0L;
		
		if (userId != null) {
			
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			
			CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
			Root<PreferenceReviewEntity> root = criteriaQuery.from(PreferenceReviewEntity.class);
			
			Join<PreferenceReviewEntity, PreferenceEntity> preferenceJoin = root.join("preference");
			
			criteriaQuery = criteriaQuery.select(criteriaBuilder.count(root));
			
			
			List<Predicate> predicateList = new ArrayList<>();
			
			Predicate projectPredicate = criteriaBuilder.equal(preferenceJoin.get("userId"), userId);
			predicateList.add(projectPredicate);
			
			/*
			 * Criando subquery para trazer os últimos registros de cada usuario/projeto
			 */
			Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
			
			Root<PreferenceEntity> preferenceRoot = subquery.from(PreferenceEntity.class);
			Expression<Timestamp> greatestRegisteredAt = preferenceRoot.get("registeredAt");
			
			subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
			subquery.where(
					criteriaBuilder.equal(preferenceJoin.get("projectId"), preferenceRoot.get("projectId")),
					criteriaBuilder.equal(preferenceJoin.get("userId"), preferenceRoot.get("userId"))
				);
			
			Predicate registeredAtPredicate = criteriaBuilder.equal(preferenceJoin.get("registeredAt"), subquery);
			predicateList.add(registeredAtPredicate);
			
			//aplicando os filtros
			criteriaQuery = criteriaQuery.where(predicateList.toArray(new Predicate[0]));
			
			
			TypedQuery<Long> typedQuery = getEntityManager().createQuery(criteriaQuery);
			
			result = typedQuery.getSingleResult();
		}
		
		return result;
	}
	
	private Long countAllLastByProject(
			Long projectId,
			boolean onlyWithReviews) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> p1 = criteriaQuery.from(getEntityClass());
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(p1));
				
		List<Predicate> predicateList = new ArrayList<>();
		
		if (projectId != null) {
			//filtrando por projeto
			Predicate namePredicate = criteriaBuilder.equal(p1.get("projectId"), projectId);
			predicateList.add(namePredicate);
		}
		
		if (onlyWithReviews) {
			Predicate preferenceReviewPredicate = criteriaBuilder.isNotNull(p1.get("preferenceReview"));
			predicateList.add(preferenceReviewPredicate);
		}
		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
		Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		subquery.where(
				criteriaBuilder.equal(p1.get("projectId"), p2.get("projectId")),
				criteriaBuilder.equal(p1.get("userId"), p2.get("userId"))
			);
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(p1.get("registeredAt"), subquery);
		predicateList.add(registeredAtPredicate);
		
		if (! predicateList.isEmpty()) {
			select.where(predicateList.toArray(new Predicate[0]));
		}
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	@Override
	public Long countAllByProjectAndUser(
			Long projectId,
			Long userId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		
		Root<PreferenceEntity> root = criteriaQuery.from(getEntityClass());
		
		CriteriaQuery<Long> select = criteriaQuery.select(criteriaBuilder.count(root));
		
		List<Predicate> predicateList = new ArrayList<>();
		
		if (userId != null) {
			Predicate userPredicate = criteriaBuilder.equal(root.get("userId"), userId);
			predicateList.add(userPredicate);
		}
		
		if (projectId != null) {
			Predicate projectPredicate = criteriaBuilder.equal(root.get("projectId"), projectId);
			predicateList.add(projectPredicate);
		}
				
		select.where(predicateList.toArray(new Predicate[0]));
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	public Double averagePreferenceByProject(Long projectId) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
		
		Root<PreferenceEntity> p1 = criteriaQuery.from(getEntityClass());
		Path<Double> valuePath = p1.get("value");
		
		CriteriaQuery<Double> select = criteriaQuery.select(criteriaBuilder.avg( valuePath ));
		
		Predicate namePredicate = criteriaBuilder.equal(p1.get("projectId"), projectId);
		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
		Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		subquery.where(
				criteriaBuilder.equal(p1.get("projectId"), p2.get("projectId")),
				criteriaBuilder.equal(p1.get("userId"), p2.get("userId"))
			);
		
		Predicate registeredAtPredicate = criteriaBuilder.equal(p1.get("registeredAt"), subquery);
		
		select.where(namePredicate, registeredAtPredicate);
		
		return getEntityManager().createQuery(criteriaQuery).getSingleResult();
	}
	
	
	@Override
	public List<PreferenceEntity> findAllLastByProject(Long projectId) {
		return findAllLastByProject(projectId, null, null);
	}
	
	@Override
	public List<PreferenceEntity> findAllLastByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset) {
		return findAllLastByProject(projectId, startAt, offset, false);
	}
	
	private List<PreferenceEntity> findAllLastByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset,
			boolean onlyWithReviews) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<PreferenceEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<PreferenceEntity> p1 = criteriaQuery.from(getEntityClass());
		CriteriaQuery<PreferenceEntity> select = criteriaQuery.select(p1);
		
		Fetch<PreferenceEntity, PreferenceReviewEntity> preferenceReviewFetch;
		if (onlyWithReviews) {
			preferenceReviewFetch = p1.fetch("preferenceReview", JoinType.INNER);
		} else {
			preferenceReviewFetch = p1.fetch("preferenceReview", JoinType.LEFT);
		}
		
		preferenceReviewFetch.fetch("uselessList", JoinType.LEFT);
		preferenceReviewFetch.fetch("usefulList", JoinType.LEFT);
		
		p1.fetch("preferenceEntryList", JoinType.LEFT);
		p1.fetch("user", JoinType.LEFT);

		List<Predicate> predicateList = new ArrayList<>();
		
		Predicate projectPredicate = criteriaBuilder.equal(p1.get("projectId"), projectId);
		predicateList.add(projectPredicate);
		
		/*
		 * Criando subquery para trazer os últimos registros de cada usuario/projeto
		 */
		Subquery<Timestamp> subquery = criteriaQuery.subquery(Timestamp.class);
		
		Root<PreferenceEntity> p2 = subquery.from(getEntityClass());
		Expression<Timestamp> greatestRegisteredAt = p2.get("registeredAt");
		
		subquery.select(criteriaBuilder.greatest((greatestRegisteredAt)));
		subquery.where(
				criteriaBuilder.equal(p1.get("projectId"), p2.get("projectId")),
				criteriaBuilder.equal(p1.get("userId"), p2.get("userId"))
			);
		
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

	@Override
	public List<PreferenceEntity> findAllLastReviewsByProject(Long projectId) {		
		return findAllLastReviewsByProject(projectId, null, null);
	}
	
	public List<PreferenceEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset) {
		return findAllLastReviewsByProject(projectId, null, null, true, true);
	}
	
	@Override
	public List<PreferenceEntity> findAllLastReviewsByProject(
			Long projectId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		
		List<PreferenceEntity> preferenceList = null;
		
		if (projectId != null) {
			String query = 
						"SELECT DISTINCT p1 FROM PreferenceEntity p1 "
					+ 	"	INNER JOIN FETCH p1.preferenceReview review "
					+ 	"	LEFT JOIN review.uselessList "
					+	"	LEFT JOIN review.usefulList "
					+	"	LEFT JOIN FETCH p1.preferenceEntryList entry "
					+	"	LEFT JOIN FETCH p1.user user "
					+	"WHERE "
					+	"	p1.projectId = :projectId AND "
					+	"	p1.registeredAt = ( "
					+	"		SELECT MAX(p2.registeredAt) FROM PreferenceEntity p2 "
					+	"		WHERE 	p2.projectId = p1.projectId"
					+	"				AND p2.userId = p1.userId"
					+	"	) "
					;
			
			String orderBy = "";
			if (orderByRegisteredAt && orderByReviewRanking) {
				orderBy += 
						"ORDER BY p1.registeredAt DESC, review.reviewRanking DESC";
			} else if (orderByRegisteredAt) {
				orderBy += 
						"ORDER BY p1.registeredAt DESC";
			} else if (orderByReviewRanking) {
				orderBy += 
						"ORDER BY review.reviewRanking DESC";
			}
			
			if (! StringUtils.isEmpty(orderBy)) {
				query += " " + orderBy;
			}
			
			TypedQuery<PreferenceEntity> typedQuery = getEntityManager().createQuery(query, PreferenceEntity.class);
			typedQuery.setParameter("projectId", projectId);
			
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
	
	public List<PreferenceEntity> findAllLastReviewsByUser(
			Long userId, 
			Integer startAt, 
			Integer offset,
			boolean orderByRegisteredAt,
			boolean orderByReviewRanking) {
		
		List<PreferenceEntity> preferenceList = null;
		
		if (userId != null) {
			String query = 
						"SELECT DISTINCT p1 FROM PreferenceEntity p1 "
					+ 	"	INNER JOIN FETCH p1.preferenceReview review "
					+ 	"	LEFT JOIN review.uselessList "
					+	"	LEFT JOIN review.usefulList "
					+	"	LEFT JOIN FETCH p1.preferenceEntryList entry "
					+	"	LEFT JOIN FETCH p1.user user "
					+	"WHERE "
					+	"	p1.userId = :userId AND "
					+	"	p1.registeredAt = ( "
					+	"		SELECT MAX(p2.registeredAt) FROM PreferenceEntity p2 "
					+	"		WHERE 	p2.projectId = p1.projectId"
					+	"				AND p2.userId = p1.userId"
					+	"	) "
					;
			
			String orderBy = "";
			if (orderByRegisteredAt && orderByReviewRanking) {
				orderBy += 
						"ORDER BY p1.registeredAt DESC, review.reviewRanking DESC";
			} else if (orderByRegisteredAt) {
				orderBy += 
						"ORDER BY p1.registeredAt DESC";
			} else if (orderByReviewRanking) {
				orderBy += 
						"ORDER BY review.reviewRanking DESC";
			}
			
			if (! StringUtils.isEmpty(orderBy)) {
				query += " " + orderBy;
			}
			
			TypedQuery<PreferenceEntity> typedQuery = getEntityManager().createQuery(query, PreferenceEntity.class);
			typedQuery.setParameter("userId", userId);
			
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
	
	public List<PreferenceEntity> findAllByProjectAndUser(
			Long projectId,
			Long userId) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<PreferenceEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<PreferenceEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<PreferenceEntity> select = criteriaQuery.select(root);
		
		root.fetch("preferenceReview", JoinType.LEFT);
		root.fetch("preferenceEntryList", JoinType.LEFT);

		List<Predicate> predicateList = new ArrayList<>();
		
		if (userId != null) {
			Predicate userPredicate = criteriaBuilder.equal(root.get("userId"), userId);
			predicateList.add(userPredicate);
		}
		
		if (projectId != null) {
			Predicate projectPredicate = criteriaBuilder.equal(root.get("projectId"), projectId);
			predicateList.add(projectPredicate);
		}
		
		select.where(predicateList.toArray(new Predicate[0]));

		TypedQuery<PreferenceEntity> query = getEntityManager().createQuery(criteriaQuery);

		List<PreferenceEntity> preferenceList = query.getResultList();
		
		return preferenceList;		
	}
	
	@Override
	public PreferenceEntity findLastByProjectAndUser(Long projectId, Long userId) {
		
		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<PreferenceEntity> criteriaQuery = criteriaBuilder
				.createQuery(getEntityClass());

		Root<PreferenceEntity> root = criteriaQuery.from(getEntityClass());
		CriteriaQuery<PreferenceEntity> select = criteriaQuery.select(root);
		
		root.fetch("preferenceReview", JoinType.LEFT);
		root.fetch("preferenceEntryList", JoinType.LEFT);

		List<Predicate> predicateList = new ArrayList<>();
		
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
		
		select.where(predicateList.toArray(new Predicate[0]));

		TypedQuery<PreferenceEntity> query = getEntityManager().createQuery(criteriaQuery);

		PreferenceEntity preference = null;
		
		try {
			preference = query.getSingleResult();
		} catch (NoResultException ex) {
		} catch (NonUniqueResultException ex) {
		}
		
		return preference;		
	}

	@Override
	public Long countAllProjectPreferenceInfo() {
		return null;
	}

	@Override
	public List<ProjectPreferenceInfo> findAllProjectPreferenceInfo() {
		return findAllProjectPreferenceInfo(null, null);
	}

	@Override
	public List<ProjectPreferenceInfo> findAllProjectPreferenceInfo(
			Integer startAt, 
			Integer offset) {
		
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tuple> tupleQuery = criteriaBuilder.createTupleQuery();
		
		Root<PreferenceEntity> root = tupleQuery.from(PreferenceEntity.class);
		Join<PreferenceEntity, OhLohProjectEntity> projectJoin = root.join("project", JoinType.INNER);
		Join<PreferenceEntity, PreferenceReviewEntity> reviewJoin = root.join("preferenceReview", JoinType.INNER);
		
		Expression<Long> reviewCount = criteriaBuilder.count(reviewJoin);
		
		tupleQuery
			.multiselect(projectJoin, reviewCount)
			.groupBy(
					projectJoin.get("id")
				)
			.having(criteriaBuilder.gt(reviewCount, 0))
			.orderBy(criteriaBuilder.desc(reviewCount));
		
		
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
		tupleQuery.where(registeredAtPredicate);
		
		TypedQuery<Tuple> tupleTypedQuery = getEntityManager().createQuery(tupleQuery);
		
		if (offset != null) {
			tupleTypedQuery.setMaxResults(offset);
		}
		
		if (startAt != null) {
			tupleTypedQuery.setFirstResult(startAt);
		}
		
		
		
		
		List<Tuple> tupleList = tupleTypedQuery.getResultList();
				
		List<ProjectPreferenceInfo> resultList = null;
		
		if (tupleList != null && ! tupleList.isEmpty()) {
			resultList = new ArrayList<>();
			
			for (Tuple tuple : tupleList) {
				assert tuple.get(0) == tuple.get(projectJoin);
				assert tuple.get(1) == tuple.get(reviewCount);
				
				ProjectPreferenceInfo info = new ProjectPreferenceInfo();
				
				OhLohProjectEntity projectInfo = tuple.get(projectJoin);
				Long reviewCountInfo = tuple.get(reviewCount);
				
				info.setProject(projectInfo);
				info.setReviewsCount(reviewCountInfo);
				
				
				resultList.add(info);
			}
		}
		
		return resultList;
	}

	
}
