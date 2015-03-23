package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.core.account.OpenHubAccountEntity;
import br.ufba.dcc.mestrado.computacao.openhub.data.account.OpenHubAccountDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.AccountRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.AccountRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.OpenHubAccountService;

@Service(OpenHubAccountServiceImpl.BEAN_NAME)
public class OpenHubAccountServiceImpl extends DefaultOpenHubServiceImpl<OpenHubAccountDTO, Long, OpenHubAccountEntity>
		implements OpenHubAccountService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9064963674941414416L;

	public static final String BEAN_NAME =  "ohLohAccountService";

	@Autowired
	public OpenHubAccountServiceImpl(@Qualifier(AccountRepositoryImpl.BEAN_NAME) AccountRepository repository) {
		super(repository, OpenHubAccountDTO.class, OpenHubAccountEntity.class);
	}
	
	public OpenHubAccountEntity findByLogin(String login) {
		return ((AccountRepository) getRepository()).findByLogin(login);
	}
	
}
