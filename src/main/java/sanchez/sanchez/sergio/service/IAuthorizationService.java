package sanchez.sanchez.sergio.service;

public interface IAuthorizationService {
	Boolean hasAdminRole();
	Boolean hasParentRole();
	Boolean isYourSon(String id);
}
