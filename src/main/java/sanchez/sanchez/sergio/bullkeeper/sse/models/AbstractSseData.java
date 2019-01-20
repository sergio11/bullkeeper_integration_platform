package sanchez.sanchez.sergio.bullkeeper.sse.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Abstract SSE Data
 * @author sergiosanchezsanchez
 *
 */
public abstract class AbstractSseData {
	
	/**
	 * Event Type
	 */
	@JsonProperty("event_type")
	protected String eventType;
	
	/**
	 * Subscriber Id
	 */
	@JsonProperty("subscriber_id")
	protected String subscriberId;
	
	public AbstractSseData() {}

	/**
	 * 
	 * @param subscriberId
	 */
	public AbstractSseData(String eventType, String subscriberId) {
		super();
		this.eventType = eventType;
		this.subscriberId = subscriberId;
	}


	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	

}
