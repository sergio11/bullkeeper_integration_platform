package sanchez.sanchez.sergio.dto.response;

import org.springframework.hateoas.ResourceSupport;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParentDTO extends ResourceSupport {
	
	@JsonProperty("identity")
	private String identity;
	@JsonProperty("first_name")
    private String firstName;
	@JsonProperty("last_name")
    private String lastName;
	@JsonProperty("birthdate")
    private String birthdate;
	@JsonProperty("age")
    private Integer age;
	@JsonProperty("email")
    private String email;
	@JsonProperty("telephone")
	private String telephone;
	@JsonProperty("fb_id")
    private String fbId;
	@JsonProperty("children")
    private Long children;
	@JsonProperty("locale")
    private String locale;
    
    public ParentDTO(){}

	public ParentDTO(String identity, String firstName, String lastName, String birthdate, Integer age, String email,
			String telephone, String fbId, Long children, String locale) {
		super();
		this.identity = identity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.age = age;
		this.email = email;
		this.telephone = telephone;
		this.fbId = fbId;
		this.children = children;
		this.locale = locale;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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
}
