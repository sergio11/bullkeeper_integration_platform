package es.bisite.usal.bulltect.persistence.entity;


import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
public class ViolenceResultsEntity extends ResultsEntity {
	
	@Field("avg_value")
	private Double avgValue;
	

	public ViolenceResultsEntity() {
		super();
	}


	@PersistenceConstructor
	public ViolenceResultsEntity(Double avgValue) {
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
