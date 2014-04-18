package br.ufba.dcc.mestrado.computacao.social.connect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.ApiBinding;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import br.ufba.dcc.mestrado.computacao.entities.recommender.user.RoleEnum;
import br.ufba.dcc.mestrado.computacao.entities.recommender.user.UserEntity;
import br.ufba.dcc.mestrado.computacao.service.core.base.UserService;

@Component
public class UserConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private UserService userService;
	
	public UserService getUserService() {
		return userService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public String execute(Connection<?> connection) {
		UserProfile userProfile = connection.fetchUserProfile();
		
		UserEntity userEntity = getUserService().findBySocialLogin(userProfile.getUsername());
		if (userEntity == null) {
			userEntity = new UserEntity();
			
			List<RoleEnum> roleList = new ArrayList<>();
			roleList.add(RoleEnum.ROLE_USER);
			
			userEntity.setRoleList(roleList);
		}
		
		ApiBinding apiBinding = (ApiBinding) connection.getApi();
		
		if (apiBinding instanceof Facebook) {
			if (StringUtils.isEmpty(userEntity.getFacebookAccount())) {
				userEntity.setFacebookAccount(userProfile.getUsername());
			}
		} else if (apiBinding instanceof Twitter) {
			if (StringUtils.isEmpty(userEntity.getTwitterAccount())) {
				userEntity.setTwitterAccount(userProfile.getUsername());
			}
		}
		
		if (StringUtils.isEmpty(userEntity.getName())) {
			userEntity.setName(userProfile.getName());
		}
		
		if (StringUtils.isEmpty(userEntity.getFirstName())) {
			userEntity.setFirstName(userProfile.getFirstName());
		}
		
		if (StringUtils.isEmpty(userEntity.getLastName())) {
			userEntity.setLastName(userProfile.getLastName());
		}
		
		if (StringUtils.isEmpty(userEntity.getEmail())) {
			userEntity.setEmail(userProfile.getEmail());
		}
		
		try {
			getUserService().save(userEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userProfile.getUsername();
	}

}
