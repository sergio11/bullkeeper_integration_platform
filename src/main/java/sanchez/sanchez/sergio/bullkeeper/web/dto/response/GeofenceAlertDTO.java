package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Geofence Alert DTO
 * @author sergiosanchezsanchez
 *
 */
public final class GeofenceAlertDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Title
	 */
	@JsonProperty("title")
	private String title;
	
	/**
	 * Description
	 */
	@JsonProperty("description")
	private String description;
	
	/**
	 * Date
	 */
	@JsonProperty("date")
	private String date;
	
	/**
	 * Type
	 */
	@JsonProperty("type")
	private String type;

	/**
	 * 
	 */
	public GeofenceAlertDTO() {}

	/**
	 * 
	 * @param title
	 * @param description
	 * @param date
	 * @param type
	 */
	public GeofenceAlertDTO(final String title, final String description,
			final String date, final String type) {
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

	public String getDate() {
		return date;
	}

	public String getType() {
		return type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "GeofenceAlertDTO [title=" + title + ", description=" + description + ", date=" + date + ", type=" + type
				+ "]";
	}
	
}
