package sanchez.sanchez.sergio.bullkeeper.sse.models;

import org.springframework.context.ApplicationEvent;

/**
 * Abstract SSE Application Event
 * @author sergiosanchezsanchez
 *
 */
public abstract class AbstractSseApplicationEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Subscriber ID
	 */
	protected String subscriberId;
	
	
	public AbstractSseApplicationEvent(Object source) {
		super(source);
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
}
