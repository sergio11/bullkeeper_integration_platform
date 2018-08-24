package sanchez.sanchez.sergio.masoc.rrss.service;

import java.io.IOException;

import sanchez.sanchez.sergio.masoc.web.dto.request.RegisterParentByGoogleDTO;

public interface IGoogleService {
	RegisterParentByGoogleDTO getUserInfo(String accessToken);
	String getUserImage(String accessToken);
	String getGoogleIdByAccessToken(String accessToken);
	String getUserNameForAccessToken(String accessToken) throws IOException;
}
