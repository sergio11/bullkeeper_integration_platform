package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.GuardianShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SupervisedChildrenShouldExistsIfPresent;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidGuardianRolesType;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.GuardianRolesEnum;


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
	@ValidObjectId(message = "{son.id.notvalid}")
	@KidShouldExists(message = "{kid.not.exists}", groups = Extended.class)
	@JsonProperty("kid")
	private String kid;
	
	/**
	 * Guardian
	 */
	@ValidObjectId(message = "{guardian.id.notvalid}")
	@GuardianShouldExists(message = "{guardian.not.exists}", groups = Extended.class)
	@JsonProperty("guardian")
	private String guardian;
	
	/**
	 * Guardian Role
	 */
	@ValidGuardianRolesType(message = "{guardian.role.type.not.valid}")
	@JsonProperty("role")
	private String role;

	/**
	 * 
	 */
	public SaveGuardianDTO() {}
	
	/**
	 * 
	 * @param kid
	 * @param guardian
	 * @param role
	 */
	public SaveGuardianDTO(String kid, String guardian, String role) {
		super();
		this.kid = kid;
		this.guardian = guardian;
		this.role = role;
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

	@Override
	public String toString() {
		return "SaveGuardianDTO [kid=" + kid + ", guardian=" + guardian + ", role=" + role + "]";
	}
}
