package br.ufba.dcc.mestrado.computacao.ohloh.restful.client;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import br.com.caelum.restfulie.Response;
import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.Restfulie;
import br.com.caelum.restfulie.mediatype.XmlMediaType;
import br.ufba.dcc.mestrado.computacao.ohloh.data.account.OhLohAccountDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.account.OhLohAccountResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact.OhLohActivityFactDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.activityfact.OhLohActivityFactResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.contributorfact.OhLohContributorFactDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.contributorfact.OhLohContributorFactResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment.OhLohEnlistmentDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.enlistment.OhLohEnlistmentResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.factoid.OhLohFactoidDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.factoid.OhLohFactoidResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.kudo.OhLohKudoDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.kudo.OhLohKudoResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.sizefact.OhLohSizeFactResult;
import br.ufba.dcc.mestrado.computacao.ohloh.data.stack.OhLohStackDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.stack.OhLohStackResult;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohAccountResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohActivityFactResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohAnalysisResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohBaseResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohContributorFactResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohEnlistmentResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohFactoidResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohKudoResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohLanguageResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohProjectResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohSizeFactResponse;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.responses.OhLohStackResponse;

@Scope("singleton")
public class OhLohRestfulClient implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6670332212734792427L;

	public static Logger logger = Logger.getLogger(OhLohRestfulClient.class);
	
	/**
	 * Lista com todas as API Keys do OhLoh que eu estou utilizando
	 */
	@Autowired
	@Value("#{ '${meta.ohloh.api.key.all}'.split(',') }")	
	private List<String> apiKeyList;
	
	/**
	 * Para pegar as URL's da API do OhLoh. Não achei maneira mais limpa de fazer isso.
	 */
	@Autowired
	private Environment environment;
	
	private Integer currentApiKey;

	public OhLohRestfulClient() {
		this.currentApiKey = 0;
	}
	
	public List<String> getApiKeyList() {
		return apiKeyList;
	}
	
	public void setApiKeyList(List<String> apiKeyList) {
		this.apiKeyList = apiKeyList;
	}
	
	public Integer getCurrentApiKey() {
		return currentApiKey;
	}
	
	public void setCurrentApiKey(Integer currentApiKey) {
		this.currentApiKey = currentApiKey;
	}
	
	private <T extends OhLohBaseResponse> T processResponse(String url, OhLohBaseRequest request, RestClient restClient, Object... params) {
		T resource = null;
		
		boolean retry = true;
		String uri = "";
		
		try {
		
		while (retry) {
			retry = false;
			
			if (getApiKeyList() != null && ! getApiKeyList().isEmpty()) {
				String apiKey = getApiKeyList().get(getCurrentApiKey());
				
				if (params != null && params.length > 0) {
					Object[] args = Arrays.copyOf(params, params.length + 1);
					args[args.length -1] = apiKey;
					uri = MessageFormat.format(url, args);
				} else {
					uri = MessageFormat.format(url, apiKey);
				}
				
				uri = processRequest(uri, request);
				
				Response response = restClient.at(uri).get();
				
				resource = response.getResource();
				
				if (resource != null && ! resource.isStatusSuccess()) {
					if (OhLohBaseResponse.ERROR_API_KEY_EXCEDED.equals(resource.getError())) {
						if (getCurrentApiKey() + 1 < getApiKeyList().size()) {
							setCurrentApiKey(getCurrentApiKey() + 1);
							retry = true;
						}
					}						
				}				
			}
		};
		
		} catch (Exception ex) {
			logger.info(uri);
			throw ex;
		}
			
		
		if (resource == null) {
			throw new IllegalStateException(OhLohBaseResponse.ERROR_API_KEY_EXCEDED);
		}
		
		logger.info(uri);
		return resource;
	}
	
	private String processRequest(String uri, OhLohBaseRequest request) {
		String newURI = uri;
		
		if (request != null) {
			if (request.getQuery() != null && ! "".equals(request.getQuery())) {
				newURI += "&query=" + request.getQuery();
			}
			
			if (request.getSort() != null && ! "".equals(request.getSort())) {
				newURI += "&sort=" + request.getSort();
			}
			
			if (request.getPage() != null) {
				newURI += "&page=" + request.getPage();
			}
		}
		
		return newURI;
	}

	
	/**
	 * 
	 * @param languageId Id da conta do usuï¿½rio do OhLoh
	 * @return
	 */
	public OhLohLanguageDTO getLanguageById(String languageId, OhLohBaseRequest request) {
		
		OhLohLanguageDTO language = null;
		
		String url = environment.getProperty("meta.ohloh.api.language");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohLanguageResponse.class,
				OhLohLanguageResult.class,
				OhLohLanguageDTO.class));
		
		OhLohLanguageResponse resource = this.<OhLohLanguageResponse>processResponse(url, request, restfulie, languageId);
		if (OhLohBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OhLohLanguageDTO> ohLohLanguageDTOs = resource.getResult().getLanguages();
			if (ohLohLanguageDTOs != null && ! ohLohLanguageDTOs.isEmpty())
				language =  ohLohLanguageDTOs.get(0);
		}
		
		return language;
	}
	
	public OhLohLanguageResponse getAllLanguages(OhLohBaseRequest request) {
		OhLohLanguageResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.language.all");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohLanguageResponse.class,
				OhLohLanguageResult.class,
				OhLohLanguageDTO.class));
		
		resource = this.<OhLohLanguageResponse>processResponse(url, request, restfulie);
			
		return resource;
	}
	
	/**
	 * 
	 * @return
	 */
	public OhLohAccountResponse getAllAccounts(OhLohBaseRequest request) {
		
		OhLohAccountResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.account.all");
		
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohAccountResponse.class,
				OhLohAccountResult.class,
				OhLohAccountDTO.class));
		
		resource = this.<OhLohAccountResponse>processResponse(url, request, restfulie);
			
		
		return resource;
	}
	
	
	/**
	 * 
	 * @param accountId Id da conta do usuï¿½rio do OhLoh
	 * @return
	 */
	public OhLohAccountDTO getAccountById(String accountId, OhLohBaseRequest request) {
		
		OhLohAccountDTO account = null;
		
		String url = environment.getProperty("meta.ohloh.api.account");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohAccountResponse.class,
				OhLohAccountResult.class,
				OhLohAccountDTO.class));
		
		OhLohAccountResponse resource = this.<OhLohAccountResponse>processResponse(url, request, restfulie, accountId);
		if (OhLohBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OhLohAccountDTO> ohLohAccountDTOs = resource.getResult().getAccounts();
			if (ohLohAccountDTOs != null && ! ohLohAccountDTOs.isEmpty())
				account =  ohLohAccountDTOs.get(0);
		}
		
		return account;
	}

	/**
	 * 
	 * @return
	 */
	public OhLohProjectResponse getAllProjects(OhLohBaseRequest request) {
		
		OhLohProjectResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.project.all");
		
		RestClient restfulie = Restfulie.custom();
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohProjectResponse.class,
				OhLohProjectResult.class,
				OhLohProjectDTO.class));
		
		resource = this.<OhLohProjectResponse>processResponse(url, request, restfulie);
			
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId ID de um projeto cadastrado no OhLoh
	 * @return
	 */
	public OhLohProjectDTO getProject(String projectId, OhLohBaseRequest request) {
		OhLohProjectDTO project = null;
		
		String url = environment.getProperty("meta.ohloh.api.project");
		
		
		RestClient restfulie = Restfulie.custom();
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohProjectResponse.class,
				OhLohProjectResult.class,
				OhLohProjectDTO.class));
		
		OhLohProjectResponse resource = this.<OhLohProjectResponse>processResponse(url, request, restfulie, projectId);
		
		if (OhLohBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OhLohProjectDTO> ohLohProjectDTOs = resource.getResult().getProjects();
			if (ohLohProjectDTOs != null && ! ohLohProjectDTOs.isEmpty()) {
				project = ohLohProjectDTOs.get(0);
			}
		}
		
		return project;
	}
	
	/**
	 * 
	 * @param accountId
	 * @param stackId
	 * @return
	 */
	public OhLohStackDTO getSingleAccountStack(String accountId, String stackId, OhLohBaseRequest request) {
		
		OhLohStackDTO stack = null;
		
		String url = environment.getProperty("meta.ohloh.api.stack.account");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohStackResponse.class,
				OhLohStackResult.class,
				OhLohStackDTO.class));
		
		
		OhLohStackResponse resource = this.<OhLohStackResponse>processResponse(url, request, restfulie);
		if (OhLohBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OhLohStackDTO> ohLohStackDTOs = resource.getResult().getStacks();
			if (ohLohStackDTOs != null && ! ohLohStackDTOs.isEmpty()) {
				stack = ohLohStackDTOs.get(0);
			}
		}
			
		return stack;
	}
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public OhLohStackDTO getDefaultAccountStack(String accountId, OhLohBaseRequest request) {
		OhLohStackDTO stack = null;
		
		String url = environment.getProperty("meta.ohloh.api.stack.account.default");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohStackResponse.class,
				OhLohStackResult.class,
				OhLohStackDTO.class));
		
		OhLohStackResponse resource = this.<OhLohStackResponse>processResponse(url, request, restfulie, accountId);
		if (OhLohBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OhLohStackDTO> ohLohStackDTOs = resource.getResult().getStacks();
			if (ohLohStackDTOs != null && ! ohLohStackDTOs.isEmpty()) {
				stack = ohLohStackDTOs.get(0);
			}
		}
		
		return stack;
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public OhLohStackResponse getProjectStacks(String projectId, OhLohBaseRequest request) {
		
		OhLohStackResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.stack.project");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohStackResponse.class,
				OhLohStackResult.class,
				OhLohStackDTO.class));
		
		resource = this.<OhLohStackResponse>processResponse(url, request, restfulie, projectId);
						
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public OhLohFactoidResponse getAllFactoidsByProject(String projectId, OhLohBaseRequest request) {
		OhLohFactoidResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.factoid.project");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohFactoidResponse.class,
				OhLohFactoidResult.class,
				OhLohFactoidDTO.class));
		
		resource = this.<OhLohFactoidResponse>processResponse(url, request, restfulie);
			
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @param factoidId
	 * @return
	 */
	public OhLohFactoidResponse getSingleFactoidByProject(String projectId, String factoidId, OhLohBaseRequest request) {
		OhLohFactoidResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.factoid");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohFactoidResponse.class,
				OhLohFactoidResult.class,
				OhLohFactoidDTO.class));
		
		resource = this.<OhLohFactoidResponse>processResponse(url, request, restfulie);
			
		return resource;
	}
	
	public OhLohSizeFactResponse getLatestSizeFackByProject(String projectId) {
		return getAnalysisSizeFactByProject(projectId, "latest");
	}
	
	/**
	 * 
	 * @param projectId
	 * @param analysisId
	 * @param request
	 * @return
	 */
	public OhLohSizeFactResponse getAnalysisSizeFactByProject(String projectId, String analysisId) {
		OhLohSizeFactResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.size_facts");
		
		RestClient restfulie = Restfulie.custom();
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohSizeFactResponse.class,
				OhLohSizeFactResult.class,
				OhLohSizeFactDTO.class));
		
		resource = this.<OhLohSizeFactResponse>processResponse(url, null, restfulie, projectId, analysisId);
		
		return resource;
	}
	
	/**
	 * 
	 * @param analysisId
	 * @param request
	 * @return
	 */
	public OhLohAnalysisDTO getAnalysisById(String projectId, String analysisId, OhLohBaseRequest request) {
		
		OhLohAnalysisDTO analysis = null;
		
		String url = environment.getProperty("meta.ohloh.api.analysis");

		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohAnalysisResponse.class,
				OhLohAnalysisResult.class,
				OhLohAnalysisDTO.class));
		
		OhLohAnalysisResponse resource = this.<OhLohAnalysisResponse>processResponse(url, request, restfulie, projectId, analysisId);
		if (resource != null && OhLohBaseResponse.SUCCESS.equals(resource.getStatus())) {
			analysis = resource.getResult().getAnalysis();
		}

		return analysis;
	}
	
	/**
	 * 
	 * @param projectId
	 * @param request
	 * @return
	 */
	public OhLohAnalysisDTO getLatestAnalysis(String projectId, OhLohBaseRequest request) {
		return getAnalysisById(projectId, "latest", request);
	}
	
	/**
	 * 
	 * @return
	 */
	public OhLohContributorFactDTO getProjectContributorFactById(String projectId, String contributorId, OhLohBaseRequest request) {
		OhLohContributorFactDTO contributorFact = null;
		
		String url = environment.getProperty("meta.ohloh.api.contributor_facts");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohContributorFactResponse.class,
				OhLohContributorFactResult.class,
				OhLohContributorFactDTO.class));

		OhLohContributorFactResponse resource = this.<OhLohContributorFactResponse>processResponse(url, request, restfulie, projectId, contributorId);
		
		if (resource != null && OhLohBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OhLohContributorFactDTO> ohLohContributorFactDTOs = resource.getResult().getContributorFacts();
			
			if (ohLohContributorFactDTOs != null && ! ohLohContributorFactDTOs.isEmpty()) {
				contributorFact = ohLohContributorFactDTOs.get(0);
			}
		}
		
		return contributorFact;
	}
	
	/**
	 * 
	 * @param projectId
	 * @param request
	 * @return
	 */
	public OhLohContributorFactResponse getAllProjectContributorFacts(String projectId, OhLohBaseRequest request) {
		OhLohContributorFactResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.contributor_facts.all");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohContributorFactResponse.class,
				OhLohContributorFactResult.class,
				OhLohContributorFactDTO.class));
		
		
		resource = this.<OhLohContributorFactResponse>processResponse(url, request, restfulie);
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @param analysisId
	 * @return
	 */
	public OhLohActivityFactResponse getProjectActivityFactByAnalysisId(String projectId, String analysisId) {
		OhLohActivityFactResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.activity_facts");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohActivityFactResponse.class,
				OhLohActivityFactResult.class,
				OhLohActivityFactDTO.class));
		
		
		resource = this.<OhLohActivityFactResponse>processResponse(url, null, restfulie, projectId, analysisId);
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public OhLohActivityFactResponse getLatestProjectActivityFacts(String projectId) {
		return getProjectActivityFactByAnalysisId(projectId, "latest");
	}
	
	/**
	 * 
	 * @param projectId
	 * @param request
	 * @return
	 */
	public OhLohEnlistmentResponse getAllProjectEnlistments(String projectId, OhLohBaseRequest request) {
		OhLohEnlistmentResponse resource = null;
			
		String url = environment.getProperty("meta.ohloh.api.enlistment.all");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohEnlistmentResponse.class,
				OhLohEnlistmentResult.class,
				OhLohEnlistmentDTO.class));
		
		
		resource = this.<OhLohEnlistmentResponse>processResponse(url, request, restfulie, projectId);
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @param enlistmentId
	 * @return
	 */
	public OhLohEnlistmentDTO getProjectEnlistmentById(String projectId, String enlistmentId) {
		OhLohEnlistmentDTO enlistment = null;
		
		String url = environment.getProperty("meta.ohloh.api.enlistment");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohEnlistmentResponse.class,
				OhLohEnlistmentResult.class,
				OhLohEnlistmentDTO.class));

		OhLohEnlistmentResponse resource = this.<OhLohEnlistmentResponse>processResponse(url, null, restfulie, projectId, enlistmentId);
		
		if (resource != null && OhLohBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OhLohEnlistmentDTO> ohLohEnlistmentDTOs = resource.getResult().getEnlistments();
			
			if (ohLohEnlistmentDTOs != null && ! ohLohEnlistmentDTOs.isEmpty()) {
				enlistment = ohLohEnlistmentDTOs.get(0);
			}
		}
		
		return enlistment;
	}
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public OhLohKudoResponse getAllKudoReceivedByAccountId(String accountId) {
		OhLohKudoResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.kudos.received");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohKudoResponse.class,
				OhLohKudoResult.class,
				OhLohKudoDTO.class));
		

		resource = this.<OhLohKudoResponse>processResponse(url, null, restfulie);
		
		return resource;
	}
	
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public OhLohKudoResponse getAllKudoSentByAccountId(String accountId) {
		OhLohKudoResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.kudos.sent");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OhLohKudoResponse.class,
				OhLohKudoResult.class,
				OhLohKudoDTO.class));
		

		resource = this.<OhLohKudoResponse>processResponse(url, null, restfulie);
		
		return resource;
	}
}
