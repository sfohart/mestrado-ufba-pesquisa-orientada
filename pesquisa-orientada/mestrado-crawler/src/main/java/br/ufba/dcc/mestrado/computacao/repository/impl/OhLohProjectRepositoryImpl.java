package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.errors.EmptyQueryException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.hibernate.search.query.facet.FacetingRequest;
import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;
import br.ufba.dcc.mestrado.computacao.search.SearchFacetsEnum;
import br.ufba.dcc.mestrado.computacao.search.SearchFieldsEnum;

@Repository(OhLohProjectRepositoryImpl.BEAN_NAME)
public class OhLohProjectRepositoryImpl 
		extends BaseRepositoryImpl<Long, OhLohProjectEntity>
		implements OhLohProjectRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "ohLohProjectRepository";

	public OhLohProjectRepositoryImpl() {
		super(OhLohProjectEntity.class);
	}

	@Override	
	public OhLohProjectEntity findById(Long id) {		
		OhLohProjectEntity result = super.findById(id);
		
		if (result != null) {
			
			if (result.getOhLohLicenses() != null) {
				result.getOhLohLicenses().size();
			}
			
			if (result.getOhLohTags() != null) {
				result.getOhLohTags().size();
			}
			
			if (result.getOhLohLinks() != null) {
				result.getOhLohLinks().size();
			}
			
			if (result.getOhLohAnalysis() != null) {
				if (result.getOhLohAnalysis().getOhLohFactoids() != null) {
					result.getOhLohAnalysis().getOhLohFactoids().size();
				}
				
				if (result.getOhLohAnalysis().getOhLohAnalysisLanguages() != null) {
					result.getOhLohAnalysis().getOhLohAnalysisLanguages().getContent();
				}
				
			}
			
		}
		
		return result;
	}
	
	@Override
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
	
	public FullTextQuery findAllByFullTextQuery(String query) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEntityManager());
		QueryBuilder queryBuilder = fullTextEntityManager
				.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(OhLohProjectEntity.class)
				.get();
		
		FullTextQuery fullTextQuery = null;
		
		try {
		
			org.apache.lucene.search.Query luceneQuery = queryBuilder
					.keyword()
					.onField(SearchFieldsEnum.projectName.fieldName())
//						.boostedTo(SearchFieldsEnum.projectName.boost())
					.andField(SearchFieldsEnum.projectDescription.fieldName())
//						.boostedTo(SearchFieldsEnum.projectDescription.boost())
					.andField(SearchFieldsEnum.tagName.fieldName())
//						.boostedTo(SearchFieldsEnum.tagName.boost())
					.matching(query)
					.createQuery();
			
			fullTextQuery = fullTextEntityManager.createFullTextQuery(
					luceneQuery, 
					OhLohProjectEntity.class);
			
			
						
			configureRelevanceSort(fullTextQuery);
			
			
		} catch (EmptyQueryException ex) {
			
		}
		
		return fullTextQuery;
	}

	private void configureRelevanceSort(FullTextQuery fullTextQuery) {
		boolean reverse = true;
		SortField userContSortField = new SortField(SearchFieldsEnum.projectUserCount.fieldName(), SortField.LONG, reverse);
		SortField ratingContSortField = new SortField(SearchFieldsEnum.projectRatingCount.fieldName(), SortField.LONG, reverse);
		SortField reviewContSortField = new SortField(SearchFieldsEnum.projectReviewCount.fieldName(), SortField.LONG, reverse);
		
		List<SortField> sortFieldList = new LinkedList<>();
		sortFieldList.addAll(Arrays.asList(Sort.RELEVANCE.getSort()));
		sortFieldList.add(userContSortField);
		sortFieldList.add(reviewContSortField);
		sortFieldList.add(ratingContSortField);
		
		Sort sort = new Sort(userContSortField);
		fullTextQuery.setSort(sort);
	}

	/**
	 *
	 * Encontra projetos que possuam as mesmas tags que o projeto  em questão
	 * @param project Projeto para o qual se deseja encontrar projetos semelhantes
	 * @returns Um objeto FullTextQuery contendo a lógica necessária para se encontrar projetos semelhantes
	 */
	@Override
	public FullTextQuery findRelatedProjects(OhLohProjectEntity project) throws IOException {
		
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEntityManager());
		BooleanQuery booleanQuery = new BooleanQuery();
		
		booleanQuery.add(
				new TermQuery(
						new Term(
								SearchFieldsEnum.projectName.fieldName(), 
								project.getName())), 
				BooleanClause.Occur.MUST_NOT);
		
		booleanQuery.add(
				new TermQuery(
						new Term(
								"id", 
								project.getId().toString())), 
				BooleanClause.Occur.MUST_NOT);
		
		if (project.getOhLohTags() != null) {
			for (OhLohTagEntity tag : project.getOhLohTags()) {
				booleanQuery.add(
						new TermQuery(
								new Term(
										SearchFieldsEnum.tagName.fieldName(), 
										tag.getName())), 
						BooleanClause.Occur.SHOULD);
			}
		}
		
		FullTextQuery fullTextQuery =  fullTextEntityManager.createFullTextQuery(booleanQuery, getEntityClass());
		
		configureRelevanceSort(fullTextQuery);
		
		return fullTextQuery;
	}

	@Override
	public IndexReader getIndexReader() {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEntityManager());
		
		IndexReader indexReader = fullTextEntityManager
				.getSearchFactory()
				.getIndexReaderAccessor()
				.open(getEntityClass());
		
		return indexReader;
	}
	
}
