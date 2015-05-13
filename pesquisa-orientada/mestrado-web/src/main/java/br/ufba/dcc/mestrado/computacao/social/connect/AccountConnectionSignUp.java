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
import org.springframework.social.google.api.Google;
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
		
		ApiBinding apiBinding = (ApiBinding) connection.getApi();
		UserEntity user = null;
		
		Facebook facebook = null;
		Twitter twitter = null;
		Google google = null;
		
		String username = null;
		
		if (apiBinding instanceof Facebook) {
			facebook = (Facebook) apiBinding;
			
			if (! StringUtils.isEmpty(userProfile.getEmail())) {
				user = getUserService().findByEmail(userProfile.getEmail());
			}
			
		} else if (apiBinding instanceof Twitter) {
			twitter = (Twitter) apiBinding;
			
			if (! StringUtils.isEmpty(userProfile.getUsername())) {
				user = getUserService().findByLogin(userProfile.getUsername());
			}
		} else if (apiBinding instanceof Google) {
			google = (Google) apiBinding;
			
			if (! StringUtils.isEmpty(userProfile.getEmail())) {
				user = getUserService().findByEmail(userProfile.getEmail());
			}
		}
		
		if (! StringUtils.isEmpty(userProfile.getEmail()) || ! StringUtils.isEmpty(userProfile.getUsername())) {
			
			if (user == null) {
				user = new UserEntity();
				
				List<RoleEnum> roleList = new ArrayList<>();
				roleList.add(RoleEnum.ROLE_USER);
				
				user.setRoleList(roleList);
			}
			
			if (facebook != null) {
				user.setFacebookAccount(facebook.userOperations().getUserProfile().getLink());
			}
			
			if (twitter != null) {
				user.setTwitterAccount(twitter.userOperations().getUserProfile().getProfileUrl());
			}
			
			if (google != null) {
				user.setGooglePlusAccount(google.plusOperations().getGoogleProfile().getUrl());
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
			
			if (StringUtils.isEmpty(user.getLogin())) {
				if (! StringUtils.isEmpty(userProfile.getUsername())) {
					user.setLogin(userProfile.getUsername());
				} else {
					user.setLogin(userProfile.getEmail());
				}
			}
			
			username = user.getLogin();
			
			try {
				getUserService().save(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return username;
		
	}

}
