package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;

@ManagedBean(name = "compareMB")
@ViewScoped
public class ProjectCompareManagedBean implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3094192001849724997L;

	@ManagedProperty("#{projectService}")
	private ProjectService projectService;
	
	@ManagedProperty("#{overallPreferenceService}")
	private OverallPreferenceService overallPreferenceService;
	
	private List<OhLohProjectEntity> projectList;
	
	private Map<Long, PreferenceEntity> averagePreferenceMap;
	
	private Map<Long, Long> preferenceCountMap;
	
	public ProjectService getProjectService() {
		return projectService;
	}
	
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	public OverallPreferenceService getOverallPreferenceService() {
		return overallPreferenceService;
	}
	
	public void setOverallPreferenceService(
			OverallPreferenceService overallPreferenceService) {
		this.overallPreferenceService = overallPreferenceService;
	}
	
	public List<OhLohProjectEntity> getProjectList() {
		return projectList;
	}
	
	public Map<Long, PreferenceEntity> getAveragePreferenceMap() {
		return averagePreferenceMap;
	}
	
	public Map<Long, Long> getPreferenceCountMap() {
		return preferenceCountMap;
	}
	
	public void init(ComponentSystemEvent event) {
		Map<String, String[]> params = 
				FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.getRequestParameterValuesMap();
		
		String[] projectIdsParam = params.get("projectId");
		
		if (projectIdsParam != null) {
			
			if (projectIdsParam.length < 2) {
				FacesContext context = FacesContext.getCurrentInstance();
				ResourceBundle bundle = context.getApplication().getResourceBundle(context, "messages");
				
				String message = bundle.getString("project.compare.error.minNumber.projectId");
				FacesMessage facesMessage = new FacesMessage(message);
				
				context.addMessage(null, facesMessage);
				
			} else {
			
				this.projectList = new ArrayList<>();
				
				this.averagePreferenceMap = new HashMap<Long, PreferenceEntity>();
				this.preferenceCountMap = new HashMap<Long,Long>();
				
				for (String projectIdParam : projectIdsParam) {
					Long projectId = Long.valueOf(projectIdParam);
					
					if (projectId != null) {
						OhLohProjectEntity project = getProjectService().findById(projectId);
						this.projectList.add(project);
						
						PreferenceEntity averagePreference = getOverallPreferenceService().averagePreferenceByProject(projectId);
						this.averagePreferenceMap.put(projectId, averagePreference);
						
						Long preferenceCount = getOverallPreferenceService().countAllLastByProject(projectId);
						this.preferenceCountMap.put(projectId, preferenceCount);
					}
				}
			
			}
		}
	}
	
}
