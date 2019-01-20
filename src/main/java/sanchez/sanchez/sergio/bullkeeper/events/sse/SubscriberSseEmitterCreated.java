package sanchez.sanchez.sergio.bullkeeper.events.sse;

import org.springframework.context.ApplicationEvent;

/**
 * Subscriber Sse Emitter Created
 * @author sergiosanchezsanchez
 *
 */
public final class SubscriberSseEmitterCreated extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Subscriber ID
	 */
	private final String subscriberId;

	/**
	 * 
	 * @param source
	 * @parma subscriberId
	 */
	public SubscriberSseEmitterCreated(final Object source,
			final String subscriberId) {
		super(source);
		this.subscriberId = subscriberId;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	@Override
	public String toString() {
		return "SubscriberSseEmitterCreated [subscriberId=" + subscriberId + "]";
	}
	
}
