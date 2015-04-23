package br.ufba.dcc.mestrado.computacao.restful;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.JSONP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.activityfact.OpenHubActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.ActivityFactService;

/**
 * 
 * @author leandro.ferreira
 *
 */
@Component
@Path("/activityFact")
public class ActivityFactChartResource implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4894081803064618522L;
	
	@Autowired
	private ActivityFactService activityFactService;
	
	public ActivityFactService getActivityFactService() {
		return activityFactService;
	}
	
	public void setActivityFactService(
			ActivityFactService activityFactService) {
		this.activityFactService = activityFactService;
	}
	
	/**
	 * 
	 * @param projectIdParam
	 * @return
	 */
	protected List<OpenHubActivityFactEntity> findAllActivityFactsByProject(String projectIdParam) {
		List<OpenHubActivityFactEntity> activityFactList = null;
		if (! StringUtils.isEmpty(projectIdParam)) {
			Long projectId = Long.valueOf(projectIdParam);
			
			if (projectId != null) {
				OpenHubProjectEntity project = new OpenHubProjectEntity();
				project.setId(projectId);
				
				activityFactList = getActivityFactService().findByProject(project);
			}
		}
		
		return activityFactList;
	}
	
	/**
	 * 
	 * @param projectIdParam
	 * @return
	 * @throws JSONException 
	 */
	@GET @Path(value="/{projectId: [0-9]*}/code/chartData")
	@JSONP
	@Produces("application/json")
	public String produceCodeChartData(
			@PathParam("projectId") String projectIdParam) throws JSONException {
		List<OpenHubActivityFactEntity> activityFactList = findAllActivityFactsByProject(projectIdParam);
		OpenHubActivityFactEntity first = null;
		
		List<Long> codeList = new ArrayList<>();
		Long totalCode = 0L;
		
		List<Long> commentsList = new ArrayList<>();
		Long totalComments = 0L;
		
		List<Long> blanksList = new ArrayList<>();
		Long totalBlanks = 0L;
		if (activityFactList != null && ! activityFactList.isEmpty()) {
			first = activityFactList.get(0);
			
			for (OpenHubActivityFactEntity activityFact : activityFactList) {				
				totalCode += activityFact.getCodeAdded() - activityFact.getCodeRemoved();
				codeList.add(totalCode);
				
				totalComments += activityFact.getCommentsAdded() - activityFact.getCommentsRemoved();
				commentsList.add(totalComments);
				
				totalBlanks += activityFact.getBlanksAdded() - activityFact.getBlanksRemoved();
				blanksList.add(totalBlanks);
			}			
		}
		
		JSONObject jsonObject = new JSONObject();
		if (first != null) {
			jsonObject.put("pointStart", first.getMonth().getTime());
		}
		
		jsonObject.put("codeData", new JSONArray(codeList));
		jsonObject.put("commentsData", new JSONArray(commentsList));
		jsonObject.put("blanksData", new JSONArray(blanksList));
				
		return jsonObject.toString();		
	}
	
	/**
	 * 
	 * @param projectIdParam
	 * @return
	 * @throws JSONException 
	 */
	@GET @Path(value="/{projectId: [0-9]*}/commits/chartData")
	@JSONP
	@Produces("application/json")
	public String produceCommitsChartData(
			@PathParam("projectId") String projectIdParam) throws JSONException {
		
		List<OpenHubActivityFactEntity> activityFactList = findAllActivityFactsByProject(projectIdParam);
		OpenHubActivityFactEntity first = null;
		
		List<Long> commitsList = new ArrayList<>();
		if (activityFactList != null && ! activityFactList.isEmpty()) {
			first = activityFactList.get(0);
			for (OpenHubActivityFactEntity activityFact : activityFactList) {
				commitsList.add(activityFact.getCommits());
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		if (first != null) {
			jsonObject.put("pointStart", first.getMonth().getTime());
		}
		
		jsonObject.put("data", new JSONArray(commitsList));
		
		return jsonObject.toString();
	}
	
	@GET @Path(value="/{projectId: [0-9]*}/contributors/chartData")
	@JSONP
	@Produces("application/json")
	public String produceContributorsChartData(
			@PathParam("projectId") String projectIdParam) throws JSONException {
		List<OpenHubActivityFactEntity> activityFactList = findAllActivityFactsByProject(projectIdParam);
		OpenHubActivityFactEntity first = null;
		
		List<Long> contributorsList = new ArrayList<>();
		if (activityFactList != null && ! activityFactList.isEmpty()) {
			first = activityFactList.get(0);
			for (OpenHubActivityFactEntity activityFact : activityFactList) {
				contributorsList.add(activityFact.getContributors());
			}
		}
		
		JSONObject jsonObject = new JSONObject();
		if (first != null) {
			jsonObject.put("pointStart", first.getMonth().getTime());
		}
		
		jsonObject.put("data", new JSONArray(contributorsList));
		
		return jsonObject.toString();
	}
	

}
