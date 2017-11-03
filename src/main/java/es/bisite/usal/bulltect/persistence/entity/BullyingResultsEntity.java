package es.bisite.usal.bulltect.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class BullyingResultsEntity extends ResultsEntity {
	
	@Field("avg_value")
	private Double avgValue;
	

	public BullyingResultsEntity() {
		super();
	}


	@PersistenceConstructor
	public BullyingResultsEntity(Double avgValue) {
		super();
		this.avgValue = avgValue;
	}


	public Double getAvgValue() {
		return avgValue;
	}


	public void setAvgValue(Double avgValue) {
		this.avgValue = avgValue;
	}
}
