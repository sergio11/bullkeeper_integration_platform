package sanchez.sanchez.sergio.bullkeeper.events.conversation;

import org.springframework.context.ApplicationEvent;

/**
 * Conversation Deleted Event
 * @author sergiosanchezsanchez
 *
 */
public final class ConversationDeletedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Conversation
	 */
	private String conversation;
	
	/**
	 * Member One
	 */
	private String memberOne;
	
	/**
	 * Member Two
	 */
	private String memberTwo;
	

	/**
	 * @param source
	 * @param conversation
	 * @param memberOne
	 * @param memberTwo
	 */
	public ConversationDeletedEvent(final Object source,
			final String conversation, final String memberOne,
			final String memberTwo) {
		super(source);
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
		return "ConversationDeletedEvent [conversation=" + conversation + ", memberOne=" + memberOne + ", memberTwo="
				+ memberTwo + "]";
	}

	
}
