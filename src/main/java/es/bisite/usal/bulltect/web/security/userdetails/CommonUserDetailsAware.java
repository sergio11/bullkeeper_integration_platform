package es.bisite.usal.bulltect.web.security.userdetails;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public interface CommonUserDetailsAware<T> extends UserDetails {

    final String ID = "id";
    final String EMAIL = "EMAIL";
    final String PASSWORD = "PASSWORD";
    final String FIRST_NAME = "FIRST_NAME";
    final String LAST_NAME = "LAST_NAME";
    final String LAST_PASSWORD_RESET_DATE = "LAST_PASSWORD_RESET_DATE";
    final String PROFILE_IMAGE_ID = "PROFILE_IMAGE_ID";
    final String LAST_ACCESS_TO_ALERTS = "LAST_ACCESS_TO_ALERTS";
    final String LAST_LOGIN_ACCESS = "LAST_LOGIN_ACCESS";

    T getUserId();

    String getEmail();

    String getPassword();

    String getFirstName();

    String getLastName();

    String getFullName();

    Date getLastPasswordResetDate();

    String getProfileImageId();
    
    Date getLastAccessToAlerts();
    
    Date getLastLoginAccess();

}
