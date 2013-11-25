package br.ufba.dcc.mestrado.computacao.service.basic;

import org.springframework.security.core.userdetails.UserDetailsService;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface RepositoryBasedUserDetailsService extends UserDetailsService {

	UserEntity loadFullLoggedUser();
	
}
