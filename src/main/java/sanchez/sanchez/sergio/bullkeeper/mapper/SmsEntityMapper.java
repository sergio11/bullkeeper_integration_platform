package sanchez.sanchez.sergio.bullkeeper.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import sanchez.sanchez.sergio.bullkeeper.persistence.entity.SmsEntity;
import sanchez.sanchez.sergio.bullkeeper.web.dto.response.SmsDTO;

/**
 * Sms Entity Mapper
 * @author sergiosanchezsanchez
 *
 */
@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public abstract class SmsEntityMapper {
	
	
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
        @Mapping(source = "smsEntity.date", target = "date", dateFormat = "yyyy/MM/dd"),
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
    
   
}
