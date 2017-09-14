package es.bisite.usal.bullytect.persistence.entity;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = RememberMeTokenEntity.COLLECTION_NAME)
public class RememberMeTokenEntity {
	
	public final static String COLLECTION_NAME = "remembermetokens";
	
	@Id
    private ObjectId id;
	@Field("series")
    private String series;
	@Field("username")
    private String username;
	@Field("token_values")
    private String tokenValue;
	@Field("date")
    private Date date;
    
    public RememberMeTokenEntity(){}
    
    @PersistenceConstructor
    public RememberMeTokenEntity(String series, String username, String tokenValue, Date date) {
		super();
		this.series = series;
		this.username = username;
		this.tokenValue = tokenValue;
		this.date = date;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
