package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedSocialUserDetailsService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

@Service(RepositoryBasedSocialUserDetailsServiceImpl.BEAN_NAME)
public class RepositoryBasedSocialUserDetailsServiceImpl implements RepositoryBasedSocialUserDetailsService {

	public static final String BEAN_NAME =  "repositoryBasedSocialUserDetailsService";

	@Autowired
	private RepositoryBasedUserDetailsService userDetailsService;
	
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		UserDetails userDetails = userDetailsService.loadUserByUsername(userId); 
		return (SocialUserDetails) userDetails;
	}
	
}
