package sanchez.sanchez.sergio.bullkeeper.web.dto.response;

import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author sergiosanchezsanchez
 *
 */
public class GuardianDTO extends ResourceSupport {

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
     * Birthdate
     */
    @JsonProperty("birthdate")
    private String birthdate;
    
    /**
     * Age
     */
    @JsonProperty("age")
    private Integer age;
    
    /**
     * Email
     */
    @JsonProperty("email")
    private String email;
    
    /**
     * Phone Prefix
     */
    @JsonProperty("phone_prefix")
    private String phonePrefix;
    
    /**
     * Phone Number 
     */
    @JsonProperty("phone_number")
    private Long phoneNumber;
    
    /**
     * FbId
     */
    @JsonProperty("fb_id")
    private String fbId;
    
    /**
     * Google Id
     */
    @JsonProperty("google_id")
    private String googleId;
    
    /**
     * Children
     */
    @JsonProperty("children")
    private Long children;
    
    /**
     * Locale
     */
    @JsonProperty("locale")
    private String locale;
    
    /**
     * Profile Image
     */
    @JsonProperty("profile_image")
    private String profileImage;
    
    /**
     * Visible
     */
    @JsonProperty("visible")
    private boolean visible;

    public GuardianDTO() {
    }

    /**
     * 
     * @param identity
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param age
     * @param email
     * @param phonePrefix
     * @param phoneNumber
     * @param fbId
     * @param googleId
     * @param children
     * @param locale
     * @param profileImage
     * @param visible
     */
    public GuardianDTO(String identity, String firstName, String lastName, String birthdate, Integer age, String email,
            String phonePrefix, Long phoneNumber, String fbId, String googleId, 
            Long children, String locale, String profileImage, boolean visible) {
        super();
        this.identity = identity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.age = age;
        this.email = email;
        this.phonePrefix = phonePrefix;
        this.phoneNumber = phoneNumber;
        this.fbId = fbId;
        this.googleId = googleId;
        this.children = children;
        this.locale = locale;
        this.profileImage = profileImage;
        this.visible = visible;
    }

    

    public String getPhonePrefix() {
		return phonePrefix;
	}

	public void setPhonePrefix(String phonePrefix) {
		this.phonePrefix = phonePrefix;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }
    

    public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	public Long getChildren() {
        return children;
    }

    public void setChildren(Long children) {
        this.children = children;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
    
    
}
