package br.ufba.dcc.mestrado.computacao.web.managedbean.account;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBean(name="accountBasicDataEditMB")
@ViewScoped
@URLMappings(mappings={
	@URLMapping(
			id="accountBasicDataEditMapping",
			beanName="accountBasicDataEditMB", 
			pattern="/account/#{ /[0-9]+/ accountId}/basicDataEdit",
			viewId="/account/accountBasicDataEdit.jsf")
})
public class AccountBasicDataEditManagedBean extends AccountManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7990824770414853100L;
	
	public AccountBasicDataEditManagedBean() {
		super();
		setLoggedUserOnly(true);
	}
	

}
