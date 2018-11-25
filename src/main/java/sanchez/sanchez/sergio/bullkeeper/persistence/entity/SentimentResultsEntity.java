package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Sentiment Results Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class SentimentResultsEntity extends ResultsEntity {
	
	/**
	 * Total Positive
	 */
	@Field("total_positive")
	private long totalPositive;
	
	/**
	 * Total Negative
	 */
	@Field("total_negative")
	private long totalNegative;
	
	/**
	 * Total Neutro
	 */
	@Field("total_neutro")
	private long totalNeutro;
	

	public SentimentResultsEntity() {
		super();
	}

	/**
	 * 
	 * @param obsolete
	 * @param date
	 * @param totalPositive
	 * @param totalNegative
	 * @param totalNeutro
	 */
	@PersistenceConstructor
	public SentimentResultsEntity(Boolean obsolete, Date date, long totalPositive, long totalNegative,
			long totalNeutro) {
		super(obsolete, date);
		this.totalPositive = totalPositive;
		this.totalNegative = totalNegative;
		this.totalNeutro = totalNeutro;
	}

	public long getTotalPositive() {
		return totalPositive;
	}

	public void setTotalPositive(long totalPositive) {
		this.totalPositive = totalPositive;
	}

	public long getTotalNegative() {
		return totalNegative;
	}

	public void setTotalNegative(long totalNegative) {
		this.totalNegative = totalNegative;
	}

	public long getTotalNeutro() {
		return totalNeutro;
	}

	public void setTotalNeutro(long totalNeutro) {
		this.totalNeutro = totalNeutro;
	}

	@Override
	public String toString() {
		return "SentimentResultsEntity [totalPositive=" + totalPositive + ", totalNegative=" + totalNegative
				+ ", totalNeutro=" + totalNeutro + "]";
	}
	
	
}
