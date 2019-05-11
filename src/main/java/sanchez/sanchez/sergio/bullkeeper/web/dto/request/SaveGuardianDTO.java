package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.GuardianShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SupervisedChildrenShouldExistsIfPresent;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidGuardianRolesType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;


/**
 * Save Guardians DTO
 * @author sergiosanchezsanchez
 *
 */
public final class SaveGuardianDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Identity
	*/
	@SupervisedChildrenShouldExistsIfPresent(message = "{supervised.children.not.exists}")
	@JsonProperty("identity")
	private String identity;
	
	/**
	 * Kid
	 */
	@ValidObjectId(message = "{id.not.valid}")
	@KidShouldExists(message = "{kid.should.be.exists}", groups = Extended.class)
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Guardian
	 */
	@ValidObjectId(message = "{id.not.valid}")
	@GuardianShouldExists(message = "{guardian.not.exists}", groups = Extended.class)
	@JsonProperty("guardian")
	private String guardian;
	
	/**
	 * Guardian Role
	 */
	@ValidGuardianRolesType(message = "{guardian.role.type.not.valid}")
	@JsonProperty("role")
	private String role;
	
	
	@JsonProperty("is_confirmed")
	private boolean isConfirmed;

	/**
	 * 
	 */
	public SaveGuardianDTO() {}
	
	/**
	 * 
	 * @param kid
	 * @param guardian
	 * @param role
	 * @param isConfirmed
	 */
	public SaveGuardianDTO(String kid, String guardian, String role, boolean isConfirmed) {
		super();
		this.kid = kid;
		this.guardian = guardian;
		this.role = role;
		this.isConfirmed = isConfirmed;
	}
	
	

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getKid() {
		return kid;
	}

	public String getGuardian() {
		return guardian;
	}

	public String getRole() {
		return role;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public void setGuardian(String guardian) {
		this.guardian = guardian;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	@Override
	public String toString() {
		return "SaveGuardianDTO [identity=" + identity + ", kid=" + kid + ", guardian=" + guardian + ", role=" + role
				+ ", isConfirmed=" + isConfirmed + "]";
	}

	
}
