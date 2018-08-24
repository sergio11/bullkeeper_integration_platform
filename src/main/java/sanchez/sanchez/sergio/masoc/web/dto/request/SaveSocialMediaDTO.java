package sanchez.sanchez.sergio.masoc.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.masoc.persistence.constraints.SonShouldExists;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.masoc.persistence.constraints.ValidSocialMediaType;
import sanchez.sanchez.sergio.masoc.persistence.constraints.group.Extended;

import org.hibernate.validator.constraints.NotBlank;

public class SaveSocialMediaDTO {

    @NotBlank(message = "{social.token.notnull}")
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @ValidSocialMediaType(message = "{social.type.invalid}")
    @JsonProperty("type")
    private String type;
    @ValidObjectId(message = "{son.id.notvalid}")
    @SonShouldExists(message = "{social.son.not.exists}", groups = Extended.class)
    @JsonProperty("son")
    private String son;

    public SaveSocialMediaDTO() {
    }

    public SaveSocialMediaDTO(String accessToken, String refreshToken,  String type, String son) {
        super();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.type = type;
        this.son = son;
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

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
        this.son = son;
    }

}
