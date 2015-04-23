package br.ufba.dcc.mestrado.computacao.batch.item;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

@Component
public class UserReader implements ItemReader<UserEntity> {
	
	private UserService userService;
	
	private List<UserEntity> users;
	private Iterator<UserEntity> userIterator;

	@Autowired
	public UserReader(UserService userService) {
		this.userService = userService;
		initReader();
	}
	
	
	

	private void initReader() {
		this.users = userService.findAll();
		if (this.users != null) {
			this.userIterator = users.iterator();
		}
	}


	@Override
	public UserEntity read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		
		if (userIterator.hasNext()) {
			return userIterator.next();
		}
		
		return null;
	}

}
