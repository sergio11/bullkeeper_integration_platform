package sanchez.sanchez.sergio.bullkeeper.rrss.service.impl;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

import sanchez.sanchez.sergio.bullkeeper.exception.GetInformationFromGoogleException;
import sanchez.sanchez.sergio.bullkeeper.mapper.GoogleMapper;
import sanchez.sanchez.sergio.bullkeeper.rrss.service.IGoogleService;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByGoogleDTO;

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
	public RegisterGuardianByGoogleDTO getUserInfo(String accessToken) {
		
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

	@Override
	public String getUserNameForAccessToken(String accessToken) throws IOException {
		Assert.notNull(accessToken, "Token can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");
		
        Oauth2 oauth2 = appCtx.getBean(Oauth2.class, accessToken);
		Userinfoplus userInfo = oauth2.userinfo().v2().me().get().execute();
		return userInfo.getName();
	}

	@Override
	public String getUserImage(String accessToken) {
		Assert.notNull(accessToken, "Token can not be null");
        Assert.hasLength(accessToken, "Token can not be empty");
        String userImage = null;
        try {
        	Oauth2 oauth2 = appCtx.getBean(Oauth2.class, accessToken);
			Userinfoplus userInfo = oauth2.userinfo().v2().me().get().execute();
			userImage = userInfo.getPicture();
        } catch (Exception e) {
        	logger.debug(e.toString());
        }
        return userImage;
	}

}
