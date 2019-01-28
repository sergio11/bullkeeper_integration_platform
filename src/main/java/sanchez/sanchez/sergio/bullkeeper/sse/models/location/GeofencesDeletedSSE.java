package sanchez.sanchez.sergio.bullkeeper.sse.models.location;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Geofences Deleted SSE
 * @author sergiosanchezsanchez
 *
 */
public final class GeofencesDeletedSSE 
		extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public static final String EVENT_TYPE = "GEOFENCES_DELETED_EVENT";
	
	/**
	 * Identity
	 */
	@JsonProperty("ids")
	private List<String> ids;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * 
	 */
	public GeofencesDeletedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param ids
	 * @param kid
	 */
	public GeofencesDeletedSSE(String subscriberId, final List<String> ids,
			final String kid) {
		super(EVENT_TYPE, subscriberId);
		this.ids = ids;
		this.kid = kid;
	}


	public List<String> getIds() {
		return ids;
	}

	public String getKid() {
		return kid;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "GeofencesDeletedSSE [ids=" + ids + ", kid=" + kid + "]";
	}

}
