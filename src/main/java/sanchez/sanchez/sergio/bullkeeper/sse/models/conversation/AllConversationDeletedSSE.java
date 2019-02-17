package sanchez.sanchez.sergio.bullkeeper.sse.models.conversation;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class AllConversationDeletedSSE extends AbstractSseData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3486105158320015339L;


	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "ALL_CONVERSATION_DELETED_EVENT";
	
	
	/**
     * Member
     */
    @JsonProperty("member")
    private String member;
    
    
    /**
   	 * 
   	 */
   	public AllConversationDeletedSSE() {
   		this.eventType = EVENT_TYPE;
   	}
   	
   	
   	/**
   	 * All Conversation Deleted SSE
   	 * @param subscriberId
   	 * @param member
   	 */
   	public AllConversationDeletedSSE(final String subscriberId, final String member) {
   		super(EVENT_TYPE, subscriberId);
   		this.member = member;
   	}


	public String getMember() {
		return member;
	}


	public void setMember(String member) {
		this.member = member;
	}


	@Override
	public String toString() {
		return "AllConversationDeletedSSE [member=" + member + "]";
	}
    
    
    
}
