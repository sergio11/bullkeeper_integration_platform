package sanchez.sanchez.sergio.masoc.persistence.entity;

import java.util.Date;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Violence Results Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class ViolenceResultsEntity extends ResultsEntity {
	
	/**
	 * Total Violent Comments
	 */
	@Field("total_violent_comments")
	private long totalViolentComments;
	
	/**
	 * Total Non Violent Comments
	 */
	@Field("total_nonviolent_comments")
	private long totalNonViolentComments;

	public ViolenceResultsEntity() {
		super();
	}

	@PersistenceConstructor
	public ViolenceResultsEntity(Boolean obsolete, Date date, long totalViolentComments, long totalNonViolentComments) {
		super(obsolete, date);
		this.totalViolentComments = totalViolentComments;
		this.totalNonViolentComments = totalNonViolentComments;
	}

	public long getTotalViolentComments() {
		return totalViolentComments;
	}

	public void setTotalViolentComments(long totalViolentComments) {
		this.totalViolentComments = totalViolentComments;
	}

	public long getTotalNonViolentComments() {
		return totalNonViolentComments;
	}

	public void setTotalNonViolentComments(long totalNonViolentComments) {
		this.totalNonViolentComments = totalNonViolentComments;
	}
}
