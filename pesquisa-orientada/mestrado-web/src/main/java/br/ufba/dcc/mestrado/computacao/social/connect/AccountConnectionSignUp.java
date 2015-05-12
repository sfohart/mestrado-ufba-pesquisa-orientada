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
public class AccountConnectionSignUp implements ConnectionSignUp {

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
		
		String username = userProfile.getEmail();
		
		if (! StringUtils.isEmpty(userProfile.getEmail())) {

			UserEntity user = getUserService().findByEmail(userProfile.getEmail());
			if (user == null) {
				user = new UserEntity();
				user.setLogin(userProfile.getEmail());
				
				List<RoleEnum> roleList = new ArrayList<>();
				roleList.add(RoleEnum.ROLE_USER);
				
				user.setRoleList(roleList);
			}
			
			
			username = user.getLogin();
			ApiBinding apiBinding = (ApiBinding) connection.getApi();
			
			if (apiBinding instanceof Facebook) {
				
				Facebook facebook = (Facebook) apiBinding;
				if (StringUtils.isEmpty(user.getFacebookAccount())) {
					user.setFacebookAccount(facebook.userOperations().getUserProfile().getLink());
				}
			} else if (apiBinding instanceof Twitter) {
				
				Twitter twitter = (Twitter) apiBinding;
				
				if (StringUtils.isEmpty(user.getTwitterAccount())) {
					user.setTwitterAccount(twitter.userOperations().getUserProfile().getProfileUrl());
				}
			}
			
			if (StringUtils.isEmpty(user.getName())) {
				user.setName(userProfile.getName());
			}
			
			if (StringUtils.isEmpty(user.getFirstName())) {
				user.setFirstName(userProfile.getFirstName());
			}
			
			if (StringUtils.isEmpty(user.getLastName())) {
				user.setLastName(userProfile.getLastName());
			}
			
			if (StringUtils.isEmpty(user.getEmail())) {
				user.setEmail(userProfile.getEmail());
			}
			
			try {
				getUserService().save(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return username;
		
	}

}
