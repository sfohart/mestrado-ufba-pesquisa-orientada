package br.ufba.dcc.mestrado.computacao.service.impl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import br.ufba.dcc.mestrado.computacao.service.basic.SpringSecuritySignInAdapter;

@Service("springSecuritySignInAdapter")
public class SpringSecuritySignInAdapterImpl implements SpringSecuritySignInAdapter {

	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		SecurityContextHolder.getContext().setAuthentication(
				 new UsernamePasswordAuthenticationToken(userId, null, null));
		 return null;
	}

}
