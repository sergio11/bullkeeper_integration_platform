package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.PhoneNumberBlockedEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.AddPhoneNumberBlockedDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.PhoneNumberBlockedDTO;

/**
 * Phone Number Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class PhoneNumberEntityMapper {
	
	/**
	 * Terminal Repository
	 */
	@Autowired
	protected TerminalRepository terminalRepository;
	
	/**
	 * Kid REpository
	 */
	@Autowired
	protected KidRepository kidRepository;

	

	/**
	 * @param phoneNumber
	 * @return
	 */
	@Mappings({
		@Mapping(expression="java(phoneNumber.getId().toString())", target="identity"),
    	@Mapping(expression="java(phoneNumber.getTerminal().getId().toString())", 
    			target="terminal"),
    	@Mapping(expression="java(phoneNumber.getKid().getId().toString())", 
    		target="kid"),
    	@Mapping(source = "phoneNumber.blockedAt", target = "blockedAt", 
    		dateFormat = "yyyy/MM/dd")
    
    })
    @Named("phoneNumberBlockedEntityToPhoneNumberBlockedDTO")
    public abstract PhoneNumberBlockedDTO phoneNumberBlockedEntityToPhoneNumberBlockedDTO(final PhoneNumberBlockedEntity phoneNumber); 
	
    /**
     * 
     * @param phoneNumberList
     * @return
     */
    @IterableMapping(qualifiedByName = "phoneNumberBlockedEntityToPhoneNumberBlockedDTO")
    public abstract Iterable<PhoneNumberBlockedDTO> phoneNumberBlockedEntityToPhoneNumberBlockedDTOs(
    		Iterable<PhoneNumberBlockedEntity> phoneNumberList);
    
    /**
     * 
     * @param addPhoneNumberBlocked
     * @return
     */
    @Mappings({
    	@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(addPhoneNumberBlocked.getTerminal())))", 
    			target="terminal"),
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(addPhoneNumberBlocked.getKid())))", 
    		target="kid")
    })
    @Named("addPhoneNumberBlockedEntity")
    public abstract PhoneNumberBlockedEntity addPhoneNumberBlockedEntity(
    		final AddPhoneNumberBlockedDTO addPhoneNumberBlocked);
    
    
   
}
