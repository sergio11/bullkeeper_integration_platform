package sanchez.sanchez.sergio.bullkeeper.events.conversation;

import org.springframework.context.ApplicationEvent;
/**
 * All Conversation Messages Deleted Event
 * @author sergiosanchezsanchez
 *
 */
public final class AllConversationMessagesDeletedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Conversation
	 */
	private final String conversation;
	
	/**
	 * Member One
	 */
	private final String memberOne;
	
	/**
	 * Member Two
	 */
	private final String memberTwo;

	/**
	 * @param source
	 * @param conversation
	 * @param memberOne
	 * @param memberTwo
	 */
	public AllConversationMessagesDeletedEvent(final Object source,
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

	@Override
	public String toString() {
		return "AllConversationMessagesDeletedEvent [conversation=" + conversation + ", memberOne=" + memberOne
				+ ", memberTwo=" + memberTwo + "]";
	}
}
