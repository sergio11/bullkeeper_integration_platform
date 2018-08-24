package sanchez.sanchez.sergio.masoc.persistence.entity;

import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import sanchez.sanchez.sergio.masoc.persistence.utils.CascadeSave;

@Document(collection = SonEntity.COLLECTION_NAME)
public final class SonEntity extends PersonEntity {

    public final static String COLLECTION_NAME = "children";

    @DBRef
    private SchoolEntity school;
    
    @DBRef
    private ParentEntity parent;
    
    
    @Field("results")
    @CascadeSave
    private SonResultsEntity results = new SonResultsEntity();

    public SonEntity() {
    }

    @PersistenceConstructor
    public SonEntity(String firstName, String lastName, Date birthdate, String profileImage, SchoolEntity school,
            ParentEntity parent, SonResultsEntity results) {
        super(firstName, lastName, birthdate, profileImage);
        this.school = school;
        this.parent = parent;
        this.results = results;
    }

    public SonEntity(String firstName, String lastName, Date birthdate, SchoolEntity school,
            ParentEntity parent) {
        super(firstName, lastName, birthdate);
        this.school = school;
        this.parent = parent;
    }

    public SchoolEntity getSchool() {
        return school;
    }

    public void setSchool(SchoolEntity school) {
        this.school = school;
    }

    public ParentEntity getParent() {
        return parent;
    }

    public void setParent(ParentEntity parent) {
        this.parent = parent;
    }

	public SonResultsEntity getResults() {
		return results;
	}

	public void setResults(SonResultsEntity results) {
		this.results = results;
	}
    
}
