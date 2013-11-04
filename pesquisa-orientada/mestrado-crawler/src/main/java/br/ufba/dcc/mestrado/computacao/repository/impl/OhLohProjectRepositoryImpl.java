package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.hibernate.search.query.facet.FacetingRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;
import br.ufba.dcc.mestrado.computacao.search.SearchFacetsEnum;
import br.ufba.dcc.mestrado.computacao.search.SearchFieldsEnum;

@Repository
public class OhLohProjectRepositoryImpl extends BaseRepositoryImpl<Long, OhLohProjectEntity>
		implements OhLohProjectRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;

	public OhLohProjectRepositoryImpl() {
		super(OhLohProjectEntity.class);
	}

	@Override
	@Transactional(readOnly = true)
	public OhLohProjectEntity findById(Long id) {		
		OhLohProjectEntity result = super.findById(id);
		
		if (result != null) {
			result.getOhLohLicenses();
			result.getOhLohTags();
		}
		
		return result;
	}
	
	@Override
	@Transactional(readOnly = true)
	public OhLohProjectEntity findByName(String name) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<OhLohProjectEntity> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		
		Root<OhLohProjectEntity> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(root);
		
		Predicate predicate = criteriaBuilder.equal(root.get("name"), name);
		criteriaQuery.where(predicate);
		
		TypedQuery<OhLohProjectEntity> query = getEntityManager().createQuery(criteriaQuery);
		
		OhLohProjectEntity projectEntity = query.getSingleResult();
		
		return projectEntity;
	}
	
	public FacetingRequest createTagFacetingRequest() {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEntityManager());
		QueryBuilder queryBuilder = fullTextEntityManager
				.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(OhLohProjectEntity.class)
				.get();
		
		FacetingRequest facetingRequest = queryBuilder
				.facet()
				.name("tagFacetRequest")
				.onField(SearchFacetsEnum.tagFacet.facetName())
				.discrete()
				.orderedBy(FacetSortOrder.COUNT_ASC)
				.includeZeroCounts(false)
				.createFacetingRequest();
		
		return facetingRequest;
	}
	
	@Transactional(readOnly = true)
	public FullTextQuery findAllByFullTextQuery(String query) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEntityManager());
		QueryBuilder queryBuilder = fullTextEntityManager
				.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(OhLohProjectEntity.class)
				.get();
		
		FacetingRequest facetingRequest = queryBuilder
				.facet()
				.name("tagFacetRequest")
				.onField(SearchFacetsEnum.tagFacet.facetName())
				.discrete()
				.orderedBy(FacetSortOrder.COUNT_ASC)
				.includeZeroCounts(false)
				.createFacetingRequest();
		
		org.apache.lucene.search.Query luceneQuery = queryBuilder
				.keyword()
				.onField(SearchFieldsEnum.projectName.fieldName())
					.boostedTo(SearchFieldsEnum.projectName.boost())
				.andField(SearchFieldsEnum.projectDescription.fieldName())
					.boostedTo(SearchFieldsEnum.projectDescription.boost())
					.andField(SearchFieldsEnum.tagName.fieldName())
					.boostedTo(SearchFieldsEnum.tagName.boost())
				.andField(SearchFieldsEnum.projectMainLanguageName.fieldName())
					.boostedTo(SearchFieldsEnum.projectMainLanguageName.boost())
				.andField(SearchFieldsEnum.languageName.fieldName())
					.boostedTo(SearchFieldsEnum.languageName.boost())
				.andField(SearchFieldsEnum.languageNiceName.fieldName())
					.boostedTo(SearchFieldsEnum.languageNiceName.boost())
				.andField(SearchFieldsEnum.languageCategory.fieldName())
					.boostedTo(SearchFieldsEnum.languageCategory.boost())
				.matching(query)
				.createQuery();
		
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(
				luceneQuery, 
				OhLohProjectEntity.class);
		
		
		
		return fullTextQuery;
	}
	
}
