package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubLicenseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.stack.OpenHubStackEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.stack.OpenHubStackEntryEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.project.OpenHubProjectDTO;
import br.ufba.dcc.mestrado.computacao.openhub.data.stack.OpenHubStackDTO;
import br.ufba.dcc.mestrado.computacao.openhub.restful.client.OpenHubRestfulClientImpl;
import br.ufba.dcc.mestrado.computacao.openhub.restful.request.OpenHubBaseRequest;
import br.ufba.dcc.mestrado.computacao.repository.base.StackRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.StackRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubStackService;

@Service(OpenHubStackServiceImpl.BEAN_NAME)
public class OpenHubStackServiceImpl extends DefaultOpenHubServiceImpl<OpenHubStackDTO, Long, OpenHubStackEntity>
		implements OpenHubStackService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2776699453019711606L;
	
	public static final String BEAN_NAME =  "ohLohStackService";

	protected static Logger logger = Logger.getLogger(OpenHubStackServiceImpl.class.getName());

	@Autowired
	private OpenHubProjectService projectService;
	
	@Autowired
	private OpenHubRestfulClientImpl restfulClient;
	
	@Autowired
	public OpenHubStackServiceImpl(@Qualifier(StackRepositoryImpl.BEAN_NAME) StackRepository repository) {
		super(repository, OpenHubStackDTO.class, OpenHubStackEntity.class);
	}
	
	@Override
	public void validateEntity(OpenHubStackEntity entity) throws Exception {
		super.validateEntity(entity);
		
		OpenHubBaseRequest request  = new OpenHubBaseRequest();
		
		if (entity.getStackEntries() != null) {
			Map<String, OpenHubTagEntity> tagMap = new HashMap<>();
			Map<String, OpenHubLicenseEntity> licenseMap = new HashMap<>();
			
			for (OpenHubStackEntryEntity stackEntry : entity.getStackEntries()) {
				if (stackEntry.getProjectId() != null) {
					OpenHubProjectEntity project = projectService.findById(stackEntry.getProjectId());
					
					if (project == null) {
						OpenHubProjectDTO projectDTO = restfulClient.getProject(stackEntry.getProjectId().toString(), request);
						if (projectDTO != null) {
							project = projectService.buildEntity(projectDTO);
							if (project != null) {
								projectService.validateEntity(project);
							}
						}
					}
					
					if (project != null) {
						if (project.getTags() != null) {
							for (OpenHubTagEntity tag : project.getTags()) {
								tagMap.put(tag.getName(), tag);
							}
						}
						
						
						if (project.getLicenses() != null) {
							for (OpenHubLicenseEntity license : project.getLicenses()) {
								licenseMap.put(license.getName(), license);
							}
						}
					}
					
					stackEntry.setProject(project);
				}
			}
			
			for (OpenHubStackEntryEntity stackEntry : entity.getStackEntries()) {
				if (stackEntry.getProject() != null && stackEntry.getProject().getTags() != null) {
					
					List<OpenHubTagEntity> projectTagList = new ArrayList<>();
					List<OpenHubLicenseEntity> projectLicenseList = new ArrayList<>();
					
					for (OpenHubTagEntity tag : stackEntry.getProject().getTags()) {
						projectTagList.add(tagMap.get(tag.getName()));						
					}
					
					for (OpenHubLicenseEntity license : stackEntry.getProject().getLicenses()) {
						projectLicenseList.add(licenseMap.get(license.getName()));						
					}
					
					stackEntry.getProject().getTags().clear();
					stackEntry.getProject().getTags().addAll(projectTagList);
					
					
					stackEntry.getProject().getLicenses().clear();
					stackEntry.getProject().getLicenses().addAll(projectLicenseList);
				}				
			}
		}
	}
	
	@Override
	@Transactional
	public OpenHubStackEntity process(OpenHubStackDTO dto) throws Exception{
		OpenHubStackEntity entity = super.process(dto);
		
		if (entity != null && entity.getStackEntries() != null) {
			Iterator<OpenHubStackEntryEntity> iterator = entity.getStackEntries().iterator();
			while(iterator.hasNext()) {
				OpenHubStackEntryEntity stackEntry  = iterator.next();
				
				if (stackEntry.getProject() != null) {
					projectService.reloadTagsFromDatabase(stackEntry.getProject());
					projectService.reloadLicensesFromDatabase(stackEntry.getProject());
					projectService.reloadAnalysisFromDatabase(stackEntry.getProject());
					
					OpenHubProjectEntity project = projectService.save(stackEntry.getProject());
					
					if (project != null) {
						stackEntry.setProject(project);
						stackEntry.setProjectId(project.getId());
						
						stackEntry.setStack(entity);
						stackEntry.setStackId(entity.getId());
					} else {
						iterator.remove();
						logger.error(String.format("stack entry %d project %d nao persistiu corretamente", stackEntry.getId(), stackEntry.getProjectId()));
					}
				} else if (stackEntry.getProject() == null || stackEntry.getStack() == null) {
					iterator.remove();
					logger.error(String.format("stack entry %d project %d nao persistiu corretamente", stackEntry.getId(), stackEntry.getProjectId()));
				}
			}
		}
		
		getRepository().save(entity);
		return entity;
	}

}
