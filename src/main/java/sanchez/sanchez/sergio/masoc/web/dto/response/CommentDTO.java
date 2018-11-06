package sanchez.sanchez.sergio.masoc.web.dto.response;

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
	@JsonProperty("extracted_at")
    private String extractedAt;
	@JsonProperty("extracted_at_since")
    private String extractedAtSince;
	@JsonProperty("author_name")
    private String authorName;
	@JsonProperty("author_photo")
    private String authorPhoto;
	@JsonProperty("adult")
	private String adult;
	@JsonProperty("bullying")
	private String bullying;
	@JsonProperty("drugs")
	private String drugs;
	@JsonProperty("sentiment")
	private String sentiment;
	@JsonProperty("violence")
	private String violence;
	
    
    public CommentDTO(){}

    public CommentDTO(String identity, String message, Long likes, String socialMedia, 
    		String createdTime, String extractedAt, String extractedAtSince, 
    		String authorName, String authorPhoto, String adult, String bullying, String drugs,
    		String sentiment, String violence) {
        this.identity = identity;
        this.message = message;
        this.likes = likes;
        this.socialMedia = socialMedia;
        this.createdTime = createdTime;
        this.extractedAt = extractedAt;
        this.extractedAtSince = extractedAtSince;
        this.authorName = authorName;
        this.authorPhoto = authorPhoto;
        this.adult = adult;
        this.bullying = bullying;
        this.drugs = drugs;
        this.sentiment = sentiment;
        this.violence = violence;
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

    
    
    public String getExtractedAt() {
		return extractedAt;
	}

	public void setExtractedAt(String extractedAt) {
		this.extractedAt = extractedAt;
	}

	
	public String getExtractedAtSince() {
		return extractedAtSince;
	}

	public void setExtractedAtSince(String extractedAtSince) {
		this.extractedAtSince = extractedAtSince;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorPhoto() {
		return authorPhoto;
	}

	public void setAuthorPhoto(String authorPhoto) {
		this.authorPhoto = authorPhoto;
	}
	
	

	public String getAdult() {
		return adult;
	}

	public void setAdult(String adult) {
		this.adult = adult;
	}

	public String getBullying() {
		return bullying;
	}

	public void setBullying(String bullying) {
		this.bullying = bullying;
	}

	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public String getViolence() {
		return violence;
	}

	public void setViolence(String violence) {
		this.violence = violence;
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
				+ socialMedia + ", createdTime=" + createdTime + ", extractedAt=" + extractedAt + ", extractedAtSince="
				+ extractedAtSince + ", authorName=" + authorName + ", authorPhoto=" + authorPhoto + ", adult=" + adult
				+ ", bullying=" + bullying + ", drugs=" + drugs + ", sentiment=" + sentiment + ", violence=" + violence
				+ "]";
	}
}
