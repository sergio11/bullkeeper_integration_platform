package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Bullying Results Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class BullyingResultsEntity extends ResultsEntity {
	
	/**
	 * Total Comments Bullying
	 */
	@Field("total_comments_bullying")
	private long totalCommentsBullying;
	
	/**
	 * Total Comments No Bullying
	 */
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
