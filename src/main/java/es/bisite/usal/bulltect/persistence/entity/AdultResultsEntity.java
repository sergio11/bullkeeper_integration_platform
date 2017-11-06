package es.bisite.usal.bulltect.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class AdultResultsEntity extends ResultsEntity {
	
	@Field("total_comments_adult_content")
	private Long totalCommentsAdultContent;
	
	@Field("total_comments_noadult_content")
	private Long totalCommentsNoAdultContent;
	

	public AdultResultsEntity() {
		super();
	}

	@PersistenceConstructor
	public AdultResultsEntity(Long totalCommentsAdultContent, Long totalCommentsNoAdultContent) {
		super();
		this.totalCommentsAdultContent = totalCommentsAdultContent;
		this.totalCommentsNoAdultContent = totalCommentsNoAdultContent;
	}

	public Long getTotalCommentsAdultContent() {
		return totalCommentsAdultContent;
	}


	public void setTotalCommentsAdultContent(Long totalCommentsAdultContent) {
		this.totalCommentsAdultContent = totalCommentsAdultContent;
	}


	public Long getTotalCommentsNoAdultContent() {
		return totalCommentsNoAdultContent;
	}


	public void setTotalCommentsNoAdultContent(Long totalCommentsNoAdultContent) {
		this.totalCommentsNoAdultContent = totalCommentsNoAdultContent;
	}
	
	
}
