package sanchez.sanchez.sergio.bullkeeper.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Comment Analysis Results Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class CommentAnalysisResultsEntity {

	/**
	 * Sentiment
	 */
	@Field("sentiment")
	private AnalysisEntity sentiment = new AnalysisEntity(AnalysisTypeEnum.SENTIMENT);
	
	/**
	 * Violence
	 */
	@Field("violence")
	private AnalysisEntity violence = new AnalysisEntity(AnalysisTypeEnum.VIOLENCE);
	
	/**
	 * Drugs
	 */
	@Field("drugs")
	private AnalysisEntity drugs = new AnalysisEntity(AnalysisTypeEnum.DRUGS);
	
	/**
	 * Adult
	 */
	@Field("adult")
	private AnalysisEntity adult = new AnalysisEntity(AnalysisTypeEnum.ADULT);
	
	/**
	 * Bullying
	 */
	@Field("bullying")
	private AnalysisEntity bullying = new AnalysisEntity(AnalysisTypeEnum.BULLYING);
	
	public CommentAnalysisResultsEntity(){}

	@PersistenceConstructor
	public CommentAnalysisResultsEntity(AnalysisEntity sentiment, AnalysisEntity violence, AnalysisEntity drugs,
			AnalysisEntity adult, AnalysisEntity bullying) {
		super();
		this.sentiment = sentiment;
		this.violence = violence;
		this.drugs = drugs;
		this.adult = adult;
		this.bullying = bullying;
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

	public AnalysisEntity getAdult() {
		return adult;
	}

	public void setAdult(AnalysisEntity adult) {
		this.adult = adult;
	}

	public AnalysisEntity getBullying() {
		return bullying;
	}

	public void setBullying(AnalysisEntity bullying) {
		this.bullying = bullying;
	}
}
