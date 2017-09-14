package es.bisite.usal.bullytect.service;

import org.bson.types.ObjectId;

import es.bisite.usal.bullytect.security.userdetails.CommonUserDetailsAware;

public interface IAuthorizationService {
	Boolean hasAdminRole();
	Boolean hasParentRole();
	Boolean hasChangePasswordPrivilege();
	Boolean isYourSon(String id);
	CommonUserDetailsAware<ObjectId> getUserDetails();
	Boolean isTheAuthenticatedUser(String id);
	void grantChangePasswordPrivilege(String id);
}
