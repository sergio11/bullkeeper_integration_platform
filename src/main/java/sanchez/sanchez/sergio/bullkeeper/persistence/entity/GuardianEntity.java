package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import java.util.Locale;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = GuardianEntity.COLLECTION_NAME)
public final class GuardianEntity extends UserSystemEntity {

    public final static String COLLECTION_NAME = "guardians";

    /**
     * Telephone
     */
    @Field("telephone")
    private String telephone;

    /**
     * FB Access Token
     */
    @Field("fb_access_token")
    private String fbAccessToken;

    /**
     * FB Id
     */
    @Field("fb_id")
    private String fbId;
    
    /**
     * Google Id
     */
    @Field("google_id")
    private String googleId;
    
    /**
     * Visible
     */
    @Field("visible")
    private boolean visible = false;

    public GuardianEntity() {
    }

    @PersistenceConstructor
    public GuardianEntity(String firstName, String lastName, Date birthdate, String profileImage, String email,
            String password, String passwordRequestedAt, Boolean active, Boolean locked, Date lastLoginAccess,
            Boolean pendingDeletion, Locale locale, Date lastPasswordResetDate, String confirmationToken,
            Date lastAccessToAlerts, PreferencesEntity preferences, AuthorityEntity authority,
            String telephone, String fbAccessToken, 
            String fbId, String googleId, boolean visible) {
        super(firstName, lastName, birthdate, profileImage, email, password, passwordRequestedAt, active, locked,
                lastLoginAccess, pendingDeletion, locale, lastPasswordResetDate, confirmationToken, lastAccessToAlerts,
                preferences, authority);
        this.telephone = telephone;
        this.fbAccessToken = fbAccessToken;
        this.fbId = fbId;
        this.googleId = googleId;
        this.visible = visible;
    }

    public GuardianEntity(String firstName, String lastName, Date birthdate, String email, String password,
            AuthorityEntity authority) {
        super(firstName, lastName, birthdate, email, password, authority);
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFbAccessToken() {
        return fbAccessToken;
    }

    public void setFbAccessToken(String fbAccessToken) {
        this.fbAccessToken = fbAccessToken;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }
    
    public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
    public String toString() {
        return "GuardianEntity [telephone=" + telephone + ", fbAccessToken=" + fbAccessToken + ", fbId=" + fbId
                + ", email=" + email + ", password=" + password + ", passwordRequestedAt=" + passwordRequestedAt
                + ", active=" + active + ", locked=" + locked + ", lastLoginAccess=" + lastLoginAccess
                + ", pendingDeletion=" + pendingDeletion + ", locale=" + locale + ", confirmationToken="
                + confirmationToken + ", authority=" + authority + ", id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", birthdate=" + birthdate + "]";
    }

}
