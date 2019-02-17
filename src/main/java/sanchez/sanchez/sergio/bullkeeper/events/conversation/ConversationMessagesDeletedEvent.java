package sanchez.sanchez.sergio.bullkeeper.events.conversation;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEvent;

/**
 * Conversation Messages Deeted Event
 * @author sergiosanchezsanchez
 *
 */
public final class ConversationMessagesDeletedEvent extends ApplicationEvent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Conversation
	 */
	private String conversation;
	
	/**
	 * Message Ids
	 */
	private final List<ObjectId> messageIds;
	
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
	 * @param messageIds
	 * @param memberOne
	 * @param memberTwo
	 */
	public ConversationMessagesDeletedEvent(final Object source,
			final String conversation, final List<ObjectId> messageIds,
			final String memberOne, final String memberTwo) {
		super(source);
		this.conversation = conversation;
		this.messageIds = messageIds;
		this.memberOne = memberOne;
		this.memberTwo = memberTwo;
	}

	public String getConversation() {
		return conversation;
	}

	public List<ObjectId> getMessageIds() {
		return messageIds;
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

	@Override
	public String toString() {
		return "ConversationMessagesDeletedEvent [conversation=" + conversation + ", messageIds=" + messageIds
				+ ", memberOne=" + memberOne + ", memberTwo=" + memberTwo + "]";
	}

	
}
