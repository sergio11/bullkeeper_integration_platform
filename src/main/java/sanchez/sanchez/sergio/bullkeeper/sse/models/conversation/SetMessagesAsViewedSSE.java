package sanchez.sanchez.sergio.bullkeeper.sse.models.conversation;

import java.io.Serializable;
import java.util.List;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class SetMessagesAsViewedSSE extends AbstractSseData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "SET_MESSAGES_AS_VIEWED_EVENT";
	
	/**
	 * Conversation
	 */
	private String conversation;
	
	/**
	 * Message Ids
	 */
	private List<String> messageIds;
	
	
	/**
	* 
	*/
	public SetMessagesAsViewedSSE() {
		this.eventType = EVENT_TYPE;
	}
		
		
	/**
	* Delete Conversation SSE
	* @param subscriberId
	* @param conversation
	* @param messageIds
	*/
	public SetMessagesAsViewedSSE(final String subscriberId, final String conversation, final List<String> messageIds) {
		super(EVENT_TYPE, subscriberId);
		this.conversation = conversation;
		this.messageIds = messageIds;
	}

	public String getConversation() {
		return conversation;
	}

	public List<String> getMessageIds() {
		return messageIds;
	}

	public void setConversation(String conversation) {
		this.conversation = conversation;
	}

	public void setMessageIds(List<String> messageIds) {
		this.messageIds = messageIds;
	}

	@Override
	public String toString() {
		return "SetMessagesAsViewedSSE [conversation=" + conversation + ", messageIds=" + messageIds + "]";
	}
}
