package br.ufba.dcc.mestrado.computacao.service.basic;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.user.UserEntity;

public interface RepositoryBasedUserDetailsService extends UserDetailsService {

	UserEntity loadFullLoggedUser();
	
	Collection<? extends GrantedAuthority> getAuthorities(UserEntity account);
	
}