package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.ufba.dcc.mestrado.computacao.service.base.OhLohProjectService;

@ManagedBean(name="indexMB")
@ViewScoped
public class IndexManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1409944916651513725L;
	
	@ManagedProperty("#{ohLohProjectService}")
	private OhLohProjectService ohLohProjectService;
		
	public IndexManagedBean() {
		
	}

	public OhLohProjectService getOhLohProjectService() {
		return ohLohProjectService;
	}

	public void setOhLohProjectService(OhLohProjectService ohLohProjectService) {
		this.ohLohProjectService = ohLohProjectService;
	}
	
	public Long getProjectCount() {
		return getOhLohProjectService().countAll();
	}
	
	
	
}
