package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Location Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public final class LocationEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Latitude
	 */
	@Field("latitude")
	private double lat;
	
	/**
	 * Longitude
	 */
	@Field("longitude")
	private double log;
	
	/**
	 * Address
	 */
	@Field("address")
	private String address;
	
	/**
	 * Location Entity
	 */
	public LocationEntity() {}

	/**
	 * Location Entity
	 * @param lat
	 * @param log
	 * @param address
	 */
	public LocationEntity(final double lat, 
			final double log, final String address) {
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
		return "LocationEntity [lat=" + lat + ", log=" + log + ", address=" + address + "]";
	}

}
