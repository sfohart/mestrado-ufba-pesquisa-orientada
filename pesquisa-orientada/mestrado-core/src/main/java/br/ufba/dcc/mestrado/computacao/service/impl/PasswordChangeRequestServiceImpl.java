package br.ufba.dcc.mestrado.computacao.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.PasswordChangeRequestEntity;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.PasswordChangeRequestRepository;
import br.ufba.dcc.mestrado.computacao.repository.impl.PasswordChangeRequestRepositoryImpl;
import br.ufba.dcc.mestrado.computacao.service.base.PasswordChangeRequestService;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;


@Service(PasswordChangeRequestServiceImpl.BEAN_NAME)
public class PasswordChangeRequestServiceImpl 
		extends BaseServiceImpl<Long, PasswordChangeRequestEntity> 
		implements PasswordChangeRequestService{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7990174766442595212L;
	
	public static final String BEAN_NAME =  "passwordChangeRequestService";
	
	@Autowired
	private UserService UserService;
	
	@Autowired
	public PasswordChangeRequestServiceImpl(@Qualifier(PasswordChangeRequestRepositoryImpl.BEAN_NAME) PasswordChangeRequestRepository repository) {
		super(repository, PasswordChangeRequestEntity.class);
	}
	
	public PasswordChangeRequestEntity findByToken(String token) {
		return ((PasswordChangeRequestRepository) getRepository()).findByToken(token);
	}
	

	@Override
	public void sendMailToUser(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validateRequest(String token) {
		PasswordChangeRequestEntity passwordChangeRequest = findByToken(token);
		
		if (passwordChangeRequest != null) {
			Timestamp today = new Timestamp(System.currentTimeMillis());
			
			if (passwordChangeRequest.getExpirationDate().after(today)) {
				return false;
			}
		} else {
			return false;
		}
				
		return true;
	}

	@Override
	public void changeUserPassword(UserEntity user) {
		// TODO Auto-generated method stub
		
	}

	

}
