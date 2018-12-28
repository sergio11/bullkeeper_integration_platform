package sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Delete Scheduled Block SSE
 * @author sergiosanchezsanchez
 *
 */
public final class DeleteScheduledBlockSSE extends AbstractSseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "DELETE_SCHEDULED_BLOCK_EVENT";
	
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Scheduled Id
	 */
	@JsonProperty("block")
	private String block;
	
	/**
	 * 
	 */
	public DeleteScheduledBlockSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param block
	 */
	public DeleteScheduledBlockSSE(String subscriberId, String kid, String block) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.block = block;
	}

	public String getKid() {
		return kid;
	}

	public String getBlock() {
		return block;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	@Override
	public String toString() {
		return "DeleteScheduledBlockSSE [kid=" + kid + ", block=" + block + "]";
	}
}
