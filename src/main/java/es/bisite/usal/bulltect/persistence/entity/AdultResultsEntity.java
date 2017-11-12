package es.bisite.usal.bulltect.persistence.entity;


import java.util.Date;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class AdultResultsEntity extends ResultsEntity {
	
	@Field("total_comments_adult_content")
	private long totalCommentsAdultContent;
	
	@Field("total_comments_noadult_content")
	private long totalCommentsNoAdultContent;
	

	public AdultResultsEntity() {
		super();
	}

	@PersistenceConstructor
	public AdultResultsEntity(Boolean obsolete, Date date, long totalCommentsAdultContent, long totalCommentsNoAdultContent) {
		super(obsolete, date);
		this.totalCommentsAdultContent = totalCommentsAdultContent;
		this.totalCommentsNoAdultContent = totalCommentsNoAdultContent;
	}

	public long getTotalCommentsAdultContent() {
		return totalCommentsAdultContent;
	}


	public void setTotalCommentsAdultContent(long totalCommentsAdultContent) {
		this.totalCommentsAdultContent = totalCommentsAdultContent;
	}


	public long getTotalCommentsNoAdultContent() {
		return totalCommentsNoAdultContent;
	}


	public void setTotalCommentsNoAdultContent(long totalCommentsNoAdultContent) {
		this.totalCommentsNoAdultContent = totalCommentsNoAdultContent;
	}
	
	
}
