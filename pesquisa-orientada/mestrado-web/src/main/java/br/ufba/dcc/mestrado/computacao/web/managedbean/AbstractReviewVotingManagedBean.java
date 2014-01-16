package br.ufba.dcc.mestrado.computacao.web.managedbean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

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

	public void addUsefulVoteToReview(AjaxBehaviorEvent event) {
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
					
					try {
						getPreferenceService().save(preference);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					
				}
			}
		}
	}
	
	public void addUselessVoteToReview(AjaxBehaviorEvent event) {
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
					
					try {
						getPreferenceService().save(preference);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					
				}
			}
		}
	}

	protected boolean isDulicatedVoteMessage(AjaxBehaviorEvent event, PreferenceEntity preference) {
		boolean duplicated = false;
		
		UserEntity user = getUserDetailsService().loadFullLoggedUser();
		
		if (preference.getPreferenceReview().getUselessList().contains(user)) {
			duplicated = true;
		} else if (preference.getPreferenceReview().getUsefulList().contains(user)) {
			duplicated = true;
		}
		
		if (duplicated) {
			ResourceBundle bundle = ResourceBundle.getBundle("br.ufba.dcc.mestrado.computacao.reviews");
			
			String message = bundle.getString("reviews.vote.duplicated.message");
			FacesMessage facesMessage = new FacesMessage(message);
			
			FacesContext.getCurrentInstance().addMessage(event.getComponent().getId(), facesMessage);
		}
		
		return duplicated;
	}

}
