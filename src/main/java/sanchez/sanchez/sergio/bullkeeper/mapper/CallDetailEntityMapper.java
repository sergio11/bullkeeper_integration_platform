package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.CallDetailEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.PhoneNumberBlockedRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveCallDetailDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.CallDetailDTO;

/**
 * Call Detail Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class CallDetailEntityMapper {
	
	/**
	 * Terminal Repository
	 */
	@Autowired
	protected TerminalRepository terminalRepository;
	
	/**
	 * Kid Repository
	 */
	@Autowired
	protected KidRepository kidRepository;
	
	/**
	 * Phone Number Blocked Repository
	 */
	@Autowired
	protected PhoneNumberBlockedRepository phoneNumberBlockedRepository;
	
	
	/**
	 * @param callDetailEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(callDetailEntity.getId().toString())", 
        		target = "identity" ),
        @Mapping(expression="java(callDetailEntity.getCallType().name())", target = "callType" ),
        @Mapping(expression="java(callDetailEntity.getTerminal().getId().toString())", 
        		target = "terminal" ),
        @Mapping(expression="java(callDetailEntity.getKid().getId().toString())", 
			target = "kid" ),
        @Mapping(source = "callDetailEntity.callDayTime", target = "callDayTime", 
        	dateFormat = "yyyy/MM/dd"),
        @Mapping(expression = "java(phoneNumberBlockedRepository"
        		+ ".countByNumberOrPhoneNumberAndKidIdAndTerminalId("
        		+ "callDetailEntity.getPhoneNumber(), "
        		+ "callDetailEntity.getPhoneNumber(), "
        		+ "callDetailEntity.getKid().getId(),"
        		+ "callDetailEntity.getTerminal().getId()) > 0 ? true: false)",
        		target = "blocked"),
    })
    @Named("callDetailEntityToCallDetailDTO")
    public abstract CallDetailDTO callDetailEntityToCallDetailDTO(final CallDetailEntity callDetailEntity); 
	
    /**
     * 
     * @param callDetailEntityList
     * @return
     */
    @IterableMapping(qualifiedByName = "callDetailEntityToCallDetailDTO")
    public abstract Iterable<CallDetailDTO> callDetailEntityToCallDetailDTOs(
    		Iterable<CallDetailEntity> callDetailEntityList);
    
    
    /**
     * 
     * @param callDetailDTO
     * @return
     */
    @Mappings({
    	@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(callDetailDTO.getTerminal())))",
    		target="terminal"),
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(callDetailDTO.getKid())))",
			target="kid"),
    	@Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.CallTypeEnum.valueOf(callDetailDTO.getCallType()))",
    		target="callType")
    })
    @Named("saveCallDetailDtoToCallDetailEntity")
    public abstract CallDetailEntity saveCallDetailDtoToCallDetailEntity(final SaveCallDetailDTO callDetailDTO);
    
    /**
     * 
     * @param saveCallDetailDTOs
     * @return
     */
    @IterableMapping(qualifiedByName = "saveCallDetailDtoToCallDetailEntity")
    public abstract Iterable<CallDetailEntity> saveCallDetailDtoToCallDetailEntities(
    		Iterable<SaveCallDetailDTO> saveCallDetailDTOs);
}
