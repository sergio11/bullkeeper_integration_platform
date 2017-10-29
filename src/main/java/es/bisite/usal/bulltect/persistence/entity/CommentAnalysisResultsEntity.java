package es.bisite.usal.bulltect.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class CommentAnalysisResultsEntity {

	@Field("sentiment")
	private AnalysisEntity sentiment = new AnalysisEntity(AnalysisTypeEnum.SENTIMENT);
	
	@Field("violence")
	private AnalysisEntity violence = new AnalysisEntity(AnalysisTypeEnum.VIOLENCE);
	
	@Field("drugs")
	private AnalysisEntity drugs = new AnalysisEntity(AnalysisTypeEnum.DRUGS);
	
	public CommentAnalysisResultsEntity(){}

	@PersistenceConstructor
	public CommentAnalysisResultsEntity(AnalysisEntity sentiment, AnalysisEntity violence,
			AnalysisEntity drugs) {
		super();
		this.sentiment = sentiment;
		this.violence = violence;
		this.drugs = drugs;
	}


	public AnalysisEntity getSentiment() {
		return sentiment;
	}

	public void setSentiment(AnalysisEntity sentiment) {
		this.sentiment = sentiment;
	}

	public AnalysisEntity getViolence() {
		return violence;
	}

	public void setViolence(AnalysisEntity violence) {
		this.violence = violence;
	}

	public AnalysisEntity getDrugs() {
		return drugs;
	}

	public void setDrugs(AnalysisEntity drugs) {
		this.drugs = drugs;
	}
}
