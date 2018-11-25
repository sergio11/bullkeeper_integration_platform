package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidSocialMediaType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Save Social Media DTO
 * @author sergiosanchezsanchez
 *
 */
public class SaveSocialMediaDTO {

	/**
	 * Access Token
	 */
    @NotBlank(message = "{social.token.notnull}")
    @JsonProperty("access_token")
    private String accessToken;
    
    /**
     * Refresh Token
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    /**
     * Type
     */
    @ValidSocialMediaType(message = "{social.type.invalid}")
    @JsonProperty("type")
    private String type;
    
    /**
     * User Social Name
     */
    @JsonProperty("user_social_name")
    private String userSocialName;

    /**
     * User Social Full Name
     */
    @JsonProperty("user_social_full_name")
    private String userSocialFullName;

    /**
     * User PIcture
     */
    @JsonProperty("user_picture")
    private String userPicture;
    
    /**
     * Kid
     */
    @ValidObjectId(message = "{kid.id.notvalid}")
    @KidShouldExists(message = "{social.son.not.exists}", groups = Extended.class)
    @JsonProperty("kid")
    private String kid;
    
    
    public SaveSocialMediaDTO() {}

    public SaveSocialMediaDTO(String accessToken, String refreshToken,
    		String type, String userSocialName,
			String userSocialFullName, String userPicture, String kid) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.type = type;
		this.userSocialName = userSocialName;
		this.userSocialFullName = userSocialFullName;
		this.userPicture = userPicture;
		this.kid = kid;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserSocialName() {
		return userSocialName;
	}

	public void setUserSocialName(String userSocialName) {
		this.userSocialName = userSocialName;
	}

	public String getUserSocialFullName() {
		return userSocialFullName;
	}

	public void setUserSocialFullName(String userSocialFullName) {
		this.userSocialFullName = userSocialFullName;
	}

	public String getUserPicture() {
		return userPicture;
	}

	public void setUserPicture(String userPicture) {
		this.userPicture = userPicture;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

  

}
