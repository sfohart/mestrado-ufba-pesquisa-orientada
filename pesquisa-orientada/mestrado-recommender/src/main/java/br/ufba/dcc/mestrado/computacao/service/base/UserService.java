package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface UserService extends BaseOhLohService<Long, UserEntity> {

	UserEntity findByLogin(String login);
	
}
