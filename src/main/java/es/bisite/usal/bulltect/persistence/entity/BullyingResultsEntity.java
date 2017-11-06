package es.bisite.usal.bulltect.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class BullyingResultsEntity extends ResultsEntity {
	
	@Field("total_comments_bullying")
	private Long totalCommentsBullying;
	
	@Field("total_comments_nobullying")
	private Long totalCommentsNoBullying;
	

	public BullyingResultsEntity() {
		super();
	}

	@PersistenceConstructor
	public BullyingResultsEntity(Long totalCommentsBullying, Long totalCommentsNoBullying) {
		super();
		this.totalCommentsBullying = totalCommentsBullying;
		this.totalCommentsNoBullying = totalCommentsNoBullying;
	}

	public Long getTotalCommentsBullying() {
		return totalCommentsBullying;
	}

	public void setTotalCommentsBullying(Long totalCommentsBullying) {
		this.totalCommentsBullying = totalCommentsBullying;
	}

	public Long getTotalCommentsNoBullying() {
		return totalCommentsNoBullying;
	}

	public void setTotalCommentsNoBullying(Long totalCommentsNoBullying) {
		this.totalCommentsNoBullying = totalCommentsNoBullying;
	}
	
}
