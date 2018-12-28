package sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Delete Scheduled Block SSE
 * @author sergiosanchezsanchez
 *
 */
public final class DeleteAllScheduledBlockSSE extends AbstractSseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "DELETE_ALL_SCHEDULED_BLOCK_EVENT";
	
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;

	/**
	 * 
	 */
	public DeleteAllScheduledBlockSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param block
	 */
	public DeleteAllScheduledBlockSSE(String subscriberId, String kid) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
	}

	public String getKid() {
		return kid;
	}


	public void setKid(String kid) {
		this.kid = kid;
	}


	@Override
	public String toString() {
		return "DeleteScheduledBlockSSE [kid=" + kid + "]";
	}
}
