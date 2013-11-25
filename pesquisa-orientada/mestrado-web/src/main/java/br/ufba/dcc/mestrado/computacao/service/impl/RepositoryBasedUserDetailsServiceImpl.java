package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.RoleEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRepository;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

@Service(RepositoryBasedUserDetailsServiceImpl.BEAN_NAME)
@Transactional(readOnly = true)
public class RepositoryBasedUserDetailsServiceImpl implements RepositoryBasedUserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	public static final String BEAN_NAME =  "repositoryBasedUserDetailsService";
	
	public UserRepository getUserRepository() {
		return userRepository;
	}
	
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserEntity loadFullLoggedUser() {
		UserEntity account = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null) {
			account = getUserRepository().findByLogin(authentication.getName()); 
		}
		
		return account;
	}

	/**
	 * 
	 */
	@Override	
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UserEntity account = getUserRepository().findByLogin(login);
		
		if (account != null) {
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			
			UserDetails user = new User(
				account.getLogin(),
				account.getPassword().toLowerCase(),
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				getAuthorities(account)
			);
			
			return user;
		} else {
			throw new UsernameNotFoundException("Este usuário não existe no sistema");
		}
	}
	
	/**
	 * 
	 * @param account
	 * @return
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(UserEntity account) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		
		if (account != null && account.getRoleList() != null) {
			for (RoleEnum role : account.getRoleList()) {
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.toString());
				grantedAuthorities.add(grantedAuthority);
			}
		}
		
		return grantedAuthorities;
	}

}
