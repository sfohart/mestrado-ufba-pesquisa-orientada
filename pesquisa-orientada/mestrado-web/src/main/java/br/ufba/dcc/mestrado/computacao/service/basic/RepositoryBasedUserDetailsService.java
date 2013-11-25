package br.ufba.dcc.mestrado.computacao.service.basic;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.account.OhLohAccountEntity;

public interface RepositoryBasedUserDetailsService extends UserDetailsService {

	OhLohAccountEntity loadFullLoggedUser();
	
}
