
package br.ufba.dcc.mestrado.computacao.restful;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.JSONP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.service.base.TagService;

@Component
@Path("/tags")
public class TagResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5518910928445631027L;
	
	@Autowired
	private TagService tagService;
	
	public TagService getTagService() {
		return tagService;
	}
	
	public void setTagService(TagService tagService) {
		this.tagService = tagService;
	}
	
	
	@GET @Path(value="/findTags/{query}")
	@JSONP
	@Produces("application/json")
	public String produceTagList(@PathParam("query") String query) throws JSONException {
		List<OpenHubTagEntity> result = getTagService().findTagListByName(query);
		
		JSONObject jsonObject = new JSONObject();
		if (result != null) {
			JSONArray jsonArray = new JSONArray();
			
			for (OpenHubTagEntity tag : result) {
				JSONObject jsonTag = new JSONObject();
				jsonTag.put("id", tag.getId());
				jsonTag.put("name", tag.getName());
				
				jsonArray.put(jsonTag);
			}
			
			jsonObject.put("size", result.size());
			jsonObject.put("data", jsonArray);
		}
		
		return jsonObject.toString(4);
	}

}

