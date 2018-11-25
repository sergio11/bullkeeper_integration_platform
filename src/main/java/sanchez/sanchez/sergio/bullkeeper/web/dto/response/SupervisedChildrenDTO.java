package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public final class SupervisedChildrenDTO extends ResourceSupport implements Serializable {

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
	 * Role
	 */
	@JsonProperty("role")
	private String role;
	
	/**
	 * Is Confirmed
	 */
	@JsonProperty("is_confirmed")
	private boolean isConfirmed;
	
	/**
	 * Request At
	 */
	@JsonProperty("request_at")
	private String requestAt;
	
	
	public SupervisedChildrenDTO() {}


	public SupervisedChildrenDTO(String identity, KidDTO kid, String role, boolean isConfirmed, String requestAt) {
		super();
		this.identity = identity;
		this.kid = kid;
		this.role = role;
		this.isConfirmed = isConfirmed;
		this.requestAt = requestAt;
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


	public boolean isConfirmed() {
		return isConfirmed;
	}


	public String getRequestAt() {
		return requestAt;
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


	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}


	public void setRequestAt(String requestAt) {
		this.requestAt = requestAt;
	}

}
