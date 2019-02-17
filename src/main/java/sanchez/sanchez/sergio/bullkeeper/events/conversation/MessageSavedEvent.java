package sanchez.sanchez.sergio.bullkeeper.events.conversation;

import org.springframework.context.ApplicationEvent;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.MessageDTO;

/**
 * Message Saved Event
 * @author sergiosanchezsanchez
 *
 */
public final class MessageSavedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Message
	 */
	private final MessageDTO message;

	/**
	 * @param source
	 * @param message
	 */
	public MessageSavedEvent(final Object source,
			final MessageDTO message) {
		super(source);
		this.message = message;
	}

	public MessageDTO getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "MessageSavedEvent [message=" + message + "]";
	}

	
	
}
