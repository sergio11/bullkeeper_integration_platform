package es.bisite.usal.bulltect.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class ViolenceResultsEntity extends ResultsEntity {
	
	@Field("total_violent_comments")
	private Long totalViolentComments;
	
	@Field("total nonviolent comments")
	private Long totalNonViolentComments;

	public ViolenceResultsEntity() {
		super();
	}

	@PersistenceConstructor
	public ViolenceResultsEntity(Long totalViolentComments, Long totalNonViolentComments) {
		super();
		this.totalViolentComments = totalViolentComments;
		this.totalNonViolentComments = totalNonViolentComments;
	}

	public Long getTotalViolentComments() {
		return totalViolentComments;
	}

	public void setTotalViolentComments(Long totalViolentComments) {
		this.totalViolentComments = totalViolentComments;
	}

	public Long getTotalNonViolentComments() {
		return totalNonViolentComments;
	}

	public void setTotalNonViolentComments(Long totalNonViolentComments) {
		this.totalNonViolentComments = totalNonViolentComments;
	}
}
