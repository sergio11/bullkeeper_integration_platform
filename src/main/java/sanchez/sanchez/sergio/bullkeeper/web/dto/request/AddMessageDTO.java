package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	 * Text
	 */
	@NotBlank(message = "{message.text.notnull}")
    @Size(min = 3, max = 15, message = "{message.text.size}", groups = Extended.class)
	@JsonProperty("text")
	private String text;
	
	/**
	 * Origin Id
	 */
	@NotBlank(message = "{message.from.notnull}")
	@ValidObjectId(message = "{message.from.no.valid}",  groups = Extended.class)
	@JsonProperty("from")
	private String from;
	
	/**
	 * Target Id
	 */
	@NotBlank(message = "{message.to.notnull}")
	@ValidObjectId(message = "{message.to.no.valid}",  groups = Extended.class)
	@JsonProperty("to")
	private String to;
	
	
	/**
	 * 
	 */
	public AddMessageDTO() {}


	/**
	 * 
	 * @param text
	 * @param from
	 * @param to
	 */
	public AddMessageDTO(final String text, final String from, final String to) {
		super();
		this.text = text;
		this.from = from;
		this.to = to;
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
		return "AddMessageDTO [text=" + text + ", from=" + from + ", to=" + to + "]";
	}

	
}
