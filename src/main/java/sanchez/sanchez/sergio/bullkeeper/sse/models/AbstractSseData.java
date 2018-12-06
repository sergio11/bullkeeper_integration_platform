package sanchez.sanchez.sergio.bullkeeper.sse.models;

/**
 * Abstract SSE Data
 * @author sergiosanchezsanchez
 *
 */
public abstract class AbstractSseData {
	
	/**
	 * Subscriber Id
	 */
	protected String subscriberId;

	/**
	 * 
	 * @param subscriberId
	 */
	public AbstractSseData(String subscriberId) {
		super();
		this.subscriberId = subscriberId;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	

}
