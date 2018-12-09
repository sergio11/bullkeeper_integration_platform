package sanchez.sanchez.sergio.bullkeeper.events;


import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.sse.models.MessageSavedSSE;

/**
 * Message Saved App Event
 * @author sergiosanchezsanchez
 *
 */
public class MessageSavedAppEvent extends AbstractSseApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Message
	 */
	private MessageSavedSSE message;

	/**
	 * 
	 * @param source
	 * @param subscriberId
	 * @param message
	 */
	public MessageSavedAppEvent(Object source, String subscriberId, MessageSavedSSE message) {
		super(source, subscriberId);
		this.message = message;
	}

	public MessageSavedSSE getMessage() {
		return message;
	}

	public void setMessage(MessageSavedSSE message) {
		this.message = message;
	}

}
