package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Person DTO
 * @author sergiosanchezsanchez
 *
 */
public final class PersonDTO implements Serializable {

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
     * First Name
     */
	@JsonProperty("first_name")
	private String firstName;

    /**
     * Last Name
     */
	@JsonProperty("last_name")
	private String lastName;
    
    /**
     * Profile Image
     */
	@JsonProperty("profile_image")
	private String profileImage;
	
	
	public PersonDTO() {}
	
	/**
	 * 
	 * @param identity
	 * @param firstName
	 * @param lastName
	 * @param profileImage
	 */
	public PersonDTO(String identity, String firstName, String lastName, String profileImage) {
		super();
		this.identity = identity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profileImage = profileImage;
	}

	public String getIdentity() {
		return identity;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "PersonDTO [identity=" + identity + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", profileImage=" + profileImage + "]";
	}
}
