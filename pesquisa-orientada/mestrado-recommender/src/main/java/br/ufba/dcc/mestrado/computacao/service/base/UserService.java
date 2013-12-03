package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface UserService extends BaseOhLohService<Long, UserEntity> {

	/**
	 * 
	 * @param login
	 * @return
	 */
	UserEntity findByLogin(String login);
	
	/**
	 * 
	 * @param socialUsername
	 * @return
	 */
	UserEntity findBySocialLogin(String socialUsername);
	
}
