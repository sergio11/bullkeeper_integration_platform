package sanchez.sanchez.sergio.dto.response;

import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author sergio
 */
public class SocialMediaDTO extends ResourceSupport implements Serializable {
    
	@JsonProperty("identity")
    private String identity;
	@JsonProperty("access_token")
    private String accessToken;
	@JsonProperty("type")
    private String type;
    @JsonProperty("invalid_token")
    private Boolean invalidToken;
    @JsonProperty("user")
    private String user;
    
    public SocialMediaDTO(){}

    public SocialMediaDTO(String identity, String accessToken, String type, Boolean invalidToken, String user) {
        this.identity = identity;
        this.accessToken = accessToken;
        this.type = type;
        this.invalidToken = invalidToken;
        this.user = user;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
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

    public Boolean getInvalidToken() {
        return invalidToken;
    }

    public void setInvalidToken(Boolean invalidToken) {
        this.invalidToken = invalidToken;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
