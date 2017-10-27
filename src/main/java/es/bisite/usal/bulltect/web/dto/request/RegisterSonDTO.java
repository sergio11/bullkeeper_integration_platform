package es.bisite.usal.bulltect.web.dto.request;

import java.util.Date;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import es.bisite.usal.bulltect.persistence.constraints.InAgeRange;
import es.bisite.usal.bulltect.persistence.constraints.SchoolMustExists;
import es.bisite.usal.bulltect.persistence.constraints.ValidObjectId;
import es.bisite.usal.bulltect.persistence.constraints.group.Extended;
import es.bisite.usal.bulltect.web.rest.deserializers.BirthdayDeserializer;
import es.bisite.usal.bulltect.web.rest.deserializers.ClearStringDeserializer;
import javax.validation.constraints.NotNull;

public final class RegisterSonDTO {

    @NotBlank(message = "{user.firstname.notnull}")
    @Size(min = 3, max = 15, message = "{user.firstname.size}", groups = Extended.class)
    @JsonProperty("first_name")
    @JsonDeserialize(using = ClearStringDeserializer.class)
    private String firstName;

    @NotBlank(message = "{user.lastname.notnull}")
    @Size(min = 3, max = 15, message = "{user.lastname.size}", groups = Extended.class)
    @JsonProperty("last_name")
    @JsonDeserialize(using = ClearStringDeserializer.class)
    private String lastName;

    @JsonProperty("birthdate")
    @JsonDeserialize(using = BirthdayDeserializer.class)
    @NotNull(message="{user.age.notnull}")
    @InAgeRange(min="8", max="18", message="{user.age.invalid}")
    private Date birthdate;

    @NotBlank(message = "{user.school.notnull}")
    @ValidObjectId(message = "{user.school.not.valid}")
    @SchoolMustExists(message="{school.should.exists}" , groups = Extended.class)
    @JsonProperty("school")
    private String school;

    public RegisterSonDTO() {
    }

    public RegisterSonDTO(String firstName, String lastName, Date birthdate, String school) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.school = school;
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
