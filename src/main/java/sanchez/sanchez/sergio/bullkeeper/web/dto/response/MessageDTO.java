package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Message DTO
 * @author sergiosanchezsanchez
 *
 */
public final class MessageDTO implements Serializable {

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

	
	public MessageDTO() {}
	
	/**
	 * 
	 * @param identity
	 * @param text
	 * @param createAt
	 * @param conversation
	 */
	public MessageDTO(String identity, String text, String createAt, String conversation) {
		super();
		this.identity = identity;
		this.text = text;
		this.createAt = createAt;
		this.conversation = conversation;
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

	@Override
	public String toString() {
		return "MessageDTO [identity=" + identity + ", text=" + text + ", createAt=" + createAt + ", conversation="
				+ conversation + "]";
	}
}
