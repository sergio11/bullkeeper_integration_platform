package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PersonEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.PersonDTO;

/**
 * Person Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class PersonEntityMapper {
	
	
	/**
	 * 
	 * @param alertEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(personEntity.getId().toString())", target = "identity" ),
    })
    @Named("personEntityToPersonDTO")
    public abstract PersonDTO personEntityToPersonDTO(PersonEntity personEntity); 
	
    /**
     * 
     * @param personEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "personEntityToPersonDTO")
    public abstract Iterable<PersonDTO> personEntitiesToPersonDTOs(Iterable<PersonEntity> personEntities);

}
