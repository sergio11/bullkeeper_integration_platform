package sanchez.sanchez.sergio.masoc.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import sanchez.sanchez.sergio.masoc.persistence.utils.CascadeSave;

@Document
public class SonResultsEntity {
	
	
	@Field("sentiment")
	@CascadeSave
	private SentimentResultsEntity sentiment = new SentimentResultsEntity();
	
	@Field("violence")
	@CascadeSave
	private ViolenceResultsEntity violence = new ViolenceResultsEntity();
	
	@Field("drugs")
	@CascadeSave
	private DrugsResultsEntity drugs = new DrugsResultsEntity();
	
	@Field("adult")
	@CascadeSave
	private AdultResultsEntity adult = new AdultResultsEntity();
	
	@Field("bullying")
	@CascadeSave
	private BullyingResultsEntity bullying = new BullyingResultsEntity();
	
	
	public SonResultsEntity(){}
	
	@PersistenceConstructor
	public SonResultsEntity(SentimentResultsEntity sentiment, ViolenceResultsEntity violence, DrugsResultsEntity drugs,
			AdultResultsEntity adult, BullyingResultsEntity bullying) {
		super();
		this.sentiment = sentiment;
		this.violence = violence;
		this.drugs = drugs;
		this.adult = adult;
		this.bullying = bullying;
	}
	

	public SentimentResultsEntity getSentiment() {
		return sentiment;
	}

	public void setSentiment(SentimentResultsEntity sentiment) {
		this.sentiment = sentiment;
	}

	public ViolenceResultsEntity getViolence() {
		return violence;
	}

	public void setViolence(ViolenceResultsEntity violence) {
		this.violence = violence;
	}

	public DrugsResultsEntity getDrugs() {
		return drugs;
	}

	public void setDrugs(DrugsResultsEntity drugs) {
		this.drugs = drugs;
	}

	

	public AdultResultsEntity getAdult() {
		return adult;
	}

	public void setAdult(AdultResultsEntity adult) {
		this.adult = adult;
	}

	public BullyingResultsEntity getBullying() {
		return bullying;
	}

	public void setBullying(BullyingResultsEntity bullying) {
		this.bullying = bullying;
	}
	
}
