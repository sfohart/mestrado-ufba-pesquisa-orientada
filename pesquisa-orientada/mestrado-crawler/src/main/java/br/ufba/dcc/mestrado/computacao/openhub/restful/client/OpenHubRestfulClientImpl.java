package br.ufba.dcc.mestrado.computacao.openhub.restful.client;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import br.com.caelum.restfulie.Response;
import br.com.caelum.restfulie.RestClient;
import br.com.caelum.restfulie.Restfulie;
import br.com.caelum.restfulie.mediatype.XmlMediaType;
import br.ufba.dcc.mestrado.computacao.openhub.data.account.OpenHubAccountDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.account.OpenHubAccountResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.activityfact.OpenHubActivityFactDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.activityfact.OpenHubActivityFactResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.analysis.OpenHubAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.analysis.OpenHubAnalysisResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.contributorfact.OpenHubContributorFactDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.contributorfact.OpenHubContributorFactResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.enlistment.OpenHubEnlistmentDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.enlistment.OpenHubEnlistmentResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.factoid.OpenHubFactoidDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.factoid.OpenHubFactoidResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.kudo.OpenHubKudoDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.kudo.OpenHubKudoResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.language.OpenHubLanguageDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.language.OpenHubLanguageResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.sizefact.OpenHubSizeFactDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.sizefact.OpenHubSizeFactResult;
import br.ufba.dcc.mestrado.computacao.openhub.data.stack.OpenHubStackDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.stack.OpenHubStackResult;
import br.ufba.dcc.mestrado.computacao.openhub.restful.request.OpenHubBaseRequest;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubAccountResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubActivityFactResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubAnalysisResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubBaseResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubContributorFactResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubEnlistmentResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubFactoidResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubKudoResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubLanguageResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubProjectResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubSizeFactResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubStackResponse;

