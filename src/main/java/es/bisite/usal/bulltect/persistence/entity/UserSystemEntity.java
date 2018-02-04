
package es.bisite.usal.bulltect.persistence.entity;

import java.util.Date;
import java.util.Locale;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author sergio
 */
@Document(collection = UserSystemEntity.COLLECTION_NAME)
public class UserSystemEntity extends PersonEntity {

    public final static String COLLECTION_NAME = "users";

    @Field("email")
    protected String email;

    @Field("password")
    protected String password;

    @Field("password_requested_at")
    protected String passwordRequestedAt;

    @Field("is_active")
    protected Boolean active = Boolean.TRUE;

    @Field("is_locked")
    protected Boolean locked = Boolean.FALSE;

    @Field("last_login_access")
    protected Date lastLoginAccess;

    @Field("pending_deletion")
    protected Boolean pendingDeletion = Boolean.FALSE;

    @Field("locale")
    protected Locale locale = Locale.getDefault();

    @Field("last_password_reset_date")
    protected Date lastPasswordResetDate;

    /* Random string sent to the user email address in order to verify it */
    @Field("confirmation_token")
    protected String confirmationToken;

    @Field("last_access_to_alerts")
    protected Date lastAccessToAlerts = new Date();
    
    @Field("preferences")
    protected PreferencesEntity preferences = new PreferencesEntity();

    @DBRef
    protected AuthorityEntity authority;

    public UserSystemEntity() {
    }

    @PersistenceConstructor
    public UserSystemEntity(String firstName, String lastName, Date birthdate, String profileImage, String email,
            String password, String passwordRequestedAt, Boolean active, Boolean locked, Date lastLoginAccess,
            Boolean pendingDeletion, Locale locale, Date lastPasswordResetDate, String confirmationToken,
            Date lastAccessToAlerts, PreferencesEntity preferences, AuthorityEntity authority) {
        super(firstName, lastName, birthdate, profileImage);
        this.email = email;
        this.password = password;
        this.passwordRequestedAt = passwordRequestedAt;
        this.active = active;
        this.locked = locked;
        this.lastLoginAccess = lastLoginAccess;
        this.pendingDeletion = pendingDeletion;
        this.locale = locale;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.confirmationToken = confirmationToken;
        this.lastAccessToAlerts = lastAccessToAlerts;
        this.preferences = preferences;
        this.authority = authority;
    }

    public UserSystemEntity(String firstName, String lastName, Date birthdate, String email, String password, AuthorityEntity authority) {
        super(firstName, lastName, birthdate);
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Date getLastLoginAccess() {
        return lastLoginAccess;
    }

    public void setLastLoginAccess(Date lastLoginAccess) {
        this.lastLoginAccess = lastLoginAccess;
    }

    public AuthorityEntity getAuthority() {
        return authority;
    }

    public void setAuthority(AuthorityEntity authority) {
        this.authority = authority;
    }

    public String getPasswordRequestedAt() {
        return passwordRequestedAt;
    }

    public void setPasswordRequestedAt(String passwordRequestedAt) {
        this.passwordRequestedAt = passwordRequestedAt;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Boolean isPendingDeletion() {
        return pendingDeletion;
    }

    public void setPendingDeletion(Boolean pendingDeletion) {
        this.pendingDeletion = pendingDeletion;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }


    public Date getLastAccessToAlerts() {
        return lastAccessToAlerts;
    }

    public void setLastAccessToAlerts(Date lastAccessToAlerts) {
        this.lastAccessToAlerts = lastAccessToAlerts;
    }
    

	public PreferencesEntity getPreferences() {
		return preferences;
	}

	public void setPreferences(PreferencesEntity preferences) {
		this.preferences = preferences;
	}

	@Override
    public String toString() {
        return "UserSystemEntity [email=" + email + ", password=" + password + ", locked=" + locked + ", authority="
                + authority + "]";
    }
}
