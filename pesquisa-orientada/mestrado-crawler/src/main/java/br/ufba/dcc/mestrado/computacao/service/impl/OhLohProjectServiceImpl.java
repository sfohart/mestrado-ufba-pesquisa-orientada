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

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLicenseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.analysis.OhLohAnalysisDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohLicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohTagRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;

@Service(OhLohProjectServiceImpl.BEAN_NAME)
public class OhLohProjectServiceImpl extends DefaultOhLohServiceImpl<OhLohProjectDTO, Long, OhLohProjectEntity>
		implements OhLohProjectService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3493252321709174022L;
	
	
	public static final String BEAN_NAME =  "ohLohProjectService";

	@Autowired
	public OhLohProjectServiceImpl(@Qualifier("ohLohProjectRepository") OhLohProjectRepository repository) {
		super(repository, OhLohProjectDTO.class, OhLohProjectEntity.class);
	}


	@Autowired
	private OhLohTagRepository tagRepository;
	
	@Autowired
	private OhLohLicenseRepository licenseRepository;
	
	@Autowired
	private OhLohAnalysisService analysisService;
	
	@Autowired
	private OhLohRestfulClient restfulClient;
	
	
	public Map<String, List<OhLohLinkEntity>> buildLinkMapByCategory(OhLohProjectEntity project) {
		Map<String, List<OhLohLinkEntity>> linkMap = null;
		
		if (project != null && project.getLinks() != null) {
			linkMap = new HashMap<>();
			
			for (OhLohLinkEntity link : project.getLinks()) {
				List<OhLohLinkEntity> linkList = linkMap.get(link.getCategory());
				
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
	public void validateEntity(OhLohProjectEntity entity) throws Exception {
		super.validateEntity(entity);
		
		
		reloadTagsFromDatabase(entity);
		
		reloadLicensesFromDatabase(entity);

		if (entity.getLinks() != null) {
			for (OhLohLinkEntity link : entity.getLinks()) {
				link.setProject(entity);
			}
		}
		
		if (entity.getAnalysisId() != null && entity.getAnalysisId() > 0) {
			OhLohAnalysisEntity analysis = analysisService.findById(entity.getAnalysisId());
			
			OhLohBaseRequest request = new OhLohBaseRequest();
			
			if (analysis == null) {
				OhLohAnalysisDTO analysisDTO = restfulClient.getAnalysisById(entity.getId().toString(), entity.getAnalysisId().toString(), request);
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
	public void reloadAnalysisFromDatabase(OhLohProjectEntity entity) throws Exception{
		if (entity != null && entity.getAnalysis() != null) {
			OhLohAnalysisEntity analysis = analysisService.findById(entity.getAnalysis().getId());
			if (analysis != null) {
				entity.setAnalysis(analysis);
			}
		}
	}
	
	@Override
	@Transactional
	public void reloadLicensesFromDatabase(OhLohProjectEntity entity) throws Exception{
		if (entity != null && entity.getLicenses() != null) {
			Set<OhLohLicenseEntity> licenseList = new HashSet<OhLohLicenseEntity>();
			Iterator<OhLohLicenseEntity> licenseIterator = entity.getLicenses().iterator();
			
			while (licenseIterator.hasNext()) {
				OhLohLicenseEntity license = licenseIterator.next();
				OhLohLicenseEntity already = licenseRepository.findByName(license.getName());
				
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
	public void reloadTagsFromDatabase(OhLohProjectEntity entity) throws Exception{
		if (entity != null && entity.getTags() != null) {
			Set<OhLohTagEntity> tagList = new HashSet<OhLohTagEntity>();
			Iterator<OhLohTagEntity> tagIterator = entity.getTags().iterator();
			while (tagIterator.hasNext()) {
				OhLohTagEntity tag = tagIterator.next();				
				OhLohTagEntity already = tagRepository.findByName(tag.getName());
				
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
