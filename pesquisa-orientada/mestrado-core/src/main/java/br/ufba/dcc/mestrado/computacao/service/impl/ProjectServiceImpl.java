package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohLinkEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.LicenseRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.ProjectRepository;
import br.ufba.dcc.mestrado.computacao.repository.base.TagRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.ProjectRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.AnalysisService;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;

@Service(ProjectServiceImpl.BEAN_NAME)
public class ProjectServiceImpl extends BaseServiceImpl<Long, OhLohProjectEntity>
		implements ProjectService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3493252321709174022L;
	
	
	public static final String BEAN_NAME =  "projectService";

	@Autowired
	public ProjectServiceImpl(@Qualifier(ProjectRepositoryImpl.BEAN_NAME) ProjectRepository repository) {
		super(repository,  OhLohProjectEntity.class);
	}


	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private AnalysisService analysisService;
	
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
	
}
