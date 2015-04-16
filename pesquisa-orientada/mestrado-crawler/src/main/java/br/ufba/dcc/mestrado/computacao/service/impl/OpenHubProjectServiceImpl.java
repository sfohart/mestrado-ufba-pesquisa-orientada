package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.analysis.OpenHubAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLicenseEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectActivityIndexEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.analysis.OpenHubAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;
import br.ufba.dcc.mestrado.computacao.openhub.restful.client.OpenHubRestfulClientImpl;
import br.ufba.dcc.mestrado.computacao.openhub.restful.request.OpenHubBaseRequest;
import br.ufba.dcc.mestrado.computacao.repository.base.LicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.TagRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.ProjectRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubAnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectActivityIndexService;

@Service(OpenHubProjectServiceImpl.BEAN_NAME)
public class OpenHubProjectServiceImpl extends DefaultOpenHubServiceImpl<OpenHubProjectDTO, Long, OpenHubProjectEntity>
		implements OpenHubProjectService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3493252321709174022L;
	
	
	public static final String BEAN_NAME =  "ohLohProjectService";

	@Autowired
	public OpenHubProjectServiceImpl(@Qualifier(ProjectRepositoryImpl.BEAN_NAME) ProjectRepository repository) {
		super(repository, OpenHubProjectDTO.class, OpenHubProjectEntity.class);
	}


	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private OpenHubAnalysisService analysisService;
	
	@Autowired
	private OpenHubRestfulClientImpl restfulClient;
	
	@Autowired
	private ProjectActivityIndexService projectActivityIndexService;
	
	
	public Map<String, List<OpenHubLinkEntity>> buildLinkMapByCategory(OpenHubProjectEntity project) {
		Map<String, List<OpenHubLinkEntity>> linkMap = null;
		
		if (project != null && project.getLinks() != null) {
			linkMap = new HashMap<>();
			
			for (OpenHubLinkEntity link : project.getLinks()) {
				List<OpenHubLinkEntity> linkList = linkMap.get(link.getCategory());
				
				if (linkList == null) {
					linkList = new ArrayList<>();
				}
				
				linkList.add(link);
				linkMap.put(link.getCategory(), linkList);
			}
		}
		
		return linkMap;
	}
	
	@Override
	@Transactional
	public void validateEntity(OpenHubProjectEntity entity) throws Exception {
		super.validateEntity(entity);
		
		
		reloadTagsFromDatabase(entity);		
		reloadLicensesFromDatabase(entity);
		reloadProjectActivityIndexFromDatabase(entity);

		if (entity.getLinks() != null) {
			for (OpenHubLinkEntity link : entity.getLinks()) {
				link.setProject(entity);
			}
		}
		
		if (entity.getAnalysisId() != null && entity.getAnalysisId() > 0) {
			OpenHubAnalysisEntity analysis = analysisService.findById(entity.getAnalysisId());
			
			OpenHubBaseRequest request = new OpenHubBaseRequest();
			
			if (analysis == null) {
				OpenHubAnalysisDTO analysisDTO = restfulClient.getAnalysisById(entity.getId().toString(), entity.getAnalysisId().toString(), request);
				analysis = analysisService.buildEntity(analysisDTO);
				analysisService.validateEntity(analysis);
				
				if (analysis != null) {
					analysis.setProject(entity);
					entity.setAnalysis(analysis);
				}
			}
		}
		
	}
	
	@Override
	@Transactional
	public void reloadProjectActivityIndexFromDatabase(OpenHubProjectEntity entity) throws Exception{
		if (entity != null && entity.getProjectActivityIndex() != null) {
			OpenHubProjectActivityIndexEntity projectActivityIndex = projectActivityIndexService.findByValue(entity.getProjectActivityIndex().getValue());
			if (projectActivityIndex != null) {
				entity.setProjectActivityIndex(projectActivityIndex);
			}
		}
	}

	@Override
	@Transactional
	public void reloadAnalysisFromDatabase(OpenHubProjectEntity entity) throws Exception{
		if (entity != null && entity.getAnalysis() != null) {
			OpenHubAnalysisEntity analysis = analysisService.findById(entity.getAnalysis().getId());
			if (analysis != null) {
				entity.setAnalysis(analysis);
			}
		}
	}
	
	@Override
	@Transactional
	public void reloadLicensesFromDatabase(OpenHubProjectEntity entity) throws Exception{
		if (entity != null && entity.getLicenses() != null) {
			Set<OpenHubLicenseEntity> licenseList = new HashSet<OpenHubLicenseEntity>();
			Iterator<OpenHubLicenseEntity> licenseIterator = entity.getLicenses().iterator();
			
			while (licenseIterator.hasNext()) {
				OpenHubLicenseEntity license = licenseIterator.next();
				OpenHubLicenseEntity already = licenseRepository.findByName(license.getName());
				
				if (already != null) {
					licenseIterator.remove();
				} else {
					license.setId(null);
					already = licenseRepository.save(license);
				}
				
				licenseList.add(already);
			}
			
			entity.getLicenses().clear();
			entity.getLicenses().addAll(licenseList);
		}
	}

	@Override
	@Transactional
	public void reloadTagsFromDatabase(OpenHubProjectEntity entity) throws Exception{
		if (entity != null && entity.getTags() != null) {
			Set<OpenHubTagEntity> tagList = new HashSet<OpenHubTagEntity>();
			Iterator<OpenHubTagEntity> tagIterator = entity.getTags().iterator();
			while (tagIterator.hasNext()) {
				OpenHubTagEntity tag = tagIterator.next();				
				OpenHubTagEntity already = tagRepository.findByName(tag.getName());
				
				if (already != null) {
					tagIterator.remove();
				} else {
					tag.setId(null);
					already = tagRepository.save(tag);
				}
					
				tagList.add(already);
			}
			
			entity.getTags().clear();
			entity.getTags().addAll(tagList);
		}
	}
	
}
