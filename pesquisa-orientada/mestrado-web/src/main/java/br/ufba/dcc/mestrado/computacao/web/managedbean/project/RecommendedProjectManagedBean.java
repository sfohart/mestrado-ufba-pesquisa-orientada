package br.ufba.dcc.mestrado.computacao.web.managedbean.project;

import javax.el.MethodExpression;
import javax.faces.context.FacesContext;

public abstract class RecommendedProjectManagedBean extends ProjectManagedBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4427973769068757558L;
	
	protected MethodExpression createMethodExpression(
			String expression, Class<?> expectedReturnType, 
			Class<?>... expectedParameterTypes) {
		
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    return facesContext.getApplication()
	    		.getExpressionFactory()
	    		.createMethodExpression(
	    				facesContext.getELContext(), 
	    				expression, 
	    				expectedReturnType, 
	    				expectedParameterTypes
	    			);	    
	}
	
	public abstract MethodExpression getEvaluationAction();

}
