package sanchez.sanchez.sergio.masoc.persistence.entity;


import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class BullyingResultsEntity extends ResultsEntity {
	
	@Field("total_comments_bullying")
	private long totalCommentsBullying;
	
	@Field("total_comments_nobullying")
	private long totalCommentsNoBullying;
	

	public BullyingResultsEntity() {
		super();
	}

	@PersistenceConstructor
	public BullyingResultsEntity(Boolean obsolete, Date date, long totalCommentsBullying, long totalCommentsNoBullying) {
		super(obsolete, date);
		this.totalCommentsBullying = totalCommentsBullying;
		this.totalCommentsNoBullying = totalCommentsNoBullying;
	}

	public long getTotalCommentsBullying() {
		return totalCommentsBullying;
	}

	public void setTotalCommentsBullying(long totalCommentsBullying) {
		this.totalCommentsBullying = totalCommentsBullying;
	}

	public long getTotalCommentsNoBullying() {
		return totalCommentsNoBullying;
	}

	public void setTotalCommentsNoBullying(long totalCommentsNoBullying) {
		this.totalCommentsNoBullying = totalCommentsNoBullying;
	}
	
}
