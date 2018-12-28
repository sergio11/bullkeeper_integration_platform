package sanchez.sanchez.sergio.bullkeeper.sse.models.scheduledblocks;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Scheduled Block Status Changed
 * @author sergiosanchezsanchez
 *
 */
public final class ScheduledBlockStatusChangedSSE 
		extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "SCHEDULED_BLOCK_STATUS_CHANGED_EVENT";
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Scheduled Block Status
	 */
	@JsonProperty("scheduled_block_status")
	private Iterable<ScheduledBlockStatus> scheduledBlockStatusList;
	
	
	/**
	 * 
	 */
	public ScheduledBlockStatusChangedSSE() {
		this.eventType = EVENT_TYPE;
	}
	
	/**
	 * 
	 * @param subscriberId
	 * @param kid
	 * @param scheduledBlockStatusList
	 */
	public ScheduledBlockStatusChangedSSE(String subscriberId, String kid,
			Iterable<ScheduledBlockStatus> scheduledBlockStatusList) {
		super(EVENT_TYPE, subscriberId);
		this.kid = kid;
		this.scheduledBlockStatusList = scheduledBlockStatusList;
	}


	public String getKid() {
		return kid;
	}

	public Iterable<ScheduledBlockStatus> getScheduledBlockStatusList() {
		return scheduledBlockStatusList;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setScheduledBlockStatusList(Iterable<ScheduledBlockStatus> scheduledBlockStatusList) {
		this.scheduledBlockStatusList = scheduledBlockStatusList;
	}

	/**
	 * Scheduled Block Status
	 * @author sergiosanchezsanchez
	 *
	 */
	public static class ScheduledBlockStatus {
		
		/**
		 * Identity
		 */
		@JsonProperty("identity")
		private String identity;
		
		/**
		 * Enable
		 */
		@JsonProperty("enable")
		private boolean enable;
		
		
		/**
		 * 
		 * @param identity
		 * @param enable
		 */
		public ScheduledBlockStatus(String identity, boolean enable) {
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
			return "ScheduledBlockStatus [identity=" + identity + ", enable=" + enable + "]";
		}
		
		
	}
	

}
