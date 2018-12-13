package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Save Location DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SaveLocationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Latitude
	 */
	@JsonProperty("latitude")
	private double lat;
	
	/**
	 * Longitude
	 */
	@JsonProperty("longitude")
	private double log;

	/**
	 * 
	 */
	public SaveLocationDTO() {}
	
	/**
	 * 
	 * @param lat
	 * @param log
	 */
	public SaveLocationDTO(double lat, double log) {
		super();
		this.lat = lat;
		this.log = log;
	}

	public double getLat() {
		return lat;
	}

	public double getLog() {
		return log;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLog(double log) {
		this.log = log;
	}

	@Override
	public String toString() {
		return "SaveLocationDTO [lat=" + lat + ", log=" + log + "]";
	}
}
