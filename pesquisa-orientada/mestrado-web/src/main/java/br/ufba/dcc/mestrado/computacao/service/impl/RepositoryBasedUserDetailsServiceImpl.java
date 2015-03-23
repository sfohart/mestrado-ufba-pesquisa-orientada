package br.ufba.dcc.mestrado.computacao.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.RoleEnum;
import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;
import br.ufba.dcc.mestrado.computacao.service.basic.RepositoryBasedUserDetailsService;

@Service(RepositoryBasedUserDetailsServiceImpl.BEAN_NAME)
@Transactional(readOnly = true)
public class RepositoryBasedUserDetailsServiceImpl implements RepositoryBasedUserDetailsService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8377919960510633469L;

	@Autowired
	private UserService userService;
	
	public static final String BEAN_NAME =  "repositoryBasedUserDetailsService";
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public UserEntity loadFullLoggedUser() {
		UserEntity account = null;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null) {
			account = getUserService().findByLogin(authentication.getName()); 
			
			if (account == null) {
				account = getUserService().findBySocialLogin(authentication.getName());
			}
		}
		
		return account;
	}

	/**
	 * 
	 */
	@Override	
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UserEntity account = getUserService().findByLogin(login);
		
		if (account == null) {
			account = getUserService().findBySocialLogin(login);
		}
		
		if (account != null) {
			boolean enabled = (account.getEnabled() != null ? account.getEnabled().booleanValue() : true);			
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			
			boolean accountNonLocked = (account.getLocked() != null ? ! account.getLocked() : true);
			
			String password = StringUtils.isEmpty(account.getPassword()) ? null : account.getPassword().toLowerCase();
			
			UserDetails userDetails = new User(
				login,
				password,
				enabled,
				accountNonExpired,
				credentialsNonExpired,
				accountNonLocked,
				getAuthorities(account)
			);
			
			return userDetails;
		} else {
			throw new UsernameNotFoundException("Este usuário não existe no sistema");
		}
	}
	
	/**
	 * 
	 * @param account
	 * @return
	 */
	public Collection<GrantedAuthority> getAuthorities(UserEntity account) {
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
