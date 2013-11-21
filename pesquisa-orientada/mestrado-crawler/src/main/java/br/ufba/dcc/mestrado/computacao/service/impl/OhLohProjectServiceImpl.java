package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		
		if (project != null && project.getOhLohLinks() != null) {
			linkMap = new HashMap<>();
			
			for (OhLohLinkEntity link : project.getOhLohLinks()) {
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
		
		if (entity.getAnalysisId() != null && entity.getAnalysisId() > 0) {
			OhLohAnalysisEntity analysis = analysisService.findById(entity.getAnalysisId());
			
			OhLohBaseRequest request = new OhLohBaseRequest();
			
			if (analysis == null) {
				OhLohAnalysisDTO analysisDTO = restfulClient.getAnalysisById(entity.getId().toString(), entity.getAnalysisId().toString(), request);
				analysis = analysisService.buildEntity(analysisDTO);
				analysisService.validateEntity(analysis);
				
				if (analysis != null) {
					analysis.setOhlohProject(entity);
					entity.setOhLohAnalysis(analysis);
				}
			}
		}
		
	}

	@Override
	@Transactional
	public void reloadAnalysisFromDatabase(OhLohProjectEntity entity) throws Exception{
		if (entity != null && entity.getOhLohAnalysis() != null) {
			OhLohAnalysisEntity analysis = analysisService.findById(entity.getOhLohAnalysis().getId());
			if (analysis != null) {
				entity.setOhLohAnalysis(analysis);
			}
		}
	}
	
	@Override
	@Transactional
	public void reloadLicensesFromDatabase(OhLohProjectEntity entity) throws Exception{
		if (entity != null && entity.getOhLohLicenses() != null) {
			List<OhLohLicenseEntity> licenseList = new ArrayList<OhLohLicenseEntity>();
			Iterator<OhLohLicenseEntity> licenseIterator = entity.getOhLohLicenses().iterator();
			
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
			
			entity.getOhLohLicenses().addAll(licenseList);
		}
	}

	@Override
	@Transactional
	public void reloadTagsFromDatabase(OhLohProjectEntity entity) throws Exception{
		if (entity != null && entity.getOhLohTags() != null) {
			List<OhLohTagEntity> tagList = new ArrayList<OhLohTagEntity>();
			Iterator<OhLohTagEntity> tagIterator = entity.getOhLohTags().iterator();
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
			
			entity.getOhLohTags().addAll(tagList);
		}
	}
	
}
