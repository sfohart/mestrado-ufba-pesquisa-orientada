
package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubTagEntity;
import br.ufba.dcc.mestrado.computacao.service.base.TagService;

@ManagedBean(name="tagMB")
@ViewScoped
public class TagManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3127510040868054311L;

	@ManagedProperty("#{tagService}")
	private TagService tagService;
	
	private List<OpenHubTagEntity> tagList;
	
	private Integer startPosition;
	private Integer offset;
	private Integer totalTags;
	
	public TagManagedBean() {
	}
	
	
	
	public List<OpenHubTagEntity> getTagList() {
		return this.tagList;
	}
	
	public void setTagList(List<OpenHubTagEntity> tagList) {
		this.tagList = tagList;
	}
	
	public TagService getTagService() {
		return this.tagService;
	}
	
	public void setTagService(TagService TagService) {
		this.tagService = TagService;
	}
	
	public Integer getStartPosition() {
		return this.startPosition;
	}
	
	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}
	
	public Integer getOffset() {
		return this.offset;
	}
	
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	
	public Integer getTotalTags() {
		return this.totalTags;
	}
	
	public void setTotalTags(Integer totalTags) {
		this.totalTags = totalTags;
	}
	
	public void initList(ComponentSystemEvent event) {
		if (tagList == null) {
			this.tagList = new ArrayList<OpenHubTagEntity>();
			this.startPosition = 0;
			this.offset = 10;
			this.totalTags = 0;
			
			searchTags();
		}
	}
	
	public void init(ComponentSystemEvent event) {
		if (tagList == null) {
			this.tagList = getTagService().findAll();
		}
	}
	
	public void moreTags(ActionEvent event) {
		if (tagList != null) {
			startPosition = tagList.size();
		}
	
		searchTags();
	}
	
	public void searchTags(ActionEvent event) {
		searchTags();
	}
	
	protected void searchTags() {
		List<OpenHubTagEntity> data = getTagService().findAll(startPosition, offset);
		
		if (getTagList() == null) {
			setTagList(new ArrayList<OpenHubTagEntity>());
		}
		
		if (data != null) {
			for (OpenHubTagEntity tag : data) {
				if (getTagList().contains(tag)) {
					getTagList().remove(tag);
				}
				
				getTagList().add(tag);
			}
		}
		
		//Se não houver mais o que carregar, atualize logo o startPosition, pro botão do scroll não aparecer a toa
		if (getTagList() != null && getTagList().size() >= totalTags) {
			startPosition = getTagList().size();
		}
	}


}