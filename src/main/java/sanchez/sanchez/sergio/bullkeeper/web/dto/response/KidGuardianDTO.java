package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Kid Guardians
 * @author sergiosanchezsanchez
 *
 */
public final class KidGuardianDTO implements Serializable {

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
	 * Kid
	 */
	@JsonProperty("kid")
	private KidDTO kid;
	
	/**
	 * Guardian
	 */
	@JsonProperty("guardian")
	private GuardianDTO guardian;
	
	/**
	 * Is Confirmed
	 */
	@JsonProperty("is_confirmed")
	private boolean isConfirmed;
	
	/**
	 * Role
	 */
	@JsonProperty("role")
	private String role;
	
	/**
	 * Request At
	 */
	@JsonProperty("request_at")
	private String requestAt;
	
	/**
	 * Pending Messages Count
	 */
	@JsonProperty("pending_messages_count")
	private long pendingMessagesCount;

	/**
	 * 
	 */
	public KidGuardianDTO() {}
	
	/**
	 * @param identity
	 * @param kid
	 * @param guardian
	 * @param isConfirmed
	 * @param role
	 * @param requestAt
	 * @param pendingMessagesCount
	 */
	public KidGuardianDTO(final String identity, final KidDTO kid, final GuardianDTO guardian, 
			final boolean isConfirmed, final String role,
			final String requestAt, final long pendingMessagesCount) {
		super();
		this.identity = identity;
		this.kid = kid;
		this.guardian = guardian;
		this.isConfirmed = isConfirmed;
		this.role = role;
		this.requestAt = requestAt;
		this.pendingMessagesCount = pendingMessagesCount;
	}
	
	
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public KidDTO getKid() {
		return kid;
	}

	public GuardianDTO getGuardian() {
		return guardian;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public String getRole() {
		return role;
	}

	public void setKid(KidDTO kid) {
		this.kid = kid;
	}

	public void setGuardian(GuardianDTO guardian) {
		this.guardian = guardian;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRequestAt() {
		return requestAt;
	}

	public void setRequestAt(String requestAt) {
		this.requestAt = requestAt;
	}
	
	public long getPendingMessagesCount() {
		return pendingMessagesCount;
	}

	public void setPendingMessagesCount(long pendingMessagesCount) {
		this.pendingMessagesCount = pendingMessagesCount;
	}

	@Override
	public String toString() {
		return "KidGuardianDTO [identity=" + identity + ", kid=" + kid + ", guardian=" + guardian + ", isConfirmed="
				+ isConfirmed + ", role=" + role + ", requestAt=" + requestAt + ", pendingMessagesCount="
				+ pendingMessagesCount + "]";
	}

	
}
