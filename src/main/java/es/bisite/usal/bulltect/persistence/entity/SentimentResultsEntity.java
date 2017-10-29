package es.bisite.usal.bulltect.persistence.entity;

import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class SentimentResultsEntity extends ResultsEntity {
	
	@Field("total_positive")
	private Long totalPositive;
	
	@Field("total_negative")
	private Long totalNegative;
	
	@Field("total_neutro")
	private Long totalNeutro;
	

	public SentimentResultsEntity() {
		super();
	}

	@PersistenceConstructor
	public SentimentResultsEntity(Boolean obsolete, Date date, Long totalPositive, Long totalNegative,
			Long totalNeutro) {
		super(obsolete, date);
		this.totalPositive = totalPositive;
		this.totalNegative = totalNegative;
		this.totalNeutro = totalNeutro;
	}

	public Long getTotalPositive() {
		return totalPositive;
	}

	public void setTotalPositive(Long totalPositive) {
		this.totalPositive = totalPositive;
	}

	public Long getTotalNegative() {
		return totalNegative;
	}

	public void setTotalNegative(Long totalNegative) {
		this.totalNegative = totalNegative;
	}

	public Long getTotalNeutro() {
		return totalNeutro;
	}

	public void setTotalNeutro(Long totalNeutro) {
		this.totalNeutro = totalNeutro;
	}
}
