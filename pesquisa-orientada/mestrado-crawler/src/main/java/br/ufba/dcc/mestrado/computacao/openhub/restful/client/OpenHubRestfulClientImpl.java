package br.ufba.dcc.mestrado.computacao.openhub.restful.client;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.openhub.data.analysis.OpenHubAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.language.OpenHubLanguageDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;
import br.ufba.dcc.mestrado.computacao.openhub.restful.request.OpenHubBaseRequest;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubActivityFactResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubAnalysisResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubBaseResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubEnlistmentResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubLanguageResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubProjectResponse;
import br.ufba.dcc.mestrado.computacao.openhub.restful.responses.OpenHubSizeFactResponse;

@Component
@Scope("singleton")
public class OpenHubRestfulClientImpl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6670332212734792427L;

	protected static final Logger logger = Logger
			.getLogger(OpenHubRestfulClientImpl.class.getName());

	/**
	 * Lista com todas as API Keys do OhLoh que eu estou utilizando
	 */
	@Autowired
	@Value("#{ '${meta.ohloh.api.key.all}'.split(',') }")
	private List<String> apiKeyList;

	/**
	 * Para pegar as URL's da API do OhLoh. N�o achei maneira mais limpa de
	 * fazer isso.
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
	
	protected Client configureClient() {
	    
		 TrustManager[] trustAllCerts = new TrustManager[]{
	        new X509TrustManager() {

	            @Override
	            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }

	            @Override
	            public void checkClientTrusted(
	                    java.security.cert.X509Certificate[] certs, String authType) {
	            }

	            @Override
	            public void checkServerTrusted(
	                    java.security.cert.X509Certificate[] certs, String authType) {
	            }
	        }
	    };

		 SSLContext sslContext = null;
		// Install the all-trusting trust manager
	    try {
	        sslContext = SSLContext.getInstance("SSL");
	        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	    } catch (Exception e) {
	    }
		
		Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
		
		return client;
	}
	
	protected WebTarget configureWebTarget() {
		return configureClient().target("https://www.openhub.net");
	}

	protected WebTarget configureApiKeyParams(WebTarget webTarget) {
		
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (getApiKeyList() != null && ! getApiKeyList().isEmpty()) {
			if (getCurrentApiKey() < getApiKeyList().size()) {
				String apiKey = getApiKeyList().get(getCurrentApiKey());
				return webTarget.queryParam("api_key", apiKey);
			} else {
				throw new IllegalStateException("N�o h� mais api keys dispon�veis para esta opera��o");
			}
		}
		
		return webTarget;
	}
	
	protected WebTarget configureRequestParams(WebTarget webTarget, OpenHubBaseRequest request) {
		
		if (request != null) {
			if (! StringUtils.isEmpty(request.getQuery())){
				webTarget = webTarget.queryParam("query", request.getQuery());
			}
			
			if (! StringUtils.isEmpty(request.getSort())){
				webTarget = webTarget.queryParam("sort", request.getSort());
			}
			
			if (request.getPage() != null) {
				webTarget = webTarget.queryParam("page", request.getPage().toString());
			}
		}
		
		return webTarget;
	}
	
	public OpenHubLanguageDTO getLanguageById(
			String languageId,
			OpenHubBaseRequest request) {
		
		OpenHubLanguageDTO language = null;
		String resourcePath = String.format("%d.xml", languageId);
		
		WebTarget webTarget = configureWebTarget();
		
		webTarget = webTarget.register(new GenericMessageBodyReader<OpenHubLanguageResponse>(OpenHubLanguageResponse.class));
		
		webTarget = webTarget
				.path("languages")
				.path(resourcePath);
		
		
		boolean retry = true;
		
		try {
			
			while (retry) {
				retry = false;
				
				webTarget = configureApiKeyParams(webTarget);
				webTarget = configureRequestParams(webTarget, request);
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_ATOM_XML_TYPE);
				OpenHubLanguageResponse languageResponse = invocationBuilder.get(OpenHubLanguageResponse.class);
				
				if (languageResponse != null) {
					if (! languageResponse.isStatusSuccess()) {
						if (OpenHubBaseResponse.ERROR_API_KEY_EXCEDED.equals(languageResponse.getError())) {
							if (getCurrentApiKey() + 1 < getApiKeyList().size()) {
								setCurrentApiKey(getCurrentApiKey() + 1);
								retry = true;
							}
						}						
					} else {
						List<OpenHubLanguageDTO> openHubLanguageDTOs = languageResponse.getResult().getLanguages();
						if (openHubLanguageDTOs != null && ! openHubLanguageDTOs.isEmpty()) {
							language =  openHubLanguageDTOs.get(0);
						}
					}
				}
				
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		
		return language;
		
	}

	public OpenHubLanguageResponse getAllLanguages(OpenHubBaseRequest request) {
		
		OpenHubLanguageResponse languageResponse = null;
		
		WebTarget webTarget = configureWebTarget();
		webTarget = webTarget.register(new GenericMessageBodyReader<OpenHubLanguageResponse>(OpenHubLanguageResponse.class));
		webTarget = webTarget.path("languages.xml");
		
		boolean retry = true;
		
		try {
			
			while (retry) {
				retry = false;
				
				webTarget = configureApiKeyParams(webTarget);
				webTarget = configureRequestParams(webTarget, request);
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML_TYPE);
				Response response = invocationBuilder.get();
				
				languageResponse = response.readEntity(OpenHubLanguageResponse.class);
				
				if (languageResponse != null) {
					if (! languageResponse.isStatusSuccess()) {
						if (OpenHubBaseResponse.ERROR_API_KEY_EXCEDED.equals(languageResponse.getError())) {
							if (getCurrentApiKey() + 1 < getApiKeyList().size()) {
								setCurrentApiKey(getCurrentApiKey() + 1);
								retry = true;
							}
						}						
					} 
				}
				
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		return languageResponse;
	}

	public OpenHubAnalysisDTO getAnalysisById(
			String projectId, 
			String analysisId,
			OpenHubBaseRequest request) {
		
		OpenHubAnalysisDTO analysis = null;
		String resourcePath = String.format("%s.xml", analysisId);
		
		WebTarget webTarget = configureWebTarget();
		
		webTarget = webTarget.register(new GenericMessageBodyReader<OpenHubAnalysisResponse>(OpenHubAnalysisResponse.class));
		
		webTarget = webTarget
				.path("projects")
				.path(projectId)
				.path("analyses")
				.path(resourcePath);
		
		
		boolean retry = true;
		
		try {
			
			while (retry) {
				retry = false;
				
				webTarget = configureApiKeyParams(webTarget);
				webTarget = configureRequestParams(webTarget, request);
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_ATOM_XML_TYPE);
				OpenHubAnalysisResponse analysisResponse = invocationBuilder.get(OpenHubAnalysisResponse.class);
				
				if (analysisResponse != null) {
					if (! analysisResponse.isStatusSuccess()) {
						if (OpenHubBaseResponse.ERROR_API_KEY_EXCEDED.equals(analysisResponse.getError())) {
							if (getCurrentApiKey() + 1 < getApiKeyList().size()) {
								setCurrentApiKey(getCurrentApiKey() + 1);
								retry = true;
							}
						}						
					} else {
						analysis = analysisResponse.getResult().getAnalises().get(0);
					}
				}
				
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		return analysis;
	}

	public OpenHubProjectDTO getProject(
			String projectId,
			OpenHubBaseRequest request) {
		
		
		OpenHubProjectDTO project = null;
		String resourcePath = String.format("%s.xml", projectId);
		
		WebTarget webTarget = configureWebTarget();
		
		webTarget = webTarget.register(new GenericMessageBodyReader<OpenHubProjectResponse>(OpenHubProjectResponse.class));
		
		webTarget = webTarget
				.path("projects")
				.path(resourcePath);
		
		
		boolean retry = true;
		
		try {
			
			while (retry) {
				retry = false;
				
				webTarget = configureApiKeyParams(webTarget);
				webTarget = configureRequestParams(webTarget, request);
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_ATOM_XML_TYPE);
				OpenHubProjectResponse projectResponse = invocationBuilder.get(OpenHubProjectResponse.class);
				
				if (projectResponse != null) {
					if (! projectResponse.isStatusSuccess()) {
						if (OpenHubBaseResponse.ERROR_API_KEY_EXCEDED.equals(projectResponse.getError())) {
							if (getCurrentApiKey() + 1 < getApiKeyList().size()) {
								setCurrentApiKey(getCurrentApiKey() + 1);
								retry = true;
							}
						}						
					} else {
						project = projectResponse.getResult().getProjects().get(0);
					}
				}
				
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		
		return project;
		
	}

	public OpenHubActivityFactResponse getLatestProjectActivityFacts(
			String projectId) {
		
		OpenHubActivityFactResponse activityFactResponse = null;
		
		WebTarget webTarget = configureWebTarget();
		
		webTarget = webTarget.register(new GenericMessageBodyReader<OpenHubActivityFactResponse>(OpenHubActivityFactResponse.class));
		
		webTarget = webTarget
				.path("projects")
				.path(projectId)
				.path("analyses")
				.path("latest")
				.path("activity_facts.xml");
		
		
		boolean retry = true;
		
		try {
			
			while (retry) {
				retry = false;
				
				webTarget = configureApiKeyParams(webTarget);
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_ATOM_XML_TYPE);
				activityFactResponse = invocationBuilder.get(OpenHubActivityFactResponse.class);
				
				if (activityFactResponse != null) {
					if (! activityFactResponse.isStatusSuccess()) {
						if (OpenHubBaseResponse.ERROR_API_KEY_EXCEDED.equals(activityFactResponse.getError())) {
							if (getCurrentApiKey() + 1 < getApiKeyList().size()) {
								setCurrentApiKey(getCurrentApiKey() + 1);
								retry = true;
							}
						}						
					} 
				}
				
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		return activityFactResponse;
	}

	public OpenHubEnlistmentResponse getAllProjectEnlistments(
			String projectId,
			OpenHubBaseRequest request) {
		
		OpenHubEnlistmentResponse enlistmentResponse = null;
		
		WebTarget webTarget = configureWebTarget();
		webTarget = webTarget.register(new GenericMessageBodyReader<OpenHubEnlistmentResponse>(OpenHubEnlistmentResponse.class));
		
		webTarget = webTarget
				.path("projects")
				.path(projectId)
				.path("enlistments.xml");
		
		boolean retry = true;
		
		try {
			
			while (retry) {
				retry = false;
				
				webTarget = configureApiKeyParams(webTarget);
				webTarget = configureRequestParams(webTarget, request);
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML_TYPE);
				Response response = invocationBuilder.get();
				
				enlistmentResponse = response.readEntity(OpenHubEnlistmentResponse.class);
				
				if (enlistmentResponse != null) {
					if (! enlistmentResponse.isStatusSuccess()) {
						if (OpenHubBaseResponse.ERROR_API_KEY_EXCEDED.equals(enlistmentResponse.getError())) {
							if (getCurrentApiKey() + 1 < getApiKeyList().size()) {
								setCurrentApiKey(getCurrentApiKey() + 1);
								retry = true;
							}
						}						
					} 
				}
				
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		return enlistmentResponse;
	}

	

	public OpenHubProjectResponse getAllProjects(OpenHubBaseRequest request) {
		OpenHubProjectResponse projectResponse = null;
		
		WebTarget webTarget = configureWebTarget();
		webTarget = webTarget.register(new GenericMessageBodyReader<OpenHubProjectResponse>(OpenHubProjectResponse.class));
		webTarget = webTarget.path("projects.xml");
		
		boolean retry = true;
		
		try {
			
			while (retry) {
				retry = false;
				
				webTarget = configureApiKeyParams(webTarget);
				webTarget = configureRequestParams(webTarget, request);
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_XML_TYPE);
				Response response = invocationBuilder.get();
				
				projectResponse = response.readEntity(OpenHubProjectResponse.class);
				
				if (projectResponse != null) {
					if (! projectResponse.isStatusSuccess()) {
						if (OpenHubBaseResponse.ERROR_API_KEY_EXCEDED.equals(projectResponse.getError())) {
							if (getCurrentApiKey() + 1 < getApiKeyList().size()) {
								setCurrentApiKey(getCurrentApiKey() + 1);
								retry = true;
							}
						}						
					} 
				}
				
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		return projectResponse;
	}

	public OpenHubSizeFactResponse getLatestSizeFackByProject(String projectId) {
		
		OpenHubSizeFactResponse sizeFactResponse = null;
		
		WebTarget webTarget = configureWebTarget();
		
		webTarget = webTarget.register(new GenericMessageBodyReader<OpenHubSizeFactResponse>(OpenHubSizeFactResponse.class));
		
		webTarget = webTarget
				.path("projects")
				.path(projectId)
				.path("analyses")
				.path("latest")
				.path("size_facts.xml");
		
		
		boolean retry = true;
		
		try {
			
			while (retry) {
				retry = false;
				
				webTarget = configureApiKeyParams(webTarget);
				
				Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_ATOM_XML_TYPE);
				sizeFactResponse = invocationBuilder.get(OpenHubSizeFactResponse.class);
				
				if (sizeFactResponse != null) {
					if (! sizeFactResponse.isStatusSuccess()) {
						if (OpenHubBaseResponse.ERROR_API_KEY_EXCEDED.equals(sizeFactResponse.getError())) {
							if (getCurrentApiKey() + 1 < getApiKeyList().size()) {
								setCurrentApiKey(getCurrentApiKey() + 1);
								retry = true;
							}
						}						
					} 
				}
				
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		return sizeFactResponse;
	}

}
