package sanchez.sanchez.sergio.bullkeeper.sse.models.conversation;

import java.io.Serializable;

import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Message Saved SSE
 * @author sergiosanchezsanchez
 *
 */
public class MessageSavedSSE extends AbstractSseData implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "MESSAGE_SAVED_EVENT";
	
	/**
	 * Origin
	 */
	private final String origin;
	
	
	/**
	 * Text
	 */
	private final String text;
	
	
	
	/**
	 * Message Saved SSE
	 * @param subscriberId
	 */
	public MessageSavedSSE(final String origin, final String target,
			final String text) {
		super(EVENT_TYPE, target);
		this.origin = origin;
		this.text = text;
	}


	public String getOrigin() {
		return origin;
	}


	public String getText() {
		return text;
	}


	@Override
	public String toString() {
		return "MessageSavedSSE [origin=" + origin + ", text=" + text + "]";
	}

}
