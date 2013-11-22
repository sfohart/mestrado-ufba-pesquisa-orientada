package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisLanguageEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.factoid.OhLohFactoidEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.language.OhLohLanguageEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisLanguagesRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAnalysisRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohTagRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohLanguageService;

@Service(OhLohAnalysisServiceImpl.BEAN_NAME)
public class OhLohAnalysisServiceImpl extends DefaultOhLohServiceImpl<OhLohAnalysisDTO, Long, OhLohAnalysisEntity>
		implements OhLohAnalysisService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3582447874034562222L;

	public static final String BEAN_NAME =  "ohLohAnalysisService";
	
	private static Logger logger = Logger.getLogger(OhLohAnalysisServiceImpl.class.getName());
	
	@Autowired
	public OhLohAnalysisServiceImpl(@Qualifier("ohLohAnalysisRepository") OhLohAnalysisRepository repository) {
		super(repository, OhLohAnalysisDTO.class, OhLohAnalysisEntity.class);
	}

	@Autowired
	private OhLohAnalysisRepository analysisRepository;
	
	@Autowired
	private OhLohAnalysisLanguagesRepository analysisLanguagesRepository;
	
	@Autowired
	private OhLohAnalysisLanguageRepository analysisLanguageRepository;
	
	@Autowired
	private OhLohRestfulClient restfulClient;

	@Autowired
	private OhLohTagRepository tagRepository;
	
	@Autowired
	private OhLohLicenseRepository licenseRepository;
	
	@Autowired
	private OhLohLanguageService languageService;
	
	@Override
	@Transactional
	public void validateEntity(OhLohAnalysisEntity entity) throws Exception {
		super.validateEntity(entity);
		
		if (entity != null) {
			if (entity.getOhLohAnalysisLanguages() != null && entity.getOhLohAnalysisLanguages().getContent() != null) {
				
				if (entity.getOhLohAnalysisLanguages().getId() == null || entity.getOhLohAnalysisLanguages().getId() == 0) {
					entity.getOhLohAnalysisLanguages().setId(entity.getId());
				}
				
				
				OhLohAnalysisLanguagesEntity analysisLanguagesEntity = analysisLanguagesRepository.findById(entity.getOhLohAnalysisLanguages().getId());
				if (analysisLanguagesEntity != null) {
					entity.setOhLohAnalysisLanguages(analysisLanguagesEntity);
				}
				
				if (entity.getOhLohFactoids() != null && ! entity.getOhLohFactoids().isEmpty()) {
					for (OhLohFactoidEntity factoid : entity.getOhLohFactoids()) {
						factoid.setOhLohAnalysis(entity);
						factoid.setAnalysisId(entity.getId());
					}
				}
				
				List<OhLohAnalysisLanguageEntity> analysisLanguageList = new ArrayList<>();
				
				OhLohBaseRequest request = new OhLohBaseRequest();
				
				for (OhLohAnalysisLanguageEntity analysisLanguage : entity.getOhLohAnalysisLanguages().getContent()) {
					
					if (analysisLanguage.getLanguageId() != null && analysisLanguage.getLanguageId() > 0) {
						OhLohLanguageEntity language = languageService.findById(analysisLanguage.getLanguageId());
						
						if (language == null) {
							OhLohLanguageDTO languageDTO = restfulClient.getLanguageById(analysisLanguage.getLanguageId().toString(), request);
							language = languageService.buildEntity(languageDTO);
							languageService.validateEntity(language);
							
							analysisLanguage.setOhLohLanguage(language);
						}
						analysisLanguageList.add(analysisLanguage);
					}
				}
				
				entity.getOhLohAnalysisLanguages().getContent().clear();
				entity.getOhLohAnalysisLanguages().getContent().addAll(analysisLanguageList);
			}
		}
	}
	


}
