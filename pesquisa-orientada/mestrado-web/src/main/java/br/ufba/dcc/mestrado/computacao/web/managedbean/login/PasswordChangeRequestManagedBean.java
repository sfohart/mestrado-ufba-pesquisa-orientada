package br.ufba.dcc.mestrado.computacao.web.managedbean.login;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;


@ManagedBean(name="passwordChangeRequestMB")
@ViewScoped
@URLMappings(mappings={
	@URLMapping(
			id="passwordChangeRequestMapping",
			beanName="passwordChangeRequestMB", 
			pattern="/passwordChangeRequest",
			viewId="/login/passwordChangeRequest.jsf")
})
public class PasswordChangeRequestManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7158037795098490742L;

}
