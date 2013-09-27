package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.analysis.OhLohAnalysisEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLicenseEntity;
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

@Service
public class OhLohProjectServiceImpl extends BaseOhLohServiceImpl<OhLohProjectDTO, Long, OhLohProjectEntity>
		implements OhLohProjectService {

	@Autowired
	public OhLohProjectServiceImpl(OhLohProjectRepository repository) {
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
	
	public Long countAll() {
		return getRepository().countAll();
	}
	
	public OhLohProjectEntity findById(Long id) {
		return getRepository().findById(id);
	}
	
	public List<OhLohProjectEntity> findAll(Integer startAt, Integer offset) {
		return getRepository().findAll(startAt, offset);
	}
	
	@Override
	public void validateEntity(OhLohProjectEntity entity) throws Exception {
		super.validateEntity(entity);
		
		
		reloadTagsFromDatabase(entity);
		
		reloadLicensesFromDatabase(entity);
		
		if (entity.getAnalysisId() != null) {
			OhLohAnalysisEntity analysis = analysisService.findById(entity.getAnalysisId());
			
			OhLohBaseRequest request = new OhLohBaseRequest();
			
			if (analysis == null) {
				OhLohAnalysisDTO analysisDTO = restfulClient.getAnalysisById(entity.getId().toString(), entity.getAnalysisId().toString(), request);
				analysis = analysisService.buildEntity(analysisDTO);
				analysisService.validateEntity(analysis);
				
				entity.setOhLohAnalysis(analysis);
			}
		}
		
	}

	@Override
	public void reloadAnalysisFromDatabase(OhLohProjectEntity entity) throws Exception{
		if (entity != null && entity.getOhLohAnalysis() != null) {
			OhLohAnalysisEntity analysis = analysisService.findById(entity.getOhLohAnalysis().getId());
			if (analysis != null) {
				entity.setOhLohAnalysis(analysis);
			}
		}
	}
	
	@Override
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
	
	@Override
	public OhLohProjectEntity process(OhLohProjectDTO dto) throws Exception{
		OhLohProjectEntity entity = super.process(dto);
		
		getRepository().save(entity);
		return entity;
	}

}
