package sanchez.sanchez.sergio.dto;

import java.io.Serializable;

/**
 *
 * @author sergio
 */
public class CommentDTO implements Serializable {
    
    private String message;
    private Long likes;
    private String createdTime;
    private String user;
    
    public CommentDTO(){}

    public CommentDTO(String message, Long likes, String createdTime, String user) {
        this.message = message;
        this.likes = likes;
        this.createdTime = createdTime;
        this.user = user;
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
