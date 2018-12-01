package sanchez.sanchez.sergio.bullkeeper.web.dto.request;

import java.util.Date;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.InAgeRange;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.KidShouldExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.SchoolMustExists;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.ValidObjectId;
import sanchez.sanchez.sergio.bullkeeper.persistence.constraints.group.Extended;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.BirthdayDeserializer;
import sanchez.sanchez.sergio.bullkeeper.web.rest.deserializers.ClearStringDeserializer;

import javax.validation.constraints.NotNull;

/**
 * Update Kid
 * @author sergiosanchezsanchez
 *
 */
public final class UpdateKidDTO {

	/**
	 * Identity
	 */
    @ValidObjectId(message = "{son.id.notvalid}")
    @KidShouldExists(message = "{social.son.not.exists}", groups = Extended.class)
    @JsonProperty("identity")
    private String identity;

    /**
     * First Name
     */
    @NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 3, max = 15, message = "{user.firstname.size}", groups = Extended.class)
    @JsonProperty("first_name")
    @JsonDeserialize(using = ClearStringDeserializer.class)
    private String firstName;

    /**
     * Last Name
     */
    @NotBlank(message = "{user.lastname.notnull}")
    @Size(min = 3, max = 15, message = "{user.lastname.size}", groups = Extended.class)
    @JsonProperty("last_name")
    @JsonDeserialize(using = ClearStringDeserializer.class)
    private String lastName;

    /**
     * BIrtdate
     */
    @JsonProperty("birthdate")
    @JsonDeserialize(using = BirthdayDeserializer.class)
    @NotNull(message="{user.age.notnull}")
    @InAgeRange(min="8", max="18", message="{user.age.invalid}")
    private Date birthdate;

    /**
     * School
     */
    @NotBlank(message = "{user.school.notnull}")
    @ValidObjectId(message = "{user.school.not.valid}")
    @SchoolMustExists(message="{school.should.exists}" , groups = Extended.class)
    @JsonProperty("school")
    private String school;

    public UpdateKidDTO() {
    }

    /**
     * 
     * @param identity
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param school
     */
    public UpdateKidDTO(String identity, String firstName, String lastName, Date birthdate, String school) {
        super();
        this.identity = identity;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.school = school;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

}
