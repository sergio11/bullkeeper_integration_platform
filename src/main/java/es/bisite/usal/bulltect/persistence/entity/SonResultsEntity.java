package es.bisite.usal.bulltect.persistence.entity;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class SonResultsEntity {
	
	@Field("sentiment")
	private SentimentResultsEntity sentiment = new SentimentResultsEntity();
	
	public SonResultsEntity(){}
	
	@PersistenceConstructor
	public SonResultsEntity(SentimentResultsEntity sentiment) {
		super();
		this.sentiment = sentiment;
	}

	public SentimentResultsEntity getSentiment() {
		return sentiment;
	}

	public void setSentiment(SentimentResultsEntity sentiment) {
		this.sentiment = sentiment;
	}
}
