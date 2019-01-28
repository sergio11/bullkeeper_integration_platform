package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Geofence Alert Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public final class GeofenceAlertEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Title
	 */
	@Field("title")
	private String title;
	
	/**
	 * Description
	 */
	@Field("description")
	private String description;
	
	/**
	 * Date
	 */
	@Field("date")
	private Date date;
		
	/**
	 * Type
	 */
	@Field("transition_type")
	private GeofenceTransitionTypeEnum type;
	
	/**
	 * 
	 */
	public GeofenceAlertEntity(){}

	/**
	 * 
	 * @param title
	 * @param description
	 * @param date
	 * @param type
	 */
	public GeofenceAlertEntity(final String title, 
			final String description, final Date date, 
			final GeofenceTransitionTypeEnum type) {
		super();
		this.title = title;
		this.description = description;
		this.date = date;
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public GeofenceTransitionTypeEnum getType() {
		return type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setType(GeofenceTransitionTypeEnum type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "GeofenceAlertEntity [title=" + title + ", description=" + description + ", date=" + date + ", type="
				+ type + "]";
	}

}
