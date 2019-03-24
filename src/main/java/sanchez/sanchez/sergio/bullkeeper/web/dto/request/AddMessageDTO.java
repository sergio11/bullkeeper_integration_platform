package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ConversationShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;

/**
 * Add Message DTO
 * @author sergiosanchezsanchez
 *
 */
public final class AddMessageDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Conversation
	 */
	@ConversationShouldExists(message = "{conversation.not.exists}")
	@JsonProperty("conversation")
	private String conversation;
	
	
	/**
	 * Text
	 */
	@NotBlank(message = "{message.text.not.null}")
	@JsonProperty("text")
	private String text;
	
	/**
	 * Origin Id
	 */
	@NotBlank(message = "{message.from.not.null}")
	@ValidObjectId(message = "{message.from.no.valid}",  groups = Extended.class)
	@JsonProperty("from")
	private String from;
	
	/**
	 * Target Id
	 */
	@NotBlank(message = "{message.to.not.null}")
	@ValidObjectId(message = "{message.to.no.valid}",  groups = Extended.class)
	@JsonProperty("to")
	private String to;
	
	
	/**
	 * 
	 */
	public AddMessageDTO() {}

	/**
	 * 
	 * @param conversation
	 * @param text
	 * @param from
	 * @param to
	 */
	public AddMessageDTO(String conversation,String text, String from, String to) {
		super();
		this.conversation = conversation;
		this.text = text;
		this.from = from;
		this.to = to;
	}

	public String getConversation() {
		return conversation;
	}

	public String getText() {
		return text;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public void setConversation(String conversation) {
		this.conversation = conversation;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "AddMessageDTO [conversation=" + conversation + ", text=" + text + ", from=" + from + ", to=" + to + "]";
	}
}
