package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import sanchez.sanchez.sergio.bullkeeper.persistence.utils.CascadeSave;

/**
 *
 * @author sergio
 */
@Document(collection = CommentEntity.COLLECTION_NAME)
public class CommentEntity {

    public final static String COLLECTION_NAME = "comments";

    /**
     * Id
     */
    @Id
    private ObjectId id;
    
    /**
     * External ID
     */
    @Field("external_id")
    private String externalId;

    /**
     * Message
     */
    @Field("message")
    private String message;

    /**
     * Likes
     */
    @Field("likes")
    private Long likes = 0l;

    /**
     * Created Time
     */
    @Field("created_time")
    private Date createdTime;
    
    /**
     * Extracted At
     */
    @Field("extracted_at")
    private Date extractedAt = new Date();
    
    /**
     * Extracted Times
     */
    @Field("extracted_times")
    private Integer extractedTimes = 1;

    /**
     * Social Media
     */
    @Field("social_media")
    private SocialMediaTypeEnum socialMedia;
    
    /**
     * Author
     */
    @Field("author")
    private CommentAuthorEntity author = new CommentAuthorEntity();

    /**
     * Target
     */
    @Field("kid")
    @DBRef
    private KidEntity kid;
    
    /**
     * Analysis Results
     */
    @Field("analysis_results")
    @CascadeSave
    private CommentAnalysisResultsEntity analysisResults = new CommentAnalysisResultsEntity();

    public CommentEntity() {
    }

    /**
     * 
     * @param externalId
     * @param message
     * @param likes
     * @param createdTime
     * @param extractedAt
     * @param extractedTimes
     * @param socialMedia
     * @param author
     * @param kid
     * @param analysisResults
     */
    @PersistenceConstructor
    public CommentEntity(String externalId, String message, Long likes, Date createdTime, Date extractedAt, Integer extractedTimes,
            SocialMediaTypeEnum socialMedia, CommentAuthorEntity author, KidEntity kid, CommentAnalysisResultsEntity analysisResults) {
        super();
        this.externalId = externalId;
        this.message = message;
        this.likes = likes;
        this.createdTime = createdTime;
        this.extractedAt = extractedAt;
        this.extractedTimes = extractedTimes;
        this.socialMedia = socialMedia;
        this.author = author;
        this.kid = kid;
        this.analysisResults = analysisResults;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    

    public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
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

    

    public KidEntity getKid() {
		return kid;
	}

	public void setKid(KidEntity kid) {
		this.kid = kid;
	}

	public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
    

    public Date getExtractedAt() {
		return extractedAt;
	}

	public void setExtractedAt(Date extractedAt) {
		this.extractedAt = extractedAt;
	}
	

	public Integer getExtractedTimes() {
		return extractedTimes;
	}

	public void setExtractedTimes(Integer extractedTimes) {
		this.extractedTimes = extractedTimes;
	}

	public SocialMediaTypeEnum getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(SocialMediaTypeEnum socialMedia) {
        this.socialMedia = socialMedia;
    }

	public CommentAuthorEntity getAuthor() {
		return author;
	}

	public void setAuthor(CommentAuthorEntity author) {
		this.author = author;
	}

	public CommentAnalysisResultsEntity getAnalysisResults() {
		return analysisResults;
	}

	public void setAnalysisResults(CommentAnalysisResultsEntity analysisResults) {
		this.analysisResults = analysisResults;
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((externalId == null) ? 0 : externalId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentEntity other = (CommentEntity) obj;
		if (externalId == null) {
			if (other.externalId != null)
				return false;
		} else if (!externalId.equals(other.externalId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommentEntity [id=" + id + ", externalId=" + externalId + ", message=" + message + ", likes=" + likes
				+ ", createdTime=" + createdTime + ", extractedAt=" + extractedAt + ", extractedTimes=" + extractedTimes
				+ ", socialMedia=" + socialMedia + ", author=" + author + ", kid=" + kid + ", analysisResults="
				+ analysisResults + "]";
	}
}
