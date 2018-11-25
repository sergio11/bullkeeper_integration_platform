package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Alert DTO
 * @author sergiosanchezsanchez
 *
 */
public class AlertDTO extends ResourceSupport {

	/**
	 * Identity
	 */
    @JsonProperty("identity")
    private String identity;
    
    /**
     * Title
     */
    @JsonProperty("title")
    private String title;
    
    /**
     * Level
     */
    @JsonProperty("level")
    private String level;
    
    /**
     * Payload
     */
    @JsonProperty("payload")
    private String payload;
    
    /**
     * Create At
     */
    @JsonProperty("create_at")
    private String createAt;
    
    /**
     * Since
     */
    @JsonProperty("since")
    private String since;
    
    /**
     * Kid
     */
    @JsonProperty("kid")
    private KidDTO kid;
    
    /**
     * Category
     */
    @JsonProperty("category")
    private String category;

    public AlertDTO() {
    }

    public AlertDTO(String identity, String level, String title, String payload, 
    		String createAt, String since, KidDTO kid, String category) {
        super();
        this.identity = identity;
        this.level = level;
        this.title = title;
        this.payload = payload;
        this.createAt = createAt;
        this.since = since;
        this.kid = kid;
        this.category = category;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getSince() {
		return since;
	}

	public void setSince(String since) {
		this.since = since;
	}

	public KidDTO getKid() {
		return kid;
	}

	public void setKid(KidDTO kid) {
		this.kid = kid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
    
}
