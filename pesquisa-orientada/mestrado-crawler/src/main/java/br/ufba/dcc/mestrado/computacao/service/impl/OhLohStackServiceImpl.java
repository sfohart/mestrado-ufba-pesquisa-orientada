package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLicenseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.stack.OhLohStackEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.stack.OhLohStackEntryEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.stack.OhLohStackDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohStackRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohStackService;

@Service(OhLohStackServiceImpl.BEAN_NAME)
public class OhLohStackServiceImpl extends BaseOhLohServiceImpl<OhLohStackDTO, Long, OhLohStackEntity>
		implements OhLohStackService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2776699453019711606L;
	
	public static final String BEAN_NAME =  "ohLohStackService";

	protected Logger logger = Logger.getLogger(OhLohStackServiceImpl.class.getName());

	@Autowired
	public OhLohStackServiceImpl(OhLohStackRepository repository) {
		super(repository, OhLohStackDTO.class, OhLohStackEntity.class);
	}

	
	@Autowired
	private OhLohProjectService projectService;
	
	@Autowired
	private OhLohRestfulClient restfulClient;
	
	public Long countAll() {
		return getRepository().countAll();
	}
	
	public OhLohStackEntity findById(Long id) {
		return getRepository().findById(id);
	}
	
	public List<OhLohStackEntity> findAll(Integer startAt, Integer offset) {
		return getRepository().findAll(startAt, offset);
	}
	
	@Override
	public void validateEntity(OhLohStackEntity entity) throws Exception {
		super.validateEntity(entity);
		
		OhLohBaseRequest request  = new OhLohBaseRequest();
		
		if (entity.getOhLohStackEntries() != null) {
			Map<String, OhLohTagEntity> tagMap = new HashMap<>();
			Map<String, OhLohLicenseEntity> licenseMap = new HashMap<>();
			
			for (OhLohStackEntryEntity stackEntry : entity.getOhLohStackEntries()) {
				if (stackEntry.getProjectId() != null) {
					OhLohProjectEntity project = projectService.findById(stackEntry.getProjectId());
					
					if (project == null) {
						OhLohProjectDTO projectDTO = restfulClient.getProject(stackEntry.getProjectId().toString(), request);
						if (projectDTO != null) {
							project = projectService.buildEntity(projectDTO);
							if (project != null) {
								projectService.validateEntity(project);
							}
						}
					}
					
					if (project != null) {
						if (project.getOhLohTags() != null) {
							for (OhLohTagEntity tag : project.getOhLohTags()) {
								tagMap.put(tag.getName(), tag);
							}
						}
						
						
						if (project.getOhLohLicenses() != null) {
							for (OhLohLicenseEntity license : project.getOhLohLicenses()) {
								licenseMap.put(license.getName(), license);
							}
						}
					}
					
					stackEntry.setProject(project);
				}
			}
			
			for (OhLohStackEntryEntity stackEntry : entity.getOhLohStackEntries()) {
				if (stackEntry.getProject() != null && stackEntry.getProject().getOhLohTags() != null) {
					
					List<OhLohTagEntity> projectTagList = new ArrayList<>();
					List<OhLohLicenseEntity> projectLicenseList = new ArrayList<>();
					
					for (OhLohTagEntity tag : stackEntry.getProject().getOhLohTags()) {
						projectTagList.add(tagMap.get(tag.getName()));						
					}
					
					for (OhLohLicenseEntity license : stackEntry.getProject().getOhLohLicenses()) {
						projectLicenseList.add(licenseMap.get(license.getName()));						
					}
					
					stackEntry.getProject().getOhLohTags().clear();
					stackEntry.getProject().getOhLohTags().addAll(projectTagList);
					
					
					stackEntry.getProject().getOhLohLicenses().clear();
					stackEntry.getProject().getOhLohLicenses().addAll(projectLicenseList);
				}				
			}
		}
	}
	
	@Override
	public OhLohStackEntity process(OhLohStackDTO dto) throws Exception{
		OhLohStackEntity entity = super.process(dto);
		
		if (entity != null && entity.getOhLohStackEntries() != null) {
			Iterator<OhLohStackEntryEntity> iterator = entity.getOhLohStackEntries().iterator();
			while(iterator.hasNext()) {
				OhLohStackEntryEntity stackEntry  = iterator.next();
				
				if (stackEntry.getProject() != null) {
					projectService.reloadTagsFromDatabase(stackEntry.getProject());
					projectService.reloadLicensesFromDatabase(stackEntry.getProject());
					projectService.reloadAnalysisFromDatabase(stackEntry.getProject());
					
					OhLohProjectEntity project = projectService.saveEntity(stackEntry.getProject());
					
					if (project != null) {
						stackEntry.setProject(project);
						stackEntry.setProjectId(project.getId());
						
						stackEntry.setOhLohStack(entity);
						stackEntry.setStackId(entity.getId());
					} else {
						iterator.remove();
						logger.error(String.format("stack entry %d project %d nao persistiu corretamente", stackEntry.getId(), stackEntry.getProjectId()));
					}
				} else if (stackEntry.getProject() == null || stackEntry.getOhLohStack() == null) {
					iterator.remove();
					logger.error(String.format("stack entry %d project %d nao persistiu corretamente", stackEntry.getId(), stackEntry.getProjectId()));
				}
			}
		}
		
		getRepository().save(entity);
		return entity;
	}

}
