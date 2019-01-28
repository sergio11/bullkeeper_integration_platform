package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.TerminalShouldExists;

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
	 * Prefix
	 */
    @JsonProperty("prefix")
	private String prefix;
    
    /**
	 * Number
	 */
    @JsonProperty("number")
	private String number;
    
    /**
     * Phone NUmber
     */
    @JsonProperty("phonenumber")
	private String phonenumber;
	
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
	 * 
	 * @param prefix
	 * @param number
	 * @param phonenumber
	 * @param terminal
	 * @param kid
	 */
	public AddPhoneNumberBlockedDTO(final String prefix, final String number, 
			final String phonenumber, final String terminal, final String kid) {
		super();
		this.prefix = prefix;
		this.number = number;
		this.phonenumber = phonenumber;
		this.terminal = terminal;
		this.kid = kid;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getNumber() {
		return number;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getKid() {
		return kid;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "AddPhoneNumberBlockedDTO [prefix=" + prefix + ", number=" + number + ", phonenumber=" + phonenumber
				+ ", terminal=" + terminal + ", kid=" + kid + "]";
	}
}