@Component
@Scope("singleton")
public class OpenHubRestfulClientImpl implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6670332212734792427L;

	public static Logger logger = Logger.getLogger(OpenHubRestfulClientImpl.class);
	
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

	public OpenHubRestfulClientImpl() {
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
	
	private <T extends OpenHubBaseResponse> T processResponse(String url, OpenHubBaseRequest request, RestClient restClient, Object... params) {
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
					if (OpenHubBaseResponse.ERROR_API_KEY_EXCEDED.equals(resource.getError())) {
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
			throw new IllegalStateException(OpenHubBaseResponse.ERROR_API_KEY_EXCEDED);
		}
		
		logger.info(uri);
		return resource;
	}
	
	private String processRequest(String uri, OpenHubBaseRequest request) {
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
	public OpenHubLanguageDTO getLanguageById(String languageId, OpenHubBaseRequest request) {
		
		OpenHubLanguageDTO language = null;
		
		String url = environment.getProperty("meta.ohloh.api.language");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubLanguageResponse.class,
				OpenHubLanguageResult.class,
				OpenHubLanguageDTO.class));
		
		OpenHubLanguageResponse resource = this.<OpenHubLanguageResponse>processResponse(url, request, restfulie, languageId);
		if (OpenHubBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OpenHubLanguageDTO> openHubLanguageDTOs = resource.getResult().getLanguages();
			if (openHubLanguageDTOs != null && ! openHubLanguageDTOs.isEmpty())
				language =  openHubLanguageDTOs.get(0);
		}
		
		return language;
	}
	
	public OpenHubLanguageResponse getAllLanguages(OpenHubBaseRequest request) {
		OpenHubLanguageResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.language.all");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubLanguageResponse.class,
				OpenHubLanguageResult.class,
				OpenHubLanguageDTO.class));
		
		resource = this.<OpenHubLanguageResponse>processResponse(url, request, restfulie);
			
		return resource;
	}
	
	/**
	 * 
	 * @return
	 */
	public OpenHubAccountResponse getAllAccounts(OpenHubBaseRequest request) {
		
		OpenHubAccountResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.account.all");
		
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubAccountResponse.class,
				OpenHubAccountResult.class,
				OpenHubAccountDTO.class));
		
		resource = this.<OpenHubAccountResponse>processResponse(url, request, restfulie);
			
		
		return resource;
	}
	
	
	/**
	 * 
	 * @param accountId Id da conta do usuï¿½rio do OhLoh
	 * @return
	 */
	public OpenHubAccountDTO getAccountById(String accountId, OpenHubBaseRequest request) {
		
		OpenHubAccountDTO account = null;
		
		String url = environment.getProperty("meta.ohloh.api.account");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubAccountResponse.class,
				OpenHubAccountResult.class,
				OpenHubAccountDTO.class));
		
		OpenHubAccountResponse resource = this.<OpenHubAccountResponse>processResponse(url, request, restfulie, accountId);
		if (OpenHubBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OpenHubAccountDTO> openHubAccountDTOs = resource.getResult().getAccounts();
			if (openHubAccountDTOs != null && ! openHubAccountDTOs.isEmpty())
				account =  openHubAccountDTOs.get(0);
		}
		
		return account;
	}

	/**
	 * 
	 * @return
	 */
	public OpenHubProjectResponse getAllProjects(OpenHubBaseRequest request) {
		
		OpenHubProjectResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.project.all");
		
		RestClient restfulie = Restfulie.custom();
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubProjectResponse.class,
				OpenHubProjectResult.class,
				OpenHubProjectDTO.class));
		
		resource = this.<OpenHubProjectResponse>processResponse(url, request, restfulie);
			
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId ID de um projeto cadastrado no OhLoh
	 * @return
	 */
	public OpenHubProjectDTO getProject(String projectId, OpenHubBaseRequest request) {
		OpenHubProjectDTO project = null;
		
		String url = environment.getProperty("meta.ohloh.api.project");
		
		
		RestClient restfulie = Restfulie.custom();
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubProjectResponse.class,
				OpenHubProjectResult.class,
				OpenHubProjectDTO.class));
		
		OpenHubProjectResponse resource = this.<OpenHubProjectResponse>processResponse(url, request, restfulie, projectId);
		
		if (OpenHubBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OpenHubProjectDTO> openHubProjectDTOs = resource.getResult().getProjects();
			if (openHubProjectDTOs != null && ! openHubProjectDTOs.isEmpty()) {
				project = openHubProjectDTOs.get(0);
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
	public OpenHubStackDTO getSingleAccountStack(String accountId, String stackId, OpenHubBaseRequest request) {
		
		OpenHubStackDTO stack = null;
		
		String url = environment.getProperty("meta.ohloh.api.stack.account");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubStackResponse.class,
				OpenHubStackResult.class,
				OpenHubStackDTO.class));
		
		
		OpenHubStackResponse resource = this.<OpenHubStackResponse>processResponse(url, request, restfulie);
		if (OpenHubBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OpenHubStackDTO> openHubStackDTOs = resource.getResult().getStacks();
			if (openHubStackDTOs != null && ! openHubStackDTOs.isEmpty()) {
				stack = openHubStackDTOs.get(0);
			}
		}
			
		return stack;
	}
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public OpenHubStackDTO getDefaultAccountStack(String accountId, OpenHubBaseRequest request) {
		OpenHubStackDTO stack = null;
		
		String url = environment.getProperty("meta.ohloh.api.stack.account.default");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubStackResponse.class,
				OpenHubStackResult.class,
				OpenHubStackDTO.class));
		
		OpenHubStackResponse resource = this.<OpenHubStackResponse>processResponse(url, request, restfulie, accountId);
		if (OpenHubBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OpenHubStackDTO> openHubStackDTOs = resource.getResult().getStacks();
			if (openHubStackDTOs != null && ! openHubStackDTOs.isEmpty()) {
				stack = openHubStackDTOs.get(0);
			}
		}
		
		return stack;
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public OpenHubStackResponse getProjectStacks(String projectId, OpenHubBaseRequest request) {
		
		OpenHubStackResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.stack.project");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubStackResponse.class,
				OpenHubStackResult.class,
				OpenHubStackDTO.class));
		
		resource = this.<OpenHubStackResponse>processResponse(url, request, restfulie, projectId);
						
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public OpenHubFactoidResponse getAllFactoidsByProject(String projectId, OpenHubBaseRequest request) {
		OpenHubFactoidResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.factoid.project");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubFactoidResponse.class,
				OpenHubFactoidResult.class,
				OpenHubFactoidDTO.class));
		
		resource = this.<OpenHubFactoidResponse>processResponse(url, request, restfulie);
			
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @param factoidId
	 * @return
	 */
	public OpenHubFactoidResponse getSingleFactoidByProject(String projectId, String factoidId, OpenHubBaseRequest request) {
		OpenHubFactoidResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.factoid");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubFactoidResponse.class,
				OpenHubFactoidResult.class,
				OpenHubFactoidDTO.class));
		
		resource = this.<OpenHubFactoidResponse>processResponse(url, request, restfulie);
			
		return resource;
	}
	
	public OpenHubSizeFactResponse getLatestSizeFackByProject(String projectId) {
		return getAnalysisSizeFactByProject(projectId, "latest");
	}
	
	/**
	 * 
	 * @param projectId
	 * @param analysisId
	 * @param request
	 * @return
	 */
	public OpenHubSizeFactResponse getAnalysisSizeFactByProject(String projectId, String analysisId) {
		OpenHubSizeFactResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.size_facts");
		
		RestClient restfulie = Restfulie.custom();
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubSizeFactResponse.class,
				OpenHubSizeFactResult.class,
				OpenHubSizeFactDTO.class));
		
		resource = this.<OpenHubSizeFactResponse>processResponse(url, null, restfulie, projectId, analysisId);
		
		return resource;
	}
	
	/**
	 * 
	 * @param analysisId
	 * @param request
	 * @return
	 */
	public OpenHubAnalysisDTO getAnalysisById(String projectId, String analysisId, OpenHubBaseRequest request) {
		
		OpenHubAnalysisDTO analysis = null;
		
		String url = environment.getProperty("meta.ohloh.api.analysis");

		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubAnalysisResponse.class,
				OpenHubAnalysisResult.class,
				OpenHubAnalysisDTO.class));
		
		OpenHubAnalysisResponse resource = this.<OpenHubAnalysisResponse>processResponse(url, request, restfulie, projectId, analysisId);
		if (resource != null && OpenHubBaseResponse.SUCCESS.equals(resource.getStatus())) {
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
	public OpenHubAnalysisDTO getLatestAnalysis(String projectId, OpenHubBaseRequest request) {
		return getAnalysisById(projectId, "latest", request);
	}
	
	/**
	 * 
	 * @return
	 */
	public OpenHubContributorFactDTO getProjectContributorFactById(String projectId, String contributorId, OpenHubBaseRequest request) {
		OpenHubContributorFactDTO contributorFact = null;
		
		String url = environment.getProperty("meta.ohloh.api.contributor_facts");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubContributorFactResponse.class,
				OpenHubContributorFactResult.class,
				OpenHubContributorFactDTO.class));

		OpenHubContributorFactResponse resource = this.<OpenHubContributorFactResponse>processResponse(url, request, restfulie, projectId, contributorId);
		
		if (resource != null && OpenHubBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OpenHubContributorFactDTO> openHubContributorFactDTOs = resource.getResult().getContributorFacts();
			
			if (openHubContributorFactDTOs != null && ! openHubContributorFactDTOs.isEmpty()) {
				contributorFact = openHubContributorFactDTOs.get(0);
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
	public OpenHubContributorFactResponse getAllProjectContributorFacts(String projectId, OpenHubBaseRequest request) {
		OpenHubContributorFactResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.contributor_facts.all");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubContributorFactResponse.class,
				OpenHubContributorFactResult.class,
				OpenHubContributorFactDTO.class));
		
		
		resource = this.<OpenHubContributorFactResponse>processResponse(url, request, restfulie);
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @param analysisId
	 * @return
	 */
	public OpenHubActivityFactResponse getProjectActivityFactByAnalysisId(String projectId, String analysisId) {
		OpenHubActivityFactResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.activity_facts");
					
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubActivityFactResponse.class,
				OpenHubActivityFactResult.class,
				OpenHubActivityFactDTO.class));
		
		
		resource = this.<OpenHubActivityFactResponse>processResponse(url, null, restfulie, projectId, analysisId);
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @return
	 */
	public OpenHubActivityFactResponse getLatestProjectActivityFacts(String projectId) {
		return getProjectActivityFactByAnalysisId(projectId, "latest");
	}
	
	/**
	 * 
	 * @param projectId
	 * @param request
	 * @return
	 */
	public OpenHubEnlistmentResponse getAllProjectEnlistments(String projectId, OpenHubBaseRequest request) {
		OpenHubEnlistmentResponse resource = null;
			
		String url = environment.getProperty("meta.ohloh.api.enlistment.all");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubEnlistmentResponse.class,
				OpenHubEnlistmentResult.class,
				OpenHubEnlistmentDTO.class));
		
		
		resource = this.<OpenHubEnlistmentResponse>processResponse(url, request, restfulie, projectId);
		
		return resource;
	}
	
	/**
	 * 
	 * @param projectId
	 * @param enlistmentId
	 * @return
	 */
	public OpenHubEnlistmentDTO getProjectEnlistmentById(String projectId, String enlistmentId) {
		OpenHubEnlistmentDTO enlistment = null;
		
		String url = environment.getProperty("meta.ohloh.api.enlistment");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubEnlistmentResponse.class,
				OpenHubEnlistmentResult.class,
				OpenHubEnlistmentDTO.class));

		OpenHubEnlistmentResponse resource = this.<OpenHubEnlistmentResponse>processResponse(url, null, restfulie, projectId, enlistmentId);
		
		if (resource != null && OpenHubBaseResponse.SUCCESS.equals(resource.getStatus())) {
			List<OpenHubEnlistmentDTO> openHubEnlistmentDTOs = resource.getResult().getEnlistments();
			
			if (openHubEnlistmentDTOs != null && ! openHubEnlistmentDTOs.isEmpty()) {
				enlistment = openHubEnlistmentDTOs.get(0);
			}
		}
		
		return enlistment;
	}
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public OpenHubKudoResponse getAllKudoReceivedByAccountId(String accountId) {
		OpenHubKudoResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.kudos.received");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubKudoResponse.class,
				OpenHubKudoResult.class,
				OpenHubKudoDTO.class));
		

		resource = this.<OpenHubKudoResponse>processResponse(url, null, restfulie);
		
		return resource;
	}
	
	
	/**
	 * 
	 * @param accountId
	 * @return
	 */
	public OpenHubKudoResponse getAllKudoSentByAccountId(String accountId) {
		OpenHubKudoResponse resource = null;
		
		String url = environment.getProperty("meta.ohloh.api.kudos.sent");
		
		RestClient restfulie = Restfulie.custom();
		
		restfulie.getMediaTypes().register(new XmlMediaType().withTypes(
				OpenHubKudoResponse.class,
				OpenHubKudoResult.class,
				OpenHubKudoDTO.class));
		

		resource = this.<OpenHubKudoResponse>processResponse(url, null, restfulie);
		
		return resource;
	}
}
