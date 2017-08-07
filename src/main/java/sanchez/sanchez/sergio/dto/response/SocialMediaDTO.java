package sanchez.sanchez.sergio.dto.response;

import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author sergio
 */
public class SocialMediaDTO extends ResourceSupport implements Serializable {
    
    private String identity;
    private String accessToken;
    private String type;
    private Boolean invalidToken;
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
