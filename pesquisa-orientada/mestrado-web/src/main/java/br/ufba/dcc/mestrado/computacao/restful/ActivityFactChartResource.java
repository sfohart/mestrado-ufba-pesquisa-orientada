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
import org.glassfish.jersey.server.JSONP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.activityfact.OhLohActivityFactEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohProjectEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohActivityFactService;

@Component
@Path("/activityFact")
public class ActivityFactChartResource implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4894081803064618522L;
	
	@Autowired
	private OhLohActivityFactService activityFactService;
	
	public OhLohActivityFactService getActivityFactService() {
		return activityFactService;
	}
	
	public void setActivityFactService(
			OhLohActivityFactService activityFactService) {
		this.activityFactService = activityFactService;
	}
	
	
	protected List<OhLohActivityFactEntity> findAllActivityFactsByProject(String projectIdParam) {
		List<OhLohActivityFactEntity> activityFactList = null;
		if (! StringUtils.isEmpty(projectIdParam)) {
			Long projectId = Long.valueOf(projectIdParam);
			
			if (projectId != null) {
				OhLohProjectEntity project = new OhLohProjectEntity();
				project.setId(projectId);
				
				activityFactList = getActivityFactService().findByProject(project);
			}
		}
		
		return activityFactList;
	}
	
	@GET @Path(value="/{projectId: [0-9]*}/commits/chartData")
	@JSONP
	@Produces("application/json")
	public String produceCommitsChartData(
			@PathParam("projectId") String projectIdParam) {
		
		List<OhLohActivityFactEntity> activityFactList = findAllActivityFactsByProject(projectIdParam);
		
		List<Long> commitsList = new ArrayList<>();
		if (activityFactList != null) {
			for (OhLohActivityFactEntity activityFact : activityFactList) {
				commitsList.add(activityFact.getCommits());
			}
		}
		
		JSONArray jsonArray = new JSONArray(commitsList);
		return jsonArray.toString();
	}
	
	@GET @Path(value="/{projectId: [0-9]*}/codeAdded/chartData")
	@JSONP
	@Produces("application/json")
	public String produceCodeAddedChartData(
			@PathParam("projectId") String projectIdParam) {
		List<OhLohActivityFactEntity> activityFactList = findAllActivityFactsByProject(projectIdParam);
		
		List<Long> codeAddedList = new ArrayList<>();
		if (activityFactList != null) {
			for (OhLohActivityFactEntity activityFact : activityFactList) {
				codeAddedList.add(activityFact.getCodeAdded());
			}
		}
		
		JSONArray jsonArray = new JSONArray(codeAddedList);
		return jsonArray.toString();
	}

}
