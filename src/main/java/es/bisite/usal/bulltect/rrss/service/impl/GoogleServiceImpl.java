package es.bisite.usal.bulltect.rrss.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.exception.FacebookOAuthException;
import com.restfb.types.User;

import es.bisite.usal.bulltect.mapper.GoogleMapper;
import es.bisite.usal.bulltect.rrss.service.IGoogleService;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByGoogleDTO;
import es.bisite.usal.bulltect.web.rest.exception.GetInformationFromFacebookException;
import es.bisite.usal.bulltect.web.rest.exception.GetInformationFromGoogleException;

@Service
@Profile({"dev", "prod"})
public class GoogleServiceImpl implements IGoogleService {
	
	private Logger logger = LoggerFactory.getLogger(GoogleServiceImpl.class);
	
	private final ApplicationContext appCtx;
	private final GoogleMapper googleMapper;
	
	@Autowired
	public GoogleServiceImpl(ApplicationContext appCtx, GoogleMapper googleMapper){
		this.appCtx = appCtx;
		this.googleMapper = googleMapper;
	}

	@Override
	public RegisterParentByGoogleDTO getUserInfo(String accessToken) {
		
		try {
			Oauth2 oauth2 = appCtx.getBean(Oauth2.class, accessToken);
			Userinfoplus userInfo = oauth2.userinfo().v2().me().get().execute();
		
			
			return googleMapper.userInfoPlusToRegisterParentByGoogleDTO(userInfo);
			
		} catch (Exception e) {
			throw new GetInformationFromGoogleException();
		}
	}

	@Override
	public String getGoogleIdByAccessToken(String accessToken) {
		Assert.notNull(accessToken, "Token can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");

       
        try {
        	Oauth2 oauth2 = appCtx.getBean(Oauth2.class, accessToken);
			Userinfoplus userInfo = oauth2.userinfo().v2().me().get().execute();
            return userInfo.getId();
        } catch (Exception e) {
            throw new GetInformationFromGoogleException();
        }
        
	}

}
