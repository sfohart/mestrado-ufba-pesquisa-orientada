package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;

public interface UserRepository extends BaseRepository<Long, UserEntity>{

	UserEntity findByLogin(String login);
	
}
