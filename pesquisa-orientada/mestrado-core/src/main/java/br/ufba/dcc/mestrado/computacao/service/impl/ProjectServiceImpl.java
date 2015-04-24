package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.LicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.TagRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.ProjectRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.AnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;

@Service(ProjectServiceImpl.BEAN_NAME)
public class ProjectServiceImpl extends BaseServiceImpl<Long, OpenHubProjectEntity>
		implements ProjectService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3493252321709174022L;
	
	
	public static final String BEAN_NAME =  "projectService";

	@Autowired
	public ProjectServiceImpl(@Qualifier(ProjectRepositoryImpl.BEAN_NAME) ProjectRepository repository) {
		super(repository,  OpenHubProjectEntity.class);
	}


	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private AnalysisService analysisService;
	
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
	
}
