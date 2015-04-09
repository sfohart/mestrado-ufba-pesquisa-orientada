package br.ufba.dcc.mestrado.computacao.repository.base;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.PasswordChangeRequestEntity;

public interface PasswordChangeRequestRepository extends BaseRepository<Long, PasswordChangeRequestEntity>{

	public PasswordChangeRequestEntity findByToken(String token);
	
}
