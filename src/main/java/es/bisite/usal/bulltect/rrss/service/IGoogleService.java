package es.bisite.usal.bulltect.rrss.service;

import es.bisite.usal.bulltect.web.dto.request.RegisterParentByGoogleDTO;

public interface IGoogleService {
	
	RegisterParentByGoogleDTO getUserInfo(String accessToken);
	String getGoogleIdByAccessToken(String accessToken);

}
