package br.ufba.dcc.mestrado.computacao.repository.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.hibernate.search.exception.EmptyQueryException;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.hibernate.search.query.facet.FacetingRequest;
import org.springframework.stereotype.Repository;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectRepository;
import br.ufba.dcc.mestrado.computacao.search.SearchFacetsEnum;
import br.ufba.dcc.mestrado.computacao.search.SearchFieldsEnum;

@Repository(ProjectRepositoryImpl.BEAN_NAME)
public class ProjectRepositoryImpl 
		extends BaseRepositoryImpl<Long, OpenHubProjectEntity>
		implements ProjectRepository {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7801826722021443632L;
	
	public static final String BEAN_NAME =  "projectRepository";

	public ProjectRepositoryImpl() {
		super(OpenHubProjectEntity.class);
	}

	@Override	
	public OpenHubProjectEntity findById(Long id) {	
		
		OpenHubProjectEntity result = getEntityManager().find(getEntityClass(), id);
		
		PersistenceUnitUtil unitUtil = getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil();
		if (unitUtil.isLoaded(result)) {
			initializeProject(result);
		}
		
		return result;
		
		
		/*
		
		return result;*/
	}
	
	private void initializeProject(OpenHubProjectEntity result) {
		if (result != null) {
			
			if (result.getLicenses() != null) {
				result.getLicenses().iterator().hasNext();
			}
			
			if (result.getTags() != null) {
				result.getTags().iterator().hasNext();
			}
			
			if (result.getLinks() != null) {
				result.getLinks().iterator().hasNext();
			}
			
			if (result.getAnalysis() != null) {
				if (result.getAnalysis().getFactoids() != null) {
					result.getAnalysis().getFactoids().iterator().hasNext();
				}
				
				if (result.getAnalysis().getAnalysisLanguages() != null) {
					if (result.getAnalysis().getAnalysisLanguages().getContent() != null) {
						result.getAnalysis().getAnalysisLanguages().getContent().iterator().hasNext();
					}
				}				
			}			
		}		
	}

	@Override
	public OpenHubProjectEntity findByName(String name) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<OpenHubProjectEntity> criteriaQuery = criteriaBuilder.createQuery(getEntityClass());
		
		Root<OpenHubProjectEntity> root = criteriaQuery.from(getEntityClass());
		criteriaQuery.select(root);
		
		Predicate predicate = criteriaBuilder.equal(root.get("name"), name);
		criteriaQuery.where(predicate);
		
		TypedQuery<OpenHubProjectEntity> query = getEntityManager().createQuery(criteriaQuery);
		
		OpenHubProjectEntity projectEntity = query.getSingleResult();
		
		return projectEntity;
	}
	
	public FacetingRequest createTagFacetingRequest() {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEntityManager());
		QueryBuilder queryBuilder = fullTextEntityManager
				.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(OpenHubProjectEntity.class)
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
		
		Analyzer analyzer = fullTextEntityManager
				.getSearchFactory()
				.getAnalyzer(getEntityClass());
		
		String[] searchFields = {
			SearchFieldsEnum.projectName.fieldName(),
			SearchFieldsEnum.projectDescription.fieldName(),
			SearchFieldsEnum.tagName.fieldName()
		};
		
		MultiFieldQueryParser queryParser = 
			new MultiFieldQueryParser(searchFields, analyzer);
		
		/*org.apache.lucene.queryParser.MultiFieldQueryParser queryParser =
				new MultiFieldQueryParser(
						Version.LUCENE_36, 
						searchFields, 
						analyzer);*/
		
		FullTextQuery fullTextQuery = null;
		
		try {
		
			org.apache.lucene.search.Query luceneQuery = 
					queryParser.parse(query);
			
			fullTextQuery = fullTextEntityManager.createFullTextQuery(
					luceneQuery, 
					OpenHubProjectEntity.class);
						
			configureRelevanceSort(fullTextQuery);
			
			
		} catch (EmptyQueryException | ParseException ex) {
			
		}
		
		return fullTextQuery;
	}

	private void configureRelevanceSort(FullTextQuery fullTextQuery) {
		boolean reverse = true;
		SortField userContSortField = new SortField(SearchFieldsEnum.projectUserCount.fieldName(), SortField.Type.LONG, reverse);
		SortField ratingContSortField = new SortField(SearchFieldsEnum.projectRatingCount.fieldName(), SortField.Type.LONG, reverse);
		SortField reviewContSortField = new SortField(SearchFieldsEnum.projectReviewCount.fieldName(), SortField.Type.LONG, reverse);
		
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
	public FullTextQuery findRelatedProjects(OpenHubProjectEntity project) throws IOException {
		
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
		
		if (project.getTags() != null) {
			for (OpenHubTagEntity tag : project.getTags()) {
				TermQuery termQuery = new TermQuery(
						new Term(
								SearchFieldsEnum.tagName.fieldName(), 
								tag.getName()));
				booleanQuery.add(
						termQuery, 
						BooleanClause.Occur.SHOULD);
			}
		}
		
		booleanQuery.setMinimumNumberShouldMatch(3);
		
		FullTextQuery fullTextQuery =  fullTextEntityManager.createFullTextQuery(booleanQuery, getEntityClass());
		
		//configureRelevanceSort(fullTextQuery);
		
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
