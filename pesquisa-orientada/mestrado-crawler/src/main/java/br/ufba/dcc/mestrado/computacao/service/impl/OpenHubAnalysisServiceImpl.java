package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis.OpenHubAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis.OpenHubAnalysisLanguageEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis.OpenHubAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.factoid.OpenHubFactoidEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.language.OpenHubLanguageEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.analysis.OpenHubAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.language.OpenHubLanguageDTO;
import br.ufba.dcc.mestrado.computacao.openhub.restful.client.OpenHubRestfulClientImpl;
import br.ufba.dcc.mestrado.computacao.openhub.restful.request.OpenHubBaseRequest;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisLanguagesRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.LicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.TagRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.AnalysisRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubAnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubLanguageService;

@Service(OpenHubAnalysisServiceImpl.BEAN_NAME)
public class OpenHubAnalysisServiceImpl extends DefaultOpenHubServiceImpl<OpenHubAnalysisDTO, Long, OpenHubAnalysisEntity>
		implements OpenHubAnalysisService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3582447874034562222L;

	public static final String BEAN_NAME =  "ohLohAnalysisService";
	
	private static Logger logger = Logger.getLogger(OpenHubAnalysisServiceImpl.class.getName());
	
	@Autowired
	public OpenHubAnalysisServiceImpl(@Qualifier(AnalysisRepositoryImpl.BEAN_NAME) AnalysisRepository repository) {
		super(repository, OpenHubAnalysisDTO.class, OpenHubAnalysisEntity.class);
	}

	@Autowired
	private AnalysisRepository analysisRepository;
	
	@Autowired
	private AnalysisLanguagesRepository analysisLanguagesRepository;
	
	@Autowired
	private AnalysisLanguageRepository analysisLanguageRepository;
	
	@Autowired
	private OpenHubRestfulClientImpl restfulClient;

	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private OpenHubLanguageService languageService;
	
	@Override
	@Transactional
	public void validateEntity(OpenHubAnalysisEntity entity) throws Exception {
		super.validateEntity(entity);
		
		if (entity != null) {
			if (entity.getAnalysisLanguages() != null && entity.getAnalysisLanguages().getContent() != null) {
				
				if (entity.getAnalysisLanguages().getId() == null || entity.getAnalysisLanguages().getId() == 0) {
					entity.getAnalysisLanguages().setId(entity.getId());
				}
				
				
				OpenHubAnalysisLanguagesEntity analysisLanguagesEntity = analysisLanguagesRepository.findById(entity.getAnalysisLanguages().getId());
				if (analysisLanguagesEntity != null) {
					entity.setAnalysisLanguages(analysisLanguagesEntity);
				} else {
					analysisLanguagesEntity = entity.getAnalysisLanguages();
				}
				
				if (entity.getFactoids() != null && ! entity.getFactoids().isEmpty()) {
					for (OpenHubFactoidEntity factoid : entity.getFactoids()) {
						factoid.setAnalysis(entity);
						factoid.setAnalysisId(entity.getId());
					}
				}
				
				List<OpenHubAnalysisLanguageEntity> analysisLanguageList = new ArrayList<>();
				
				OpenHubBaseRequest request = new OpenHubBaseRequest();
				
				for (OpenHubAnalysisLanguageEntity analysisLanguage : entity.getAnalysisLanguages().getContent()) {
					analysisLanguage.setAnalysisLanguages(analysisLanguagesEntity);
					
					if (analysisLanguage.getLanguageId() != null && analysisLanguage.getLanguageId() > 0) {
						OpenHubLanguageEntity language = languageService.findById(analysisLanguage.getLanguageId());
						
						if (language == null) {
							OpenHubLanguageDTO languageDTO = restfulClient.getLanguageById(analysisLanguage.getLanguageId().toString(), request);
							language = languageService.buildEntity(languageDTO);
							languageService.validateEntity(language);
						}
						
						analysisLanguage.setLanguage(language);
					}
					
					analysisLanguageList.add(analysisLanguage);
				}
				
				entity.getAnalysisLanguages().getContent().clear();
				entity.getAnalysisLanguages().getContent().addAll(analysisLanguageList);
			}
		}
	}
	


}
