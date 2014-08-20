package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.analysis.OhLohAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.analysis.OhLohAnalysisLanguageEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.analysis.OhLohAnalysisLanguagesEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.factoid.OhLohFactoidEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.language.OhLohLanguageEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.language.OhLohLanguageDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisLanguageRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisLanguagesRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.AnalysisRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.LicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.TagRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.AnalysisRepositoryImpl;
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
	public OhLohAnalysisServiceImpl(@Qualifier(AnalysisRepositoryImpl.BEAN_NAME) AnalysisRepository repository) {
		super(repository, OhLohAnalysisDTO.class, OhLohAnalysisEntity.class);
	}

	@Autowired
	private AnalysisRepository analysisRepository;
	
	@Autowired
	private AnalysisLanguagesRepository analysisLanguagesRepository;
	
	@Autowired
	private AnalysisLanguageRepository analysisLanguageRepository;
	
	@Autowired
	private OhLohRestfulClient restfulClient;

	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private OhLohLanguageService languageService;
	
	@Override
	@Transactional
	public void validateEntity(OhLohAnalysisEntity entity) throws Exception {
		super.validateEntity(entity);
		
		if (entity != null) {
			if (entity.getAnalysisLanguages() != null && entity.getAnalysisLanguages().getContent() != null) {
				
				if (entity.getAnalysisLanguages().getId() == null || entity.getAnalysisLanguages().getId() == 0) {
					entity.getAnalysisLanguages().setId(entity.getId());
				}
				
				
				OhLohAnalysisLanguagesEntity analysisLanguagesEntity = analysisLanguagesRepository.findById(entity.getAnalysisLanguages().getId());
				if (analysisLanguagesEntity != null) {
					entity.setAnalysisLanguages(analysisLanguagesEntity);
				} else {
					analysisLanguagesEntity = entity.getAnalysisLanguages();
				}
				
				if (entity.getFactoids() != null && ! entity.getFactoids().isEmpty()) {
					for (OhLohFactoidEntity factoid : entity.getFactoids()) {
						factoid.setAnalysis(entity);
						factoid.setAnalysisId(entity.getId());
					}
				}
				
				List<OhLohAnalysisLanguageEntity> analysisLanguageList = new ArrayList<>();
				
				OhLohBaseRequest request = new OhLohBaseRequest();
				
				for (OhLohAnalysisLanguageEntity analysisLanguage : entity.getAnalysisLanguages().getContent()) {
					analysisLanguage.setAnalysisLanguages(analysisLanguagesEntity);
					
					if (analysisLanguage.getLanguageId() != null && analysisLanguage.getLanguageId() > 0) {
						OhLohLanguageEntity language = languageService.findById(analysisLanguage.getLanguageId());
						
						if (language == null) {
							OhLohLanguageDTO languageDTO = restfulClient.getLanguageById(analysisLanguage.getLanguageId().toString(), request);
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