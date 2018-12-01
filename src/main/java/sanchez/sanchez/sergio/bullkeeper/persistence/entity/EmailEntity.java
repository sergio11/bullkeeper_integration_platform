
package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author sergio
 * Represents the information of an email that failed to be delivered.
 */
@Document(collection = EmailEntity.COLLECTION_NAME)
public class EmailEntity {
    
    public final static String COLLECTION_NAME = "failed_emails";
    
    /**
     * Id
     */
    @Id
    private ObjectId id;
    
    /**
     * Send To
     */
    @Field("send_to")
    private String sendTo;
    
    /*
     * Subject
     */
    @Field("subject")
    private String subject;
    
    /**
     * Content
     */
    @Field("content")
    private String content;
    
    /**
     * Md5
     */
    @Field("md5")
    private String md5;
    
    /**
     * Last Chance
     */
    @Field("last_chance")
    private Date lastChance = new Date();
    
    /**
     * Error
     */
    @Field("error")
    private String error;
    
    /**
     * Type
     */
    @Field("type")
    private EmailTypeEnum type;
    
    public EmailEntity(){}

    /**
     * 
     * @param sendTo
     * @param subject
     * @param content
     * @param md5
     * @param lastChance
     * @param error
     * @param type
     */
    @PersistenceConstructor
    public EmailEntity(String sendTo, String subject, 
            String content, String md5, Date lastChance, String error, EmailTypeEnum type) {
        this.sendTo = sendTo;
        this.subject = subject;
        this.content = content;
        this.md5 = md5;
        this.lastChance = lastChance;
        this.error = error;
        this.type = type;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
   

    public Date getLastChance() {
        return lastChance;
    }

    public void setLastChance(Date lastChance) {
        this.lastChance = lastChance;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

	public EmailTypeEnum getType() {
		return type;
	}

	public void setType(EmailTypeEnum type) {
		this.type = type;
	}
}
