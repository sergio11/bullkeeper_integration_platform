package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidPhoneNumber;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.PhoneNumberDeserializer;

/**
 * Add Phone Number Blocked
 * @author sergiosanchezsanchez
 *
 */
public class AddPhoneNumberBlockedDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Phone Number
	 */

    @ValidPhoneNumber(message = "{phone.number.not.valid}")
    @JsonProperty("phone_number")
	@JsonDeserialize(using = PhoneNumberDeserializer.class)
	private PhoneNumber phoneNumber;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	@TerminalShouldExists(message="{terminal.not.exists}")
	private String terminal;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	@KidShouldExists(message="{kid.not.exists}")
	private String kid;
	
	/**
	 * 
	 */
	public AddPhoneNumberBlockedDTO() {}
	
	
	/**
	 * Add Phone Number
	 * @param phoneNumber
	 * @param terminal
	 * @param kid
	 */
	public AddPhoneNumberBlockedDTO(final PhoneNumber phoneNumber, final String terminal, final String kid) {
		super();
		this.phoneNumber = phoneNumber;
		this.terminal = terminal;
		this.kid = kid;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "AddPhoneNumberBlockedDTO [phoneNumber=" + phoneNumber + ", terminal=" + terminal + ", kid=" + kid + "]";
	}
	
	
	
}
