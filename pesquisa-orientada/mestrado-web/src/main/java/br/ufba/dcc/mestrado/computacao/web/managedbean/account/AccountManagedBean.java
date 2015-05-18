
package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.project.OpenHubProjectEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.RoleEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name="accountMB")
@ViewScoped
@URLMappings(mappings={
	@URLMapping(
			id="accountMapping",
			beanName="accountMB", 
			pattern="/account/#{ /[0-9]+/ accountId}/",
			viewId="/account/account.jsf"),
	
	@URLMapping(
			id="newAccountMapping",
			beanName="accountMB", 
			pattern="/account/new",
			viewId="/account/newAccount.jsf")
})
public class AccountManagedBean extends AbstractAccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4801276509767530348L;
	
	
	private Long totalViewedProjects;
	private Long totalRatedProjects;
	private Long totalCommentedProjects;

	public void init(ComponentSystemEvent event) {
		super.init(event);
		
		if (getAccount() != null && getAccount().getId() != null) {
			loadViewedProjects();
			loadRatedProjects();
			loadCommentedProjects();
		}
	}
	
	protected void loadViewedProjects() {
		this.totalViewedProjects = getPageViewService().countAllProjectRecentlyViewedByUser(getAccount());
	}
	
	protected void loadRatedProjects() {
		this.totalRatedProjects = getOverallRatingService().countAllLastPreferenceByUser(getAccount().getId());
	}
	
	protected void loadCommentedProjects() {
		this.totalCommentedProjects = getPreferenceReviewService().countAllLastReviewsByUser(getAccount().getId());
	}
	
	
	public String createAccount() {
		
		String encodedPassword = getPasswordEncoder().encode(getAccount().getPassword());
		getAccount().setPassword(encodedPassword);
		
		List<RoleEnum> roleList = new ArrayList<>();
		roleList.add(RoleEnum.ROLE_USER);
		
		getAccount().setRoleList(roleList);
		
		try {
			getUserService().save(getAccount());			
			setAccount(new UserEntity());
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Conta criada com sucesso", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
			
		} catch (Exception e) {
			FacesMessage message = new FacesMessage("Ocorreu um erro durante esta operação", e.getLocalizedMessage());
			FacesContext.getCurrentInstance().addMessage(null, message);
			e.printStackTrace();
			return "";
		}
		
		
		return "/login/login.jsf";
	}

	public Long getTotalViewedProjects() {
		return totalViewedProjects;
	}

	public Long getTotalRatedProjects() {
		return totalRatedProjects;
	}
	
	public Long getTotalCommentedProjects() {
		return totalCommentedProjects;
	}
}

