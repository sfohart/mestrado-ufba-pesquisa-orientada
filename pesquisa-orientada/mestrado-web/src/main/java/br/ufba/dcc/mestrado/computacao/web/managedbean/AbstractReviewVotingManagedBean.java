package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.ufba.dcc.mestrado.computacao.entities.recommender.preference.PreferenceEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.OverallPreferenceService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

public abstract class AbstractReviewVotingManagedBean implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8314180469728867890L;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{overallPreferenceService}")
	private OverallPreferenceService preferenceService;

	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public OverallPreferenceService getPreferenceService() {
		return preferenceService;
	}

	public void setPreferenceService(OverallPreferenceService preferenceService) {
		this.preferenceService = preferenceService;
	}

	protected PreferenceEntity addUsefulVoteToReview(ActionEvent event) {
		PreferenceEntity preference = (PreferenceEntity)
				event.getComponent().getAttributes().get("preference");
		
		if (preference != null && preference.getId() != null) {
			
			preference = getPreferenceService().findById(preference.getId());
			
			if (preference != null && preference.getPreferenceReview() != null) {
				UserEntity user = getUserDetailsService().loadFullLoggedUser();
				
				if (preference.getPreferenceReview().getUsefulList() == null) {
					preference.getPreferenceReview().setUsefulList(new HashSet<UserEntity>());
				}
				
				if (! isDulicatedVoteMessage(event, preference)) {
					preference.getPreferenceReview().getUsefulList().add(user);
				} else {
					preference.getPreferenceReview().getUsefulList().remove(user);
				}
				
				try {
					return getPreferenceService().save(preference);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return preference;
	}
	
	protected PreferenceEntity addUselessVoteToReview(ActionEvent event) {
		PreferenceEntity preference = (PreferenceEntity)
				event.getComponent().getAttributes().get("preference");
		
		if (preference != null && preference.getId() != null) {
			
			preference = getPreferenceService().findById(preference.getId());
			
			if (preference != null && preference.getPreferenceReview() != null) {
				UserEntity user = getUserDetailsService().loadFullLoggedUser();
				
				if (preference.getPreferenceReview().getUselessList() == null) {
					preference.getPreferenceReview().setUselessList(new HashSet<UserEntity>());
				}
				
				if (! isDulicatedVoteMessage(event, preference)) {
					preference.getPreferenceReview().getUselessList().add(user);
				} else {
					preference.getPreferenceReview().getUselessList().remove(user);
				}
				
				
				try {
					return getPreferenceService().save(preference);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return preference;
	}

	protected boolean isDulicatedVoteMessage(ActionEvent event, PreferenceEntity preference) {
		boolean duplicated = false;
		
		UserEntity user = getUserDetailsService().loadFullLoggedUser();
		
		if (preference.getPreferenceReview().getUselessList().contains(user)) {
			duplicated = true;
		} else if (preference.getPreferenceReview().getUsefulList().contains(user)) {
			duplicated = true;
		}
		
		return duplicated;
	}

}
