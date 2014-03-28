package br.ufba.dcc.mestrado.computacao.web.managedbean;

import br.ufba.dcc.mestrado.computacao.service.base.OhLohTagService;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.project.OhLohTagEntity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ComponentSystemEvent;

import javax.faces.event.ActionEvent;

@ManagedBean(name="tagMB")
@ViewScoped
public class TagManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3127510040868054311L;

	@ManagedProperty("#{ohLohTagService}")
	private OhLohTagService ohLohTagService;
	
	private List<OhLohTagEntity> tagList;
	
	private Integer startPosition;
	private Integer offset;
	private Integer totalTags;
	
	public TagManagedBean() {
	}
	
	
	
	public List<OhLohTagEntity> getTagList() {
		return this.tagList;
	}
	
	public void setTagList(List<OhLohTagEntity> tagList) {
		this.tagList = tagList;
	}
	
	public OhLohTagService getOhLohTagService() {
		return this.ohLohTagService;
	}
	
	public void setOhLohTagService(OhLohTagService ohLohTagService) {
		this.ohLohTagService = ohLohTagService;
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
			this.tagList = new ArrayList<OhLohTagEntity>();
			this.startPosition = 0;
			this.offset = 10;
			this.totalTags = 0;
			
			searchTags();
		}
	}
	
	public void init(ComponentSystemEvent event) {
		if (tagList == null) {
			this.tagList = getOhLohTagService().findAll();
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
		List<OhLohTagEntity> data = getOhLohTagService().findAll(startPosition, offset);
		
		if (getTagList() == null) {
			setTagList(new ArrayList<OhLohTagEntity>());
		}
		
		if (data != null) {
			for (OhLohTagEntity tag : data) {
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