package es.bisite.usal.bulltect.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class DrugsResultsEntity extends ResultsEntity {
	
	@Field("total_comments_drugs")
	private Long totalCommentsDrugs;
	
	@Field("total_comments_nodrugs")
	private Long totalCommentsNoDrugs;
	

	public DrugsResultsEntity() {
		super();
	}

	@PersistenceConstructor
	public DrugsResultsEntity(Long totalCommentsDrugs, Long totalCommentsNoDrugs) {
		super();
		this.totalCommentsDrugs = totalCommentsDrugs;
		this.totalCommentsNoDrugs = totalCommentsNoDrugs;
	}

	public Long getTotalCommentsDrugs() {
		return totalCommentsDrugs;
	}

	public void setTotalCommentsDrugs(Long totalCommentsDrugs) {
		this.totalCommentsDrugs = totalCommentsDrugs;
	}

	public Long getTotalCommentsNoDrugs() {
		return totalCommentsNoDrugs;
	}

	public void setTotalCommentsNoDrugs(Long totalCommentsNoDrugs) {
		this.totalCommentsNoDrugs = totalCommentsNoDrugs;
	}
	
}
