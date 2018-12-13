package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import java.util.Date;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import sanchez.sanchez.sergio.bullkeeper.persistence.utils.CascadeSave;

/**
 * Kid Entity
 * @author sergiosanchezsanchez
 *
 */
@Document(collection = KidEntity.COLLECTION_NAME)
public final class KidEntity extends PersonEntity {

    public final static String COLLECTION_NAME = "kids";

    /**
     * School
     */
    @DBRef
    private SchoolEntity school;
    
    /**
     * Current Location
     */
    @Field("current_location")
    @CascadeSave
    private LocationEntity currentLocation;

    
    /**
     * Results
     */
    @Field("results")
    @CascadeSave
    private KidResultsEntity results = new KidResultsEntity();

    public KidEntity() {
    }

    /**
     * 
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param profileImage
     * @param school
     * @param results
     * @param currentLocation
     */
    @PersistenceConstructor
    public KidEntity(String firstName, String lastName, Date birthdate, String profileImage, SchoolEntity school,
            KidResultsEntity results, LocationEntity currentLocation) {
        super(firstName, lastName, birthdate, profileImage);
        this.school = school;
        this.results = results;
        this.currentLocation = currentLocation;
    }

    
    /**
     * 
     * @param firstName
     * @param lastName
     * @param birthdate
     * @param school
     */
    public KidEntity(String firstName, String lastName, Date birthdate, SchoolEntity school) {
        super(firstName, lastName, birthdate);
        this.school = school;
    }

    public SchoolEntity getSchool() {
        return school;
    }

    public void setSchool(SchoolEntity school) {
        this.school = school;
    }


	public KidResultsEntity getResults() {
		return results;
	}

	public void setResults(KidResultsEntity results) {
		this.results = results;
	}

	public LocationEntity getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(LocationEntity currentLocation) {
		this.currentLocation = currentLocation;
	}
	
	
    
}
