package sanchez.sanchez.sergio.bullkeeper.web.security.userdetails;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;

public interface CommonUserDetailsAware<T> extends UserDetails {

    final String ID = "id";
    final String EMAIL = "EMAIL";
    final String PASSWORD = "PASSWORD";
    final String FIRST_NAME = "FIRST_NAME";
    final String LAST_NAME = "LAST_NAME";
    final String LAST_PASSWORD_RESET_DATE = "LAST_PASSWORD_RESET_DATE";
    final String LAST_ACCESS_TO_ALERTS = "LAST_ACCESS_TO_ALERTS";
    final String LAST_LOGIN_ACCESS = "LAST_LOGIN_ACCESS";
    final String IS_PENDING_DELETE = "IS_PENDING_DELETE";
    final String IS_PROFILE_VISIBLE = "IS_PROFILE_VISIBLE";

    T getUserId();

    String getEmail();

    String getPassword();

    String getFirstName();

    String getLastName();

    String getFullName();

    Date getLastPasswordResetDate();
    
    Date getLastAccessToAlerts();
    
    Date getLastLoginAccess();
    
    Boolean isPendingDelete();
    
    Boolean isProfileVisible();
    
    

}
