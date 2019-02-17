package sanchez.sanchez.sergio.bullkeeper.events.conversation;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationEvent;

/**
 * Set Messages As Viewed Event
 * @author sergiosanchezsanchez
 *
 */
public final class SetMessagesAsViewedEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private final String conversation;
	private final String memberOne;
	private final String memberTwo;
	private final List<ObjectId> messageIds;
	
	/**
	 * 
	 * @param source
	 * @param conversation
	 * @param memberOne
	 * @param memberTwo
	 * @param messageIds
	 */
	public SetMessagesAsViewedEvent(Object source, final String conversation,
			final String memberOne, final String memberTwo,
			final List<ObjectId> messageIds) {
		super(source);
		this.conversation = conversation;
		this.memberOne = memberOne;
		this.memberTwo = memberTwo;
		this.messageIds = messageIds;
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

	public List<ObjectId> getMessageIds() {
		return messageIds;
	}

	@Override
	public String toString() {
		return "SetMessagesAsViewedEvent [conversation=" + conversation + ", memberOne=" + memberOne + ", memberTwo="
				+ memberTwo + ", messageIds=" + messageIds + "]";
	}

}
