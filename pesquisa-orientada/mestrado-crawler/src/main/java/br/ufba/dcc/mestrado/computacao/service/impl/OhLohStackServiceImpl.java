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

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLicenseEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.stack.OhLohStackEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.stack.OhLohStackEntryEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.project.OhLohProjectDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.data.stack.OhLohStackDTO;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.client.OhLohRestfulClient;
import br.ufba.dcc.mestrado.computacao.ohloh.restful.request.OhLohBaseRequest;
import br.ufba.dcc.mestrado.computacao.repository.base.StackRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.StackRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohStackService;

@Service(OhLohStackServiceImpl.BEAN_NAME)
public class OhLohStackServiceImpl extends DefaultOhLohServiceImpl<OhLohStackDTO, Long, OhLohStackEntity>
		implements OhLohStackService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2776699453019711606L;
	
	public static final String BEAN_NAME =  "ohLohStackService";

	protected static Logger logger = Logger.getLogger(OhLohStackServiceImpl.class.getName());

	@Autowired
	private OhLohProjectService projectService;
	
	@Autowired
	private OhLohRestfulClient restfulClient;
	
	@Autowired
	public OhLohStackServiceImpl(@Qualifier(StackRepositoryImpl.BEAN_NAME) StackRepository repository) {
		super(repository, OhLohStackDTO.class, OhLohStackEntity.class);
	}
	
	@Override
	public void validateEntity(OhLohStackEntity entity) throws Exception {
		super.validateEntity(entity);
		
		OhLohBaseRequest request  = new OhLohBaseRequest();
		
		if (entity.getStackEntries() != null) {
			Map<String, OhLohTagEntity> tagMap = new HashMap<>();
			Map<String, OhLohLicenseEntity> licenseMap = new HashMap<>();
			
			for (OhLohStackEntryEntity stackEntry : entity.getStackEntries()) {
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
						if (project.getTags() != null) {
							for (OhLohTagEntity tag : project.getTags()) {
								tagMap.put(tag.getName(), tag);
							}
						}
						
						
						if (project.getLicenses() != null) {
							for (OhLohLicenseEntity license : project.getLicenses()) {
								licenseMap.put(license.getName(), license);
							}
						}
					}
					
					stackEntry.setProject(project);
				}
			}
			
			for (OhLohStackEntryEntity stackEntry : entity.getStackEntries()) {
				if (stackEntry.getProject() != null && stackEntry.getProject().getTags() != null) {
					
					List<OhLohTagEntity> projectTagList = new ArrayList<>();
					List<OhLohLicenseEntity> projectLicenseList = new ArrayList<>();
					
					for (OhLohTagEntity tag : stackEntry.getProject().getTags()) {
						projectTagList.add(tagMap.get(tag.getName()));						
					}
					
					for (OhLohLicenseEntity license : stackEntry.getProject().getLicenses()) {
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
	public OhLohStackEntity process(OhLohStackDTO dto) throws Exception{
		OhLohStackEntity entity = super.process(dto);
		
		if (entity != null && entity.getStackEntries() != null) {
			Iterator<OhLohStackEntryEntity> iterator = entity.getStackEntries().iterator();
			while(iterator.hasNext()) {
				OhLohStackEntryEntity stackEntry  = iterator.next();
				
				if (stackEntry.getProject() != null) {
					projectService.reloadTagsFromDatabase(stackEntry.getProject());
					projectService.reloadLicensesFromDatabase(stackEntry.getProject());
					projectService.reloadAnalysisFromDatabase(stackEntry.getProject());
					
					OhLohProjectEntity project = projectService.save(stackEntry.getProject());
					
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
