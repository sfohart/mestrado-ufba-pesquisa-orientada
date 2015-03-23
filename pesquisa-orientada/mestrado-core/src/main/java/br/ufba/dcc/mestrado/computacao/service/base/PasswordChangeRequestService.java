package br.ufba.dcc.mestrado.computacao.service.base;

import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.PasswordChangeRequestEntity;
import br.ufba.dcc.mestrado.computacao.entities.openhub.recommender.user.UserEntity;

public interface PasswordChangeRequestService extends BaseService<Long, PasswordChangeRequestEntity> {
	
	public PasswordChangeRequestEntity findByToken(String token);
	
	public void sendMailToUser(String email);
	
	public boolean validateRequest(String email);
	
	public void changeUserPassword(UserEntity user);
	
}
