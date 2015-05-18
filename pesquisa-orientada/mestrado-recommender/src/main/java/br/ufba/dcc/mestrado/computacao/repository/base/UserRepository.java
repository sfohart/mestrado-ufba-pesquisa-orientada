package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface UserRepository extends BaseRepository<Long, UserEntity>{

	/**
	 * 
	 * @param login
	 * @return
	 */
	UserEntity findByLogin(String login);
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	UserEntity findByEmail(String email);
	
	/**
	 * 
	 * @param socialUsername
	 * @return
	 */
	UserEntity findBySocialLogin(String socialUsername);
	
}
