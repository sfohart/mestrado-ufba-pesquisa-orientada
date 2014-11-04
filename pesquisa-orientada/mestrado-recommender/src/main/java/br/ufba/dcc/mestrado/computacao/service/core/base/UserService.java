package br.ufba.dcc.mestrado.computacao.service.core.base;

import br.ufba.dcc.mestrado.computacao.entities.ohloh.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.base.BaseService;

public interface UserService extends BaseService<Long, UserEntity> {

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
