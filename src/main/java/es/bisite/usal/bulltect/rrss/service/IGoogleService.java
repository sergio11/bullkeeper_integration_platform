package es.bisite.usal.bulltect.rrss.service;

import java.io.IOException;
import es.bisite.usal.bulltect.web.dto.request.RegisterParentByGoogleDTO;

public interface IGoogleService {
	RegisterParentByGoogleDTO getUserInfo(String accessToken);
	String getUserImage(String accessToken);
	String getGoogleIdByAccessToken(String accessToken);
	String getUserNameForAccessToken(String accessToken) throws IOException;
}
