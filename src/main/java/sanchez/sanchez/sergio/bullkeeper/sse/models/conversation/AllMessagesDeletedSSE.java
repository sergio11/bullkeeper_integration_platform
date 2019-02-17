package sanchez.sanchez.sergio.bullkeeper.sse.models.conversation;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * All Messages Deleted SSE
 * @author sergiosanchezsanchez
 *
 */
public class AllMessagesDeletedSSE extends AbstractSseData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "ALL_MESSAGES_DELETED_EVENT";
	
	
	/**
     * Conversation
     */
    @JsonProperty("conversation")
    private String conversation;
    
    
    /**
	 * 
	 */
	public AllMessagesDeletedSSE() {
		this.eventType = EVENT_TYPE;
	}
	
	
	/**
	 * Delete Conversation SSE
	 * @param subscriberId
	 * @param conversation
	 */
	public AllMessagesDeletedSSE(final String subscriberId, final String conversation) {
		super(EVENT_TYPE, subscriberId);
		this.conversation = conversation;
	}


	public String getConversation() {
		return conversation;
	}


	public void setConversation(String conversation) {
		this.conversation = conversation;
	}


	@Override
	public String toString() {
		return "AllMessagesDeletedSSE [conversation=" + conversation + "]";
	}
	
	

}
