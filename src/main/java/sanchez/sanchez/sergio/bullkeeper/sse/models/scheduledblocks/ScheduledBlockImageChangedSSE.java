package sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Scheduled Block Image Changed SSE
 * @author sergiosanchezsanchez
 *
 */
public final class ScheduledBlockImageChangedSSE 
	extends AbstractSseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "SCHEDULED_BLOCK_IMAGE_CHANGED_EVENT";
	
	
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
	 * IMage
	 */
	@JsonProperty("image")
	private String image;
	
	public ScheduledBlockImageChangedSSE() {
		this.eventType = EVENT_TYPE;
	}

	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param block
	 * @param image
	 */
	public ScheduledBlockImageChangedSSE(String subscriberId, 
			String kid, String block, String image) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.block = block;
		this.image = image;
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
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ScheduledBlockImageChangedSSE [kid=" + kid + ", block=" + block + ", image=" + image + "]";
	}

	
}
