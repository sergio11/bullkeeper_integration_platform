package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Phone Number Blocked DTO
 * @author sergiosanchezsanchez
 *
 */
public final class PhoneNumberBlockedDTO {
	
	/**
	 * Identity
	 */
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Blocked At
	 */
	@JsonProperty("blocked_at")
	private String blockedAt;
	
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
	 * Phone Number
	 */
	@JsonProperty("phonenumber")
	private String phoneNumber;
	
	/**
	 * Terminal
	 */
	@JsonProperty("terminal")
	private String terminal;
	
	/**
	 * Kid
	 */
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * 
	 */
	public PhoneNumberBlockedDTO() {}

	/**
	 * 
	 * @param identity
	 * @param blockedAt
	 * @param prefix
	 * @param number
	 * @param phoneNumber
	 * @param terminal
	 * @param kid
	 */
	public PhoneNumberBlockedDTO(String identity, String blockedAt, String prefix, 
			String number, String phoneNumber,
			String terminal, String kid) {
		super();
		this.identity = identity;
		this.blockedAt = blockedAt;
		this.prefix = prefix;
		this.number = number;
		this.phoneNumber = phoneNumber;
		this.terminal = terminal;
		this.kid = kid;
	}

	

	public String getIdentity() {
		return identity;
	}

	public String getBlockedAt() {
		return blockedAt;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getNumber() {
		return number;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getTerminal() {
		return terminal;
	}

	public String getKid() {
		return kid;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setBlockedAt(String blockedAt) {
		this.blockedAt = blockedAt;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "PhoneNumberBlockedDTO [identity=" + identity + ", blockedAt=" + blockedAt + ", prefix=" + prefix
				+ ", number=" + number + ", phoneNumber=" + phoneNumber + ", terminal=" + terminal + ", kid=" + kid
				+ "]";
	}

	
}
