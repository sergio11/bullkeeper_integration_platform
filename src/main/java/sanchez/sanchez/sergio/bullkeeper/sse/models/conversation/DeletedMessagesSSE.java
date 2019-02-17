package sanchez.sanchez.sergio.bullkeeper.sse.models.conversation;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Deleted Messages SSE
 * @author sergiosanchezsanchez
 *
 */
public final class DeletedMessagesSSE extends AbstractSseData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "DELETED_MESSAGES_EVENT";
	
	
	 /**
     * Conversation
     */
    @JsonProperty("conversation")
    private String conversation;

    /**
     * Ids
     */
    @JsonProperty("ids")
	private List<String> ids;
	
	
	/**
	 * 
	 */
	public DeletedMessagesSSE() {
		this.eventType = EVENT_TYPE;
	}
	
	
	/**
	 * Delete Messages SSE
	 * @param subscriberId
	 * @param conversation
	 * @param ids
	 */
	public DeletedMessagesSSE(final String subscriberId, final String conversation,
			final List<String> ids) {
		super(EVENT_TYPE, subscriberId);
		this.conversation = conversation;
		this.ids = ids;
	}


	public String getConversation() {
		return conversation;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setConversation(String conversation) {
		this.conversation = conversation;
	}
	
	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "DeletedMessagesSSE [conversation=" + conversation + ", ids=" + ids + "]";
	}
}
