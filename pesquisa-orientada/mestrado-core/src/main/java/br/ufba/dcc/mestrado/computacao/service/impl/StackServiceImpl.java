package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.stack.OpenHubStackEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.StackRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.StackRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.ProjectService;
import br.ufba.dcc.mestrado.computacao.service.base.StackService;

@Service(StackServiceImpl.BEAN_NAME)
public class StackServiceImpl extends BaseServiceImpl<Long, OpenHubStackEntity>
		implements StackService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2776699453019711606L;
	
	public static final String BEAN_NAME =  "stackService";

	protected static Logger logger = Logger.getLogger(StackServiceImpl.class.getName());

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	public StackServiceImpl(@Qualifier(StackRepositoryImpl.BEAN_NAME) StackRepository repository) {
		super(repository, OpenHubStackEntity.class);
	}
	
}
