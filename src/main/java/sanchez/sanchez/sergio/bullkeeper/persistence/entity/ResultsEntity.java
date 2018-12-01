package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Results Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class ResultsEntity {

	/**
	 * Obsolete
	 */
	@Field("obsolete")
	private Boolean obsolete = Boolean.FALSE;
	
	/**
	 * Date
	 */
	@Field("date")
    private Date date = new Date();
	

	public ResultsEntity() {
		super();
	}
	
	/**
	 * 
	 * @param obsolete
	 * @param date
	 */
	@PersistenceConstructor
	public ResultsEntity( Boolean obsolete, Date date) {
		super();
		this.obsolete = obsolete;
		this.date = date;
	}
	

	public Boolean IsObsolete() {
		return obsolete;
	}

	public void setObsolete(Boolean obsolete) {
		this.obsolete = obsolete;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
