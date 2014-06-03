package br.ufba.dcc.mestrado.computacao.web.managedbean.review;

import java.io.Serializable;
import java.util.HashSet;

import javax.faces.bean.ManagedProperty;
import javax.faces.event.ActionEvent;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.preference.PreferenceReviewEntity;
import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.core.base.PreferenceReviewService;

public abstract class AbstractReviewVotingManagedBean implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8314180469728867890L;
	
	@ManagedProperty("#{repositoryBasedUserDetailsService}")
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@ManagedProperty("#{preferenceReviewService}")
	private PreferenceReviewService preferenceReviewService;

	public RepositoryBasedUserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(
			RepositoryBasedUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public PreferenceReviewService getPreferenceReviewService() {
		return preferenceReviewService;
	}

	public void setPreferenceReviewService(
			PreferenceReviewService preferenceReviewService) {
		this.preferenceReviewService = preferenceReviewService;
	}

	protected PreferenceReviewEntity addUsefulVoteToReview(ActionEvent event) {
		PreferenceReviewEntity preferenceReview = (PreferenceReviewEntity)
				event.getComponent().getAttributes().get("preference");
		
		if (preferenceReview != null && preferenceReview.getId() != null) {
			
			preferenceReview = getPreferenceReviewService().findById(preferenceReview.getId());
			
			if (preferenceReview != null ) {
				UserEntity user = getUserDetailsService().loadFullLoggedUser();
				
				if (preferenceReview.getUsefulList() == null) {
					preferenceReview.setUsefulList(new HashSet<UserEntity>());
				}
				
				if (! isDulicatedVoteMessage(event, preferenceReview)) {
					preferenceReview.getUsefulList().add(user);
				} else {
					preferenceReview.getUsefulList().remove(user);
				}
				
				try {
					getPreferenceReviewService().save(preferenceReview);
					preferenceReview = getPreferenceReviewService().findById(preferenceReview.getId());
					return preferenceReview;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return preferenceReview;
	}
	
	protected PreferenceReviewEntity addUselessVoteToReview(ActionEvent event) {
		PreferenceReviewEntity preferenceReview = (PreferenceReviewEntity)
				event.getComponent().getAttributes().get("preference");
		
		if (preferenceReview != null && preferenceReview.getId() != null) {
			
			preferenceReview = getPreferenceReviewService().findById(preferenceReview.getId());
			
			if (preferenceReview != null) {
				UserEntity user = getUserDetailsService().loadFullLoggedUser();
				
				if (preferenceReview.getUselessList() == null) {
					preferenceReview.setUselessList(new HashSet<UserEntity>());
				}
				
				if (! isDulicatedVoteMessage(event, preferenceReview)) {
					preferenceReview.getUselessList().add(user);
				} else {
					preferenceReview.getUselessList().remove(user);
				}
				
				
				try {
					getPreferenceReviewService().save(preferenceReview);
					preferenceReview = getPreferenceReviewService().findById(preferenceReview.getId());
					
					return preferenceReview;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return preferenceReview;
	}

	protected boolean isDulicatedVoteMessage(ActionEvent event, PreferenceReviewEntity preferenceReview) {
		boolean duplicated = false;
		
		UserEntity user = getUserDetailsService().loadFullLoggedUser();
		
		if (preferenceReview.getUselessList().contains(user)) {
			duplicated = true;
		} else if (preferenceReview.getUsefulList().contains(user)) {
			duplicated = true;
		}
		
		return duplicated;
	}

}
