package sanchez.sanchez.sergio.bullkeeper.sse.models.location;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Current Location Update SSE
 * @author sergiosanchezsanchez
 *
 */
public final class CurrentLocationUpdateSSE 
		extends AbstractSseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *Event Type
	 */
	public static final String EVENT_TYPE = "CURRENT_LOCATION_UPDATE_EVENT";

	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Lat
	 */
	@JsonProperty("lat")
	private double lat;
	
	/**
	 * Log
	 */
	@JsonProperty("log")
	private double log;
	
	/**
	 * Address
	 */
	@JsonProperty("address")
	private String address;
	
	/**
	 * 
	 */
	public CurrentLocationUpdateSSE() {
		this.eventType = EVENT_TYPE;
	}
	
	/**
	 *
	 * @param subscriberId
	 * @param kid
	 * @param lat
	 * @param log
	 * @param address
	 */
	public CurrentLocationUpdateSSE(String subscriberId, String kid, double lat, double log,
			String address) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.lat = lat;
		this.log = log;
		this.address = address;
	}

	public String getKid() {
		return kid;
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

	public void setKid(String kid) {
		this.kid = kid;
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
		return "CurrentLocationUpdateSSE [kid=" + kid + ", lat=" + lat + ", log=" + log + ", address=" + address + "]";
	}
}
