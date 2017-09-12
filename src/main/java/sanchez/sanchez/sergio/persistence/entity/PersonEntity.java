

package sanchez.sanchez.sergio.persistence.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;
import org.bson.types.ObjectId;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author sergio
 */

public abstract class PersonEntity {
    
    
    @Id
    protected ObjectId id;
    
    @Field("first_name")
    protected String firstName;

    @Field("last_name")
    protected String lastName;
    
    @Field("birthdate")
    protected Date birthdate;

    
    public PersonEntity(){}

    @PersistenceConstructor
    public PersonEntity(String firstName, String lastName, Date birthdate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
	}
    

    public ObjectId getId() {
        return id;
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

	public String getFullName(){
        return this.firstName + " - " + this.lastName;
    }
	
	
	public Integer getAge() {
		
		Integer years = 0;
		if ((birthdate != null)) {
			Years age = Years.yearsBetween(LocalDate.fromDateFields(birthdate), LocalDate.now());
			years = age.getYears();
        }
		
		return years;
	    
	}

	@Override
	public String toString() {
		return "PersonEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", birthdate="
				+ birthdate + "]";
	}
   
}
