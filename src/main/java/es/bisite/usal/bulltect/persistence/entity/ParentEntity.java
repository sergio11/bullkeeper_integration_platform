package es.bisite.usal.bulltect.persistence.entity;

import java.util.Date;
import java.util.Locale;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = ParentEntity.COLLECTION_NAME)
public final class ParentEntity extends UserSystemEntity {

    public final static String COLLECTION_NAME = "parents";

    @Field("telephone")
    private String telephone;

    @Field("fb_access_token")
    private String fbAccessToken;

    @Field("fb_id")
    private String fbId;
    
    @Field("google_id")
    private String googleId;

    public ParentEntity() {
    }

    @PersistenceConstructor
    public ParentEntity(String firstName, String lastName, Date birthdate, String profileImage, String email,
            String password, String passwordRequestedAt, Boolean active, Boolean locked, Date lastLoginAccess,
            Boolean pendingDeletion, Locale locale, Date lastPasswordResetDate, String confirmationToken,
            Date lastAccessToAlerts, PreferencesEntity preferences, AuthorityEntity authority, String telephone, String fbAccessToken, String fbId, String googleId) {
        super(firstName, lastName, birthdate, profileImage, email, password, passwordRequestedAt, active, locked,
                lastLoginAccess, pendingDeletion, locale, lastPasswordResetDate, confirmationToken, lastAccessToAlerts,
                preferences, authority);
        this.telephone = telephone;
        this.fbAccessToken = fbAccessToken;
        this.fbId = fbId;
        this.googleId = googleId;
    }

    public ParentEntity(String firstName, String lastName, Date birthdate, String email, String password,
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

	@Override
    public String toString() {
        return "ParentEntity [telephone=" + telephone + ", fbAccessToken=" + fbAccessToken + ", fbId=" + fbId
                + ", email=" + email + ", password=" + password + ", passwordRequestedAt=" + passwordRequestedAt
                + ", active=" + active + ", locked=" + locked + ", lastLoginAccess=" + lastLoginAccess
                + ", pendingDeletion=" + pendingDeletion + ", locale=" + locale + ", confirmationToken="
                + confirmationToken + ", authority=" + authority + ", id=" + id + ", firstName=" + firstName
                + ", lastName=" + lastName + ", birthdate=" + birthdate + "]";
    }

}
