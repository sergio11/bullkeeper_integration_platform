package es.bisite.usal.bulltect.persistence.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author sergio
 */
@Document(collection = CommentEntity.COLLECTION_NAME)
public class CommentEntity {

    public final static String COLLECTION_NAME = "comments";

    @Id
    private ObjectId id;

    @Field("message")
    private String message;

    @Field("likes")
    private Long likes = 0l;

    @Field("created_time")
    private Date createdTime;
    
    @Field("extracted_at")
    private Date extractedAt = new Date();

    @Field("social_media")
    private SocialMediaTypeEnum socialMedia;
    
    @Field("author")
    private CommentAuthorEntity author = new CommentAuthorEntity();

    @Field("target")
    @DBRef
    private SonEntity sonEntity;
    
    @Field("analysis_results")
    private CommentAnalysisResultsEntity analysisResults = new CommentAnalysisResultsEntity();

    public CommentEntity() {
    }

    @PersistenceConstructor
    public CommentEntity(String message, Long likes, Date createdTime, Date extractedAt,
            SocialMediaTypeEnum socialMedia, CommentAuthorEntity author, SonEntity sonEntity, CommentAnalysisResultsEntity analysisResults) {
        super();
        this.message = message;
        this.likes = likes;
        this.createdTime = createdTime;
        this.extractedAt = extractedAt;
        this.socialMedia = socialMedia;
        this.author = author;
        this.sonEntity = sonEntity;
        this.analysisResults = analysisResults;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public SonEntity getSonEntity() {
        return sonEntity;
    }

    public void setSonEntity(SonEntity sonEntity) {
        this.sonEntity = sonEntity;
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
	public String toString() {
		return "CommentEntity [id=" + id + ", message=" + message + ", likes=" + likes + ", createdTime=" + createdTime
				+ ", socialMedia=" + socialMedia + ", author=" + author + ", sonEntity=" + sonEntity
				+ ", analysisResults=" + analysisResults + "]";
	}
	
	
}
