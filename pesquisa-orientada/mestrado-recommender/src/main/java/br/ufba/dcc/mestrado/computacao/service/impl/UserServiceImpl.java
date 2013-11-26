package br.ufba.dcc.mestrado.computacao.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.repository.base.UserRepository;
import br.ufba.dcc.mestrado.computacao.service.base.UserService;

@Service(UserServiceImpl.BEAN_NAME)
public class UserServiceImpl extends BaseOhLohServiceImpl<Long, UserEntity>
		implements UserService {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4249165761507596580L;
	
	public static final String BEAN_NAME =  "userService";

	@Autowired
	public UserServiceImpl(@Qualifier("userRepository") UserRepository repository) {
		super(repository,  UserEntity.class);
	}
	
	@Transactional
	@Override
	public UserEntity findByLogin(String login) {
		return ((UserRepository)  getRepository()).findByLogin(login);
	}

	
	@Override
	@Transactional
	public UserEntity save(UserEntity entity) throws Exception {
		validateEntity(entity);
		return super.save(entity);
	}

	private void validateEntity(UserEntity entity) {
		entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
	}
}
