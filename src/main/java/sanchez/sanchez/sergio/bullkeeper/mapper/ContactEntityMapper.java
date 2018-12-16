package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.ContactEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveContactDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.ContactDTO;

/**
 * Contact Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class ContactEntityMapper {
	

	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Terminal Repository
	 */
	@Autowired
	protected TerminalRepository terminalRepository;
	
	/**
	 * @param contactEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(contactEntity.getId().toString())", 
        		target = "identity" ),
        @Mapping(expression="java(contactEntity.getKid().getId().toString())", 
        		target = "kid" ),
        @Mapping(expression="java(contactEntity.getTerminal().getId().toString())",
        		target = "terminal" )
    })
    @Named("contactEntityToContactDTO")
    public abstract ContactDTO contactEntityToContactDTO(final ContactEntity contactEntity); 
	
    /**
     * 
     * @param contactEntitiesList
     * @return
     */
    @IterableMapping(qualifiedByName = "contactEntityToContactDTO")
    public abstract Iterable<ContactDTO> contactEntityToContactDTOs(
    		Iterable<ContactEntity> contactEntitiesList);
    
    
    /**
     * 
     * @param saveContactDTO
     * @return
     */
    @Mappings({
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveContactDTO.getKid())))", 
    			target="kid"),
    	@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(saveContactDTO.getTerminal())))", 
    			target="terminal")
    })
    @Named("saveContactDTOToContactEntity")
    public abstract ContactEntity saveContactDTOToContactEntity(final SaveContactDTO saveContactDTO);
    
    /**
     * 
     * @param saveContactDTOs
     * @return
     */
    @IterableMapping(qualifiedByName = "saveContactDTOToContactEntity")
    public abstract Iterable<ContactEntity> saveContactDTOToContactEntities(final Iterable<SaveContactDTO> saveContactDTOs);

}
