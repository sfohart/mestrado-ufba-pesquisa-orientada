package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.openhub.core.account.OpenHubAccountEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.AccountRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.AccountRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.AccountService;

@Service(AccountServiceImpl.BEAN_NAME)
public class AccountServiceImpl extends BaseServiceImpl<Long, OpenHubAccountEntity>
		implements AccountService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9064963674941414416L;

	public static final String BEAN_NAME =  "accountService";

	@Autowired
	public AccountServiceImpl(@Qualifier(AccountRepositoryImpl.BEAN_NAME) AccountRepository repository) {
		super(repository, OpenHubAccountEntity.class);
	}
	
	public OpenHubAccountEntity findByLogin(String login) {
		return ((AccountRepository) getRepository()).findByLogin(login);
	}
	
}
