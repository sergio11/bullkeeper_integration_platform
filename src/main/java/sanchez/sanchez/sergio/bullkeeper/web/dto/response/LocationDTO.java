package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Location DTO
 * @author sergiosanchezsanchez
 *
 */
public final class LocationDTO implements Serializable {

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
	 * Address
	 */
	@JsonProperty("address")
	private String address;
	
	/**
	 * Location DTO
	 */
	public LocationDTO() {}

	/**
	 * Location DTO
	 * @param lat
	 * @param log
	 * @param address
	 */
	public LocationDTO(double lat, double log, String address) {
		super();
		this.lat = lat;
		this.log = log;
		this.address = address;
	}

	public double getLat() {
		return lat;
	}

	public double getLog() {
		return log;
	}

	public String getAddress() {
		return address;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public void setLog(double log) {
		this.log = log;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "LocationDTO [lat=" + lat + ", log=" + log + ", address=" + address + "]";
	}

}
