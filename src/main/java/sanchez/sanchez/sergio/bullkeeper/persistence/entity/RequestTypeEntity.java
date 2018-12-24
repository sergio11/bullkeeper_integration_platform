package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import sanchez.sanchez.sergio.bullkeeper.persistence.utils.CascadeSave;

/**
 * Request Type Entity
 * @author sergiosanchezsanchez
 *
 */
@Document
public class RequestTypeEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Type
	 */
	@Field("type")
	private RequestTypeEnum type;
	
	/**
	 * Request At
	 */
	@Field("request_at")
	private Date requestAt;
	
	/**
	 * Expired At
	 */
	@Field("expired_at")
	private Date expiredAt;
	
	/**
     * Location
     */
    @Field("location")
    @CascadeSave
    private LocationEntity location;
	
	/**
	 * 
	 */
	public RequestTypeEntity() {}

	/**
	 * 
	 * @param type
	 * @param requestAt
	 */
	@PersistenceConstructor
	public RequestTypeEntity(final RequestTypeEnum type, final Date requestAt,
			final LocationEntity location) {
		super();
		this.type = type;
		this.requestAt = requestAt;
		this.location = location;
	}

	public RequestTypeEnum getType() {
		return type;
	}

	public Date getRequestAt() {
		return requestAt;
	}

	public void setType(RequestTypeEnum type) {
		this.type = type;
	}

	public void setRequestAt(Date requestAt) {
		this.requestAt = requestAt;
	}
	
	public Date getExpiredAt() {
		return expiredAt;
	}

	public LocationEntity getLocation() {
		return location;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

	public void setLocation(LocationEntity location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "RequestTypeEntity [type=" + type + ", requestAt=" + requestAt + ", expiredAt=" + expiredAt
				+ ", location=" + location + "]";
	}
	
}
