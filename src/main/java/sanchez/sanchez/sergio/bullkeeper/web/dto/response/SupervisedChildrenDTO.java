package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;
import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class SupervisedChildrenDTO extends ResourceSupport 
	implements Serializable {


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
	 * Role
	 */
	@JsonProperty("role")
	private String role;
	
	/**
	 * Pending Message Count
	 */
	@JsonProperty("pending_message_count")
	private long pendingMessagesCount;
	
	public SupervisedChildrenDTO() {}

	/**
	 * 
	 * @param identity
	 * @param kid
	 * @param role
	 * @param pendingMessagesCount
	 */
	public SupervisedChildrenDTO(String identity, KidDTO kid, String role, long pendingMessagesCount) {
		super();
		this.identity = identity;
		this.kid = kid;
		this.role = role;
		this.pendingMessagesCount = pendingMessagesCount;
	}

	public String getIdentity() {
		return identity;
	}

	public KidDTO getKid() {
		return kid;
	}

	public String getRole() {
		return role;
	}

	public long getPendingMessagesCount() {
		return pendingMessagesCount;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setKid(KidDTO kid) {
		this.kid = kid;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPendingMessagesCount(long pendingMessagesCount) {
		this.pendingMessagesCount = pendingMessagesCount;
	}

	@Override
	public String toString() {
		return "SupervisedChildrenDTO [identity=" + identity + ", kid=" + kid + ", role=" + role
				+ ", pendingMessagesCount=" + pendingMessagesCount + "]";
	}

	
	
}
