package sanchez.sanchez.sergio.bullkeeper.events.conversation;

import org.springframework.context.ApplicationEvent;

/**
 * All Conversation Deleted Event
 * @author sergiosanchezsanchez
 *
 */
public final class AllConversationDeletedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Member
	 */
	private final String member;

	/**
	 * @param source
	 * @param member
	 */
	public AllConversationDeletedEvent(final Object source,
			final String member) {
		super(source);
		this.member = member;
	}

	public String getMember() {
		return member;
	}

	@Override
	public String toString() {
		return "AllConversationDeletedEvent [member=" + member + "]";
	}
}
