package es.bisite.usal.bulltect.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.bisite.usal.bulltect.persistence.constraints.SonShouldExists;
import es.bisite.usal.bulltect.persistence.constraints.ValidObjectId;
import es.bisite.usal.bulltect.persistence.constraints.ValidSocialMediaType;
import es.bisite.usal.bulltect.persistence.constraints.group.Extended;

import org.hibernate.validator.constraints.NotBlank;

public class SaveSocialMediaDTO {

    @NotBlank(message = "{social.token.notnull}")
    @JsonProperty("access_token")
    private String accessToken;
    @ValidSocialMediaType(message = "{social.type.invalid}")
    @JsonProperty("type")
    private String type;
    @ValidObjectId(message = "{son.id.notvalid}")
    @SonShouldExists(message = "{social.son.not.exists}", groups = Extended.class)
    @JsonProperty("son")
    private String son;

    public SaveSocialMediaDTO() {
    }

    public SaveSocialMediaDTO(String accessToken, String type, String son) {
        super();
        this.accessToken = accessToken;
        this.type = type;
        this.son = son;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
