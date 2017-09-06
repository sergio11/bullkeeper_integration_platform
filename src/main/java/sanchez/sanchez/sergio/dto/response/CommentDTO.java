package sanchez.sanchez.sergio.dto.response;

import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author sergio
 */
public class CommentDTO extends ResourceSupport implements Serializable {
    
	@JsonProperty("identity")
    private String identity;
	@JsonProperty("message")
    private String message;
	@JsonProperty("likes")
    private Long likes;
	@JsonProperty("created_time")
    private String createdTime;
	@JsonProperty("user")
    private String user;
    
    public CommentDTO(){}

    public CommentDTO(String identity, String message, Long likes, String createdTime, String user) {
        this.identity = identity;
        this.message = message;
        this.likes = likes;
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
    public String toString() {
        return "CommentDTO{" + "message=" + message + ", likes=" + likes + ", createdTime=" + createdTime + ", user=" + user + '}';
    }
}
