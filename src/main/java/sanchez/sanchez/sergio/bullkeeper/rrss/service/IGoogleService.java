package sanchez.sanchez.sergio.bullkeeper.rrss.service;

import java.io.IOException;

import sanchez.sanchez.sergio.bullkeeper.web.dto.request.RegisterGuardianByGoogleDTO;

public interface IGoogleService {
	RegisterGuardianByGoogleDTO getUserInfo(String accessToken);
	String getUserImage(String accessToken);
	String getGoogleIdByAccessToken(String accessToken);
	String getUserNameForAccessToken(String accessToken) throws IOException;
}
