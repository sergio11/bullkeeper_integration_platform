package sanchez.sanchez.sergio.bullkeeper.sse.models;

import java.io.Serializable;

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
		super(target);
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
