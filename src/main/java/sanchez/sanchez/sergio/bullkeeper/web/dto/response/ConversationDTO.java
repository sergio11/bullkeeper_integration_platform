package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Conversation DTO
 * @author sergiosanchezsanchez
 *
 */
public final class ConversationDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Create At
	 */
	@JsonProperty("create_at")
	private String createAt;
	
	/**
	 * Update At
	 */
	@JsonProperty("update_at")
	private String updateAt;
	
	/**
	 * Member One
	 */
	@JsonProperty("member_one")
	private PersonDTO memberOne;
	
	/**
	 * Member Two
	 */
	@JsonProperty("member_two")
	private PersonDTO memberTwo; 
	
	
	/**
	 * Messages Count
	 */
	@JsonProperty("messages_count")
	private long messagesCount;
	
	/**
     * Pending Messages For Member One
     */
    @JsonProperty("pending_messages_for_member_one")
	private long pendingMessagesForMemberOne;
    
    /**
     * Pending Messages For Member Two
     */
    @JsonProperty("pending_messages_for_member_two")
	private long pendingMessagesForMemberTwo;
   
    /**
     * Last Message for member one
     */
    @JsonProperty("last_message_for_member_one")
	private String lastMessageForMemberOne;
    
    /**
     * Last Message for member two
     */
    @JsonProperty("last_message_for_member_two")
	private String lastMessageForMemberTwo;
    
    /**
     * Last Message
     */
    @JsonProperty("last_message")
    private String lastMessage;
	
	
	public ConversationDTO() {}
	
	/**
	 * 
	 * @param identity
	 * @param createAt
	 * @param updateAt
	 * @param memberOne
	 * @param memberTwo
	 * @param messagesCount
	 * @param pendingMessagesForMemberOne
	 * @param pendingMessagesForMemberTwo
	 * @param lastMessageForMemberOne
	 * @param lastMessageForMemberTwo
	 * @param lastMessage
	 */
	public ConversationDTO(final String identity, final String createAt,
			final String updateAt, final PersonDTO memberOne, final PersonDTO memberTwo,
			final long messagesCount, final long pendingMessagesForMemberOne,
			final long pendingMessagesForMemberTwo, final String lastMessageForMemberOne,
			final String lastMessageForMemberTwo, final String lastMessage) {
		super();
		this.identity = identity;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.memberOne = memberOne;
		this.memberTwo = memberTwo;
		this.messagesCount = messagesCount;
		this.pendingMessagesForMemberOne = pendingMessagesForMemberOne;
		this.pendingMessagesForMemberTwo = pendingMessagesForMemberTwo;
		this.lastMessageForMemberOne = lastMessageForMemberOne;
		this.lastMessageForMemberTwo = lastMessageForMemberTwo;
		this.lastMessage = lastMessage;
	}

	public String getIdentity() {
		return identity;
	}

	public String getCreateAt() {
		return createAt;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public PersonDTO getMemberOne() {
		return memberOne;
	}

	public PersonDTO getMemberTwo() {
		return memberTwo;
	}

	public long getMessagesCount() {
		return messagesCount;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public void setMemberOne(PersonDTO memberOne) {
		this.memberOne = memberOne;
	}

	public void setMemberTwo(PersonDTO memberTwo) {
		this.memberTwo = memberTwo;
	}

	public void setMessagesCount(long messagesCount) {
		this.messagesCount = messagesCount;
	}

	public long getPendingMessagesForMemberOne() {
		return pendingMessagesForMemberOne;
	}

	public long getPendingMessagesForMemberTwo() {
		return pendingMessagesForMemberTwo;
	}

	public String getLastMessageForMemberOne() {
		return lastMessageForMemberOne;
	}

	public String getLastMessageForMemberTwo() {
		return lastMessageForMemberTwo;
	}

	public void setPendingMessagesForMemberOne(long pendingMessagesForMemberOne) {
		this.pendingMessagesForMemberOne = pendingMessagesForMemberOne;
	}

	public void setPendingMessagesForMemberTwo(long pendingMessagesForMemberTwo) {
		this.pendingMessagesForMemberTwo = pendingMessagesForMemberTwo;
	}

	public void setLastMessageForMemberOne(String lastMessageForMemberOne) {
		this.lastMessageForMemberOne = lastMessageForMemberOne;
	}

	public void setLastMessageForMemberTwo(String lastMessageForMemberTwo) {
		this.lastMessageForMemberTwo = lastMessageForMemberTwo;
	}
	
	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	@Override
	public String toString() {
		return "ConversationDTO [identity=" + identity + ", createAt=" + createAt + ", updateAt=" + updateAt
				+ ", memberOne=" + memberOne + ", memberTwo=" + memberTwo + ", messagesCount=" + messagesCount
				+ ", pendingMessagesForMemberOne=" + pendingMessagesForMemberOne + ", pendingMessagesForMemberTwo="
				+ pendingMessagesForMemberTwo + ", lastMessageForMemberOne=" + lastMessageForMemberOne
				+ ", lastMessageForMemberTwo=" + lastMessageForMemberTwo + ", lastMessage=" + lastMessage + "]";
	}

	
	
}
