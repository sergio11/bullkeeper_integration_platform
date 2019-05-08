package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SmsEntity;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.TerminalRepository;
import sanchez.sanchez.sergio.bullkeeper.persistence.repository.KidRepository;
import sanchez.sanchez.sergio.bullkeeper.web.dto.request.SaveSmsDTO;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SmsDTO;

/**
 * Sms Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SmsEntityMapper {
	
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
	 * SMS Entity to SMS DTO
	 * @param smsEntity
	 * @return
	 */
    @Mappings({
        @Mapping(expression="java(smsEntity.getId().toString())", target = "identity" ),
        @Mapping(expression="java(smsEntity.getReadState().name())", target = "readState" ),
        @Mapping(expression="java(smsEntity.getFolderName().name())",
        		target = "folderName" ),
        @Mapping(expression="java(smsEntity.getTerminal().getId().toString())", 
        		target = "terminal" ),
        @Mapping(expression="java(smsEntity.getKid().getId().toString())", 
				target = "kid" ),
        @Mapping(source = "smsEntity.date", target = "date", dateFormat = "yyyy/MM/dd HH:mm:ss.SSS"),
    })
    @Named("smsEntityToSmsDTO")
    public abstract SmsDTO smsEntityToSmsDTO(final SmsEntity smsEntity); 
	
    /**
     * 
     * @param smsEntityList
     * @return
     */
    @IterableMapping(qualifiedByName = "smsEntityToSmsDTO")
    public abstract Iterable<SmsDTO> smsEntityToSmsDTOs(
    		Iterable<SmsEntity> smsEntityList);
    
    /**
     * Save SMS Dto to Sms Entity
     * @param saveSmsDto
     * @return
     */
    @Mappings({
    	@Mapping(expression="java(terminalRepository.findOne(new org.bson.types.ObjectId(saveSmsDto.getTerminal())))",
    		target="terminal"),
    	@Mapping(expression="java(kidRepository.findOne(new org.bson.types.ObjectId(saveSmsDto.getKid())))",
			target="kid"),
    	@Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.SmsReadStateEnum.valueOf(saveSmsDto.getReadState()))",
    		target="readState"),
    	@Mapping(expression="java(sanchez.sanchez.sergio.bullkeeper.persistence.entity.SmsFolderNameEnum.valueOf(saveSmsDto.getFolderName()))",
			target="folderName")
    })
    @Named("saveSmsDtoToSmsEntity")
    public abstract SmsEntity saveSmsDtoToSmsEntity(final SaveSmsDTO saveSmsDto);
    
    /**
     * Save SMS DTO To Sms Entities
     * @param smsEntityList
     * @return
     */
    @IterableMapping(qualifiedByName = "saveSmsDtoToSmsEntity")
    public abstract Iterable<SmsEntity> saveSmsDtoToSmsEntities(
    			Iterable<SaveSmsDTO> saveSmsDtos);
    
}
