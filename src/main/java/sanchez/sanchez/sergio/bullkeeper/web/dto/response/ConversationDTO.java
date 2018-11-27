package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Conversation DTO
 * @author sergiosanchezsanchez
 *
 */
public final class ConversationDTO implements Serializable {

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
	 * Create At
	 */
	@JsonProperty("create_at")
	private String createAt;
	
	/**
	 * Update At
	 */
	@JsonProperty("update_at")
	private String updateAt;
	
	
	/**
	 * Kid Guardian
	 */
	@JsonProperty("kid_guardian")
	private KidGuardianDTO kidGuardian;
	
	
	public ConversationDTO() {}
	
	/**
	 * 
	 * @param identity
	 * @param createAt
	 * @param updateAt
	 * @param kidGuardian
	 */
	public ConversationDTO(String identity, String createAt, String updateAt, KidGuardianDTO kidGuardian) {
		super();
		this.identity = identity;
		this.createAt = createAt;
		this.updateAt = updateAt;
		this.kidGuardian = kidGuardian;
	}


	public String getIdentity() {
		return identity;
	}


	public String getCreateAt() {
		return createAt;
	}


	public String getUpdateAt() {
		return updateAt;
	}


	public KidGuardianDTO getKidGuardian() {
		return kidGuardian;
	}


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}


	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}


	public void setKidGuardian(KidGuardianDTO kidGuardian) {
		this.kidGuardian = kidGuardian;
	}

	@Override
	public String toString() {
		return "ConversationDTO [identity=" + identity + ", createAt=" + createAt + ", updateAt=" + updateAt
				+ ", kidGuardian=" + kidGuardian + "]";
	}
	
	

}
