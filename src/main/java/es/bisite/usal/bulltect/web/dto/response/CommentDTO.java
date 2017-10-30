package es.bisite.usal.bulltect.web.dto.response;

import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 *
 * @author sergio
 */
public class CommentDTO extends ResourceSupport implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("identity")
    private String identity;
	@JsonProperty("message")
    private String message;
	@JsonProperty("likes")
    private Long likes;
	@JsonProperty("social_media")
	private String socialMedia;
	@JsonProperty("created_time")
    private String createdTime;
	@JsonProperty("user")
    private String user;
    
    public CommentDTO(){}

    public CommentDTO(String identity, String message, Long likes, String socialMedia, String createdTime, String user) {
        this.identity = identity;
        this.message = message;
        this.likes = likes;
        this.socialMedia = socialMedia;
        this.createdTime = createdTime;
        this.user = user;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
    
    public String getSocialMedia() {
		return socialMedia;
	}

	public void setSocialMedia(String socialMedia) {
		this.socialMedia = socialMedia;
	}

	public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((identity == null) ? 0 : identity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentDTO other = (CommentDTO) obj;
		if (identity == null) {
			if (other.identity != null)
				return false;
		} else if (!identity.equals(other.identity))
			return false;
		return true;
	}
    

	@Override
	public String toString() {
		return "CommentDTO [identity=" + identity + ", message=" + message + ", likes=" + likes + ", socialMedia="
				+ socialMedia + ", createdTime=" + createdTime + ", user=" + user + "]";
	}

    
}
