package sanchez.sanchez.sergio.bullkeeper.web.rest.response;

/**
 * Conversation Response Enum
 * @author sergiosanchezsanchez
 *
 */
public enum ConversationResponseEnum implements IResponseCodeTypes {
	
	ALL_SELF_CONVERSATIONS(1100L),
    NO_CONVERSATIONS_FOUND(1101L),
    CONVERSATION_DETAIL(1102L),
    CONVERSATION_NOT_FOUND_EXCEPTION(1103L),
    CONVERSATION_SUCCESSFULLY_DELETED(1104L),
    ALL_CONVERSATIONS_SUCCESSFULLY_DELETED(1105L),
    NO_MESSAGES_FOUND(1106L),
    ALL_CONVERSATION_MESSAGES_SUCCESSFULLY_DELETED(1107L),
    MESSAGE_SUCCESSFULLY_CREATED(1108L),
    MESSAGE_NOT_ALLOWED(1109L),
    CONVERSATION_MEMBER_NOT_FOUND(1110L),
    ALL_CONVERSATION_MESSAGES(1111L);
	
	private Long code;

	private ConversationResponseEnum(Long code) {
	      this.code = code;
	}
	
	@Override
	public Long getResponseCode() {
		return code;
	}
}
