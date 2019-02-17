package sanchez.sanchez.sergio.bullkeeper.sse.models.conversation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.sse.models.AbstractSseData;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.PersonDTO;

/**
 * Message Saved SSE
 * @author sergiosanchezsanchez
 *
 */
public class MessageSavedSSE extends AbstractSseData implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Event Type
	 */
	public final static String EVENT_TYPE = "MESSAGE_SAVED_EVENT";
	
	/**
     * Identity
     */
    @JsonProperty("identity")
    private String identity;

    /**
     * Text
     */
    @JsonProperty("text")
    private String text;

    /**
     * Create At
     */
    @JsonProperty("create_at")
    private String createAt;

    /**
     * Conversation
     */
    @JsonProperty("conversation")
    private String conversation;

    /**
     * From
     */
    @JsonProperty("from")
    private PersonDTO from;

    /**
     * To
     */
    @JsonProperty("to")
    private PersonDTO to;

    /**
     * Viewed
     */
    @JsonProperty("viewed")
    private boolean viewed;
	
    
    /**
	 * 
	 */
	public MessageSavedSSE() {
		this.eventType = EVENT_TYPE;
	}
	
	
	/**
	 * Message Saved SSE
	 * @param subscriberId
	 */
	public MessageSavedSSE(final String subscriberId, final String identity, final String text,
			final String createAt, final String conversation, 
			final PersonDTO from, final PersonDTO to, boolean viewed) {
		super(EVENT_TYPE, subscriberId);
		this.identity = identity;
		this.text = text;
		this.createAt = createAt;
		this.conversation = conversation;
		this.from = from;
		this.to = to;
		this.viewed = viewed;
	}

	public String getIdentity() {
		return identity;
	}

	public String getText() {
		return text;
	}

	public String getCreateAt() {
		return createAt;
	}

	public String getConversation() {
		return conversation;
	}

	public PersonDTO getFrom() {
		return from;
	}

	public PersonDTO getTo() {
		return to;
	}

	public boolean isViewed() {
		return viewed;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public void setConversation(String conversation) {
		this.conversation = conversation;
	}
	
	public void setFrom(PersonDTO from) {
		this.from = from;
	}
	
	public void setTo(PersonDTO to) {
		this.to = to;
	}

	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}


	@Override
	public String toString() {
		return "MessageSavedSSE [identity=" + identity + ", text=" + text + ", createAt=" + createAt + ", conversation="
				+ conversation + ", from=" + from + ", to=" + to + ", viewed=" + viewed + "]";
	}
}
