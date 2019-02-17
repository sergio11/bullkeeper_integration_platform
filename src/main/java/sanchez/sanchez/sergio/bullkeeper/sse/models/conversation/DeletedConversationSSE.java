package sanchez.sanchez.sergio.bullkeeper.sse.models.conversation;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;

/**
 * Delete Conversaton SSE
 * @author sergiosanchezsanchez
 *
 */
public final class DeletedConversationSSE extends AbstractSseData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "DELETED_CONVERSATION_EVENT";
	
	/**
     * Conversation
     */
    @JsonProperty("conversation")
    private String conversation;

    /**
     * Member One
     */
    @JsonProperty("member_one")
    private String memberOne;

    /**
     * Member Two
     */
    @JsonProperty("member_two")
    private String memberTwo;
    
    
    /**
	 * 
	 */
	public DeletedConversationSSE() {
		this.eventType = EVENT_TYPE;
	}
	
	
	/**
	 * Delete Conversation SSE
	 * @param subscriberId
	 * @param conversation
	 * @param memberOne
	 * @param memberTwo
	 */
	public DeletedConversationSSE(final String subscriberId, final String conversation,
			final String memberOne, final String memberTwo) {
		super(EVENT_TYPE, subscriberId);
		this.conversation = conversation;
		this.memberOne = memberOne;
		this.memberTwo = memberTwo;
	}


	public String getConversation() {
		return conversation;
	}


	public String getMemberOne() {
		return memberOne;
	}


	public String getMemberTwo() {
		return memberTwo;
	}


	public void setConversation(String conversation) {
		this.conversation = conversation;
	}


	public void setMemberOne(String memberOne) {
		this.memberOne = memberOne;
	}


	public void setMemberTwo(String memberTwo) {
		this.memberTwo = memberTwo;
	}


	@Override
	public String toString() {
		return "DeletedConversationSSE [conversation=" + conversation + ", memberOne=" + memberOne + ", memberTwo="
				+ memberTwo + "]";
	}

	
}
