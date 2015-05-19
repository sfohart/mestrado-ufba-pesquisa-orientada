package br.ufba.dcc.mestrado.computacao.recommender.evaluator.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.recommender.evaluator.base.OfflineRecommenderEvaluator;
import br.ufba.dcc.mestrado.computacao.service.base.SearchService;
import br.ufba.dcc.mestrado.computacao.service.core.base.ProjectDetailPageViewService;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

@Component(SimilarProjectsRecommenderEvaluator.BEAN_NAME)
public class SimilarProjectsRecommenderEvaluator implements OfflineRecommenderEvaluator {
	
	private static final Logger logger = Logger.getLogger(SimilarProjectsRecommenderEvaluator.class);
	
	public static final String BEAN_NAME = "similarProjectsRecommenderEvaluator";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectDetailPageViewService pageViewService;
	
	@Autowired
	private SearchService searchService;
	
	private Integer maxResults = 6;
	
	public static void main(String[] args) throws TasteException {
		OfflineRecommenderEvaluatorUtils.runEvaluator(SimilarProjectsRecommenderEvaluator.BEAN_NAME);
	}
	
	
	@Override
	public void evaluate() throws TasteException {
		List<UserEntity> users = userService.findAll();
		
		if (users != null) {
			generateTagIntersectionMatrix(users);
			evaluateBasedContentRecommendation(users);
		}
	}

	private void generateTagIntersectionMatrix(List<UserEntity> users) {
		logger.info("Gerando matriz de intersecção de palavras-chave para os projetos visualizados pelos usuários");
		for (UserEntity user : users) {
			List<OpenHubProjectEntity> viewedProjects = pageViewService.findAllProjectRecentlyViewedByUser(user);
			if (viewedProjects != null && viewedProjects.size() >= 2) {
				logger.info(String.format("Gerando matriz de intersecção de palavras-chave para os projetos visualizados pelo usuário %d", user.getId()));
				int size = viewedProjects.size();
				Integer intersectionMatrix[][] = new Integer[size][size];
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						OpenHubProjectEntity projectI = viewedProjects.get(i);
						OpenHubProjectEntity projectJ = viewedProjects.get(j);
						
						Collection<OpenHubTagEntity> intersection = (Collection<OpenHubTagEntity>) CollectionUtils.intersection(projectI.getTags(), projectJ.getTags());
						if (! intersection.isEmpty()) {
							intersectionMatrix[i][j] = intersection.size();
						} else {
							intersectionMatrix[i][j] = 0;
						}
					}
				}
				
				StringBuffer buffer = new StringBuffer();
				
				for (int i = 0; i < size; i++) {
					OpenHubProjectEntity projectI = viewedProjects.get(i);
					buffer.append(String.format(";%s", projectI.getName()));
				}
				
				System.out.println(buffer.toString());
				
				for (int i = 0; i < size; i++) {
					buffer = new StringBuffer();
					OpenHubProjectEntity projectI = viewedProjects.get(i);
					
					buffer.append(projectI.getName());
					for (int j = 0; j < size; j++) {
						buffer.append(String.format(";%d", intersectionMatrix[i][j]));
					}
					
					System.out.println(buffer.toString());
				}
				
			}
		}
		
	}


	@SuppressWarnings("unchecked")
	private void evaluateBasedContentRecommendation(List<UserEntity> users)
			throws TasteException {
		System.out.println("id do usuario;quantidade de projetos visualizados;buscando projetos similares a este;projeto recomendado;posicao em que o projeto recomendado aparece;interseccao no conjunto de tags");
		for (UserEntity user : users) {
			List<OpenHubProjectEntity> viewedProjects = pageViewService.findAllProjectRecentlyViewedByUser(user);
			
			if (viewedProjects != null && viewedProjects.size() >= 2) {
				for (OpenHubProjectEntity project : viewedProjects) {
					
					List<OpenHubProjectEntity> similarProjects = null;
					try {
						similarProjects = searchService.findRelatedProjects(project, 0, maxResults);
					} catch (IOException e) {
						throw new TasteException(e);
					}
					
					if (similarProjects != null && ! similarProjects.isEmpty()) {
						boolean found = false;
						//for (OpenHubProjectEntity recommended : similarProjects) {
						for (int index = 0; index < similarProjects.size(); index++) {
							OpenHubProjectEntity recommended = similarProjects.get(index);
							if (! project.equals(recommended)) {
								if (viewedProjects.contains(recommended) ) {
									found = true;
									
									Integer intersectionSize = 0;
									for (OpenHubProjectEntity viewed : viewedProjects) {
										Collection<OpenHubTagEntity> intersection = (Collection<OpenHubTagEntity>) CollectionUtils.intersection(viewed.getTags(), recommended.getTags());
										if (intersectionSize > 0) {
											if (! intersection.isEmpty() && intersection.size() < intersectionSize) {
												intersectionSize = intersection.size();
											}
										} else if (! intersection.isEmpty()) {
											intersectionSize = intersection.size();
										}
										
									}
									
									String linha = String.format(
											"%d;%d;%s;%s;%d;%d",
											user.getId(),					//id do usu�rio
											viewedProjects.size(),			//quantidade de projetos visualizados
											project.getName(),				//buscando projetos similares a este
											recommended.getName(),			//projeto recomendado
											index + 1,						//posi��o em que o projeto recomendado aparece
											intersectionSize				//intersec��o no conjunto de tags
										);
									
									System.out.println(linha);
								}								
							}
						}
						
						if (! found) {
							String linha = String.format(
									"%d;%d;%s;%s;%d;%d",
									user.getId(),					//id do usu�rio
									viewedProjects.size(),			//quantidade de projetos visualizados
									project.getName(),				//buscando projetos similares a este
									"",								//projeto recomendado
									0,								//posi��o em que o projeto recomendado aparece
									0								//intersec��o no conjunto de tags
								);
							System.out.println(linha);
						}
					} else {
						String linha = String.format(
								"%d;%d;%s;%s;%d;%d",
								user.getId(),					//id do usu�rio
								viewedProjects.size(),			//quantidade de projetos visualizados
								project.getName(),				//buscando projetos similares a este
								"",								//projeto recomendado
								0,								//posi��o em que o projeto recomendado aparece
								0								//intersec��o no conjunto de tags
							);
						System.out.println(linha);
					}
					
				}
			}
		}
	}

	

}
