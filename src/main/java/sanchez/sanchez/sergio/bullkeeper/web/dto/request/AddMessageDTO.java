package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	 * 
	 */
	public AddMessageDTO() {}


	/**
	 * 
	 * @param text
	 */
	public AddMessageDTO(final String text) {
		super();
		this.text = text;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	@Override
	public String toString() {
		return "AddMessageDTO [text=" + text + "]";
	}
}
