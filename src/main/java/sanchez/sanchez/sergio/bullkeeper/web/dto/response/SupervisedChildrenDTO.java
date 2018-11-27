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
	
	
	public SupervisedChildrenDTO() {}

	/**
	 * 
	 * @param identity
	 * @param kid
	 * @param role
	 */
	public SupervisedChildrenDTO(String identity, KidDTO kid, String role) {
		super();
		this.identity = identity;
		this.kid = kid;
		this.role = role;
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


	public void setIdentity(String identity) {
		this.identity = identity;
	}


	public void setKid(KidDTO kid) {
		this.kid = kid;
	}


	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "SupervisedChildrenDTO [identity=" + identity + ", kid=" + kid + ", role=" + role + "]";
	}
}
