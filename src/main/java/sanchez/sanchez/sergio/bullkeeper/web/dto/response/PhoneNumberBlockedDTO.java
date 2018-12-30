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
	 * Phone Prefix
	 */
	@JsonProperty("phone_prefix")
	private String phonePrefix;
	
	/**
	 * Phone Number
	 */
	@JsonProperty("phone_number")
	private String phoneNumber;
	
	/**
	 * Phone Complete Number
	 */
	@JsonProperty("phone_complete_number")
	private String phoneCompleteNumber;
	
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
	 * @param phonePrefix
	 * @param phoneNumber
	 * @param phoneCompleteNumber
	 * @param terminal
	 * @param kid
	 */
	public PhoneNumberBlockedDTO(String identity, String blockedAt, String phonePrefix, String phoneNumber,
			String phoneCompleteNumber, String terminal, String kid) {
		super();
		this.identity = identity;
		this.blockedAt = blockedAt;
		this.phonePrefix = phonePrefix;
		this.phoneNumber = phoneNumber;
		this.phoneCompleteNumber = phoneCompleteNumber;
		this.terminal = terminal;
		this.kid = kid;
	}

	public String getIdentity() {
		return identity;
	}

	public String getBlockedAt() {
		return blockedAt;
	}

	public String getPhonePrefix() {
		return phonePrefix;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPhoneCompleteNumber() {
		return phoneCompleteNumber;
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

	public void setPhonePrefix(String phonePrefix) {
		this.phonePrefix = phonePrefix;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPhoneCompleteNumber(String phoneCompleteNumber) {
		this.phoneCompleteNumber = phoneCompleteNumber;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	@Override
	public String toString() {
		return "PhoneNumberBlockedDTO [identity=" + identity + ", blockedAt=" + blockedAt + ", phonePrefix="
				+ phonePrefix + ", phoneNumber=" + phoneNumber + ", phoneCompleteNumber=" + phoneCompleteNumber
				+ ", terminal=" + terminal + ", kid=" + kid + "]";
	}
}
