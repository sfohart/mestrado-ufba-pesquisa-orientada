package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;
import br.ufba.dcc.mestrado.computacao.ohloh.data.account.OhLohAccountDTO;
import br.ufba.dcc.mestrado.computacao.repository.base.OhLohAccountRepository;
import br.ufba.dcc.mestrado.computacao.service.base.OhLohAccountService;

@Service(OhLohAccountServiceImpl.BEAN_NAME)
public class OhLohAccountServiceImpl extends DefaultOhLohServiceImpl<OhLohAccountDTO, Long, OhLohAccountEntity>
		implements OhLohAccountService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9064963674941414416L;

	public static final String BEAN_NAME =  "ohLohAccountService";

	@Autowired
	public OhLohAccountServiceImpl(@Qualifier("ohLohAccountRepository") OhLohAccountRepository repository) {
		super(repository, OhLohAccountDTO.class, OhLohAccountEntity.class);
	}

}
