package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ScheduledBlockShouldExists;

/**
 * Save Scheduled Blocks Status
 * @author sergiosanchezsanchez
 *
 */
public class SaveScheduledBlockStatusDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@ScheduledBlockShouldExists(message = "{scheduled.block.not.exists}")
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Enable
	 */
	@JsonProperty("enable")
	private boolean enable;
	
	
	public SaveScheduledBlockStatusDTO() {}
	
	/**
	 * 
	 * @param identity
	 * @param enable
	 */
	public SaveScheduledBlockStatusDTO(final String identity, boolean enable) {
		super();
		this.identity = identity;
		this.enable = enable;
	}

	public String getIdentity() {
		return identity;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "SaveScheduledBlockStatusDTO [identity=" + identity + ", enable=" + enable + "]";
	}


}
