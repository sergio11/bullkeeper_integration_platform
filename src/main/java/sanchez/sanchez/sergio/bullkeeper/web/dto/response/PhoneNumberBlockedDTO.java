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
	 * Phone Number
	 */
	@JsonProperty("phone_number")
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
	 * @param phoneNumber
	 * @param terminal
	 * @param kid
	 */
	public PhoneNumberBlockedDTO(String identity, String blockedAt, 
			String phoneNumber, String terminal, String kid) {
		super();
		this.identity = identity;
		this.blockedAt = blockedAt;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setBlockedAt(String blockedAt) {
		this.blockedAt = blockedAt;
	}

	public void setPhoneNumber(String phoneNumber) {
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
		return "PhoneNumberBlockedDTO [identity=" + identity + ", blockedAt=" + blockedAt + ", phoneNumber="
				+ phoneNumber + ", terminal=" + terminal + ", kid=" + kid + "]";
	}

	
}
